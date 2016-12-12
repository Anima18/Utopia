package com.chris.utopia.module.home.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chris.utopia.R;
import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.DateUtil;
import com.chris.utopia.common.view.BaseFragment;
import com.chris.utopia.entity.Thing;
import com.chris.utopia.module.home.adapter.TodayTaskAdapter;
import com.chris.utopia.module.home.presenter.TodayTaskPresenter;
import com.google.inject.Inject;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Chris on 2015/8/15.
 */
public class TodayTaskFragment extends BaseFragment implements TodayTaskActionView, View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    private RecyclerView todayRecyclerView;
    private FloatingActionButton addFAB;

    private TodayTaskAdapter adapter;
    private List<Thing> thingList = new ArrayList<>();

    @Inject
    private TodayTaskPresenter presenter;

    private Thing selectThing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today_task, container, false);
        initView(view);
        initEvent();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    public void initView(View view) {
        todayRecyclerView = (RecyclerView) view.findViewById(R.id.todayTaskFrm_recyclerView);
        addFAB = (FloatingActionButton) view.findViewById(R.id.todayTaskFrm_addFad);

        adapter = new TodayTaskAdapter(getContext(), thingList);
        adapter.setOnItemClickListener(new TodayTaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Thing thing = thingList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("THING", thing);
                Intent intent = null;
                if(Constant.THING_TYPE_THING.equals(thing.getType())) {
                    intent = new Intent(getContext(), ThingCreateActivity.class);
                }else if(Constant.THING_TYPE_HABIT.equals(thing.getType())) {
                    intent = new Intent(getContext(), HabitCreateActivity.class);
                }
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
            }
        });
        adapter.setOnItemLongClickListener(new TodayTaskAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View itemView, int position) {
                Integer menuArray = null;
                selectThing = thingList.get(position);
                final Thing thing = selectThing;

                if(Constant.THING_TYPE_THING.equals(thing.getType())) {
                    if(Constant.THING_STATUS_DONE.equals(thing.getStatus())) {
                        menuArray = R.array.today_task_thing_done_action;
                    }else if(Constant.THING_STATUS_IGNORE.equals(thing.getStatus())) {
                        menuArray = R.array.today_task_thing_ignore_action;
                    }else {
                        menuArray = R.array.today_task_thing_action;
                    }
                }else if(Constant.THING_TYPE_HABIT.equals(thing.getType())) {
                    menuArray = R.array.today_task_habit_action;
                }
                new MaterialDialog.Builder(getContext())
                        .items(menuArray)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                if(text.equals("标记完成")) {
                                    thing.setStatus(Constant.THING_STATUS_DONE);
                                    presenter.updateThing(thing);
                                }else if(text.equals("推迟执行")) {
                                    showTimeDialog(thing.getBeginTime());
                                }/*else if(text.equals("转为习惯")) {

                                }*/else if(text.equals("忽略事件")) {
                                    thing.setStatus(Constant.THING_STATUS_IGNORE);
                                    presenter.updateThing(thing);
                                }else if(text.equals("删除事件")) {
                                    presenter.deleteThing(thing);
                                }
                            }
                        })
                        .show();
            }
        });
        todayRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        todayRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
        todayRecyclerView.setLayoutManager(layoutManager);
    }

    public void initData() {
        presenter.setActionView(this);
        presenter.loadTodayTask();
    }

    public void initEvent() {
        addFAB.setOnClickListener(this);
    }

    @Override
    public void loadTodayTask(List<Thing> thingList) {
        this.thingList.clear();
        this.thingList.addAll(thingList);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.todayTaskFrm_addFad:
                Intent intent = new Intent(getContext(), ThingCreateActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                break;
        }
    }

    public void showTimeDialog(String timeStr) {
        Calendar calendar = Calendar.getInstance();
        Date date = DateUtil.toDate(timeStr, Constant.DATETIME_FORMAT_5);
        calendar.setTime(date);
        TimePickerDialog tpd = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), false);
        tpd.show(getActivity().getFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String timeStr = (hourOfDay < 10 ? "0"+hourOfDay : hourOfDay) + ":" + (minute < 10 ? "0"+minute : minute) + ":" + (second < 10 ? "0"+second : second);
        selectThing.setBeginTime(timeStr);
        selectThing.setStatus(Constant.PLAN_STATUS_NEW);
        presenter.updateThing(selectThing);
    }

    @Override
    public void updateTodayTask(Thing thing) {
        if(thing.getStatus().equals(Constant.THING_STATUS_NEW)) {
            adapter.notifyDataSetChanged();
        }else {
            adapter.notifyItemChanged(thingList.indexOf(thing));
        }

    }

    @Override
    public void removeTodayTask(Thing thing) {
        thingList.remove(thing);
        adapter.notifyItemRemoved(thingList.indexOf(thing));
    }
}
