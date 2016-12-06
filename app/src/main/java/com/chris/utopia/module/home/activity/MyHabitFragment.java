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
import com.chris.utopia.common.view.BaseFragment;
import com.chris.utopia.entity.Thing;
import com.chris.utopia.module.home.adapter.MyHabitAdapter;
import com.chris.utopia.module.home.presenter.HabitPresenter;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Crhis on 2015/8/15.
 */
public class MyHabitFragment extends BaseFragment implements View.OnClickListener, MyHabitActionView {

    private RecyclerView habitRecyclerView;
    private FloatingActionButton addFAB;

    private MyHabitAdapter adapter;
    private List<Thing> thingList = new ArrayList<>();

    @Inject
    private HabitPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_habit, container, false);
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
        addFAB = (FloatingActionButton) view.findViewById(R.id.habitFrm_addFad);
        habitRecyclerView = (RecyclerView) view.findViewById(R.id.habitFrm_recyclerView);
        adapter = new MyHabitAdapter(getContext(), thingList);
        adapter.setOnItemClickListener(new MyHabitAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("THING", thingList.get(position));
                Intent intent = new Intent(getContext(), HabitCreateActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
            }
        });
        adapter.setOnItemLongClickListener(new MyHabitAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View itemView, final int position) {
                Integer menuArrayId = null;
                final Thing thing = thingList.get(position);
                if(Constant.HABIT_STATUS_ACTION.equals(thing.getHabitStatus())) {
                    menuArrayId = R.array.habit_pause_action;
                }else if(Constant.HABIT_STATUS_PAUSE.equals(thing.getHabitStatus())) {
                    menuArrayId = R.array.habit_continue_action;
                }
                new MaterialDialog.Builder(getContext())
                        .items(menuArrayId)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                if(text.equals("暂停习惯")) {
                                    thing.setHabitStatus(Constant.HABIT_STATUS_PAUSE);
                                    presenter.updateHabit(thing);
                                }else if(text.equals("继续习惯")) {
                                    thing.setHabitStatus(Constant.HABIT_STATUS_ACTION);
                                    presenter.updateHabit(thing);
                                }else if(text.equals("删除习惯")) {
                                    presenter.deleteHabit(thing);
                                }
                            }
                        })
                        .show();
            }
        });
        habitRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        habitRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
        habitRecyclerView.setLayoutManager(layoutManager);
    }

    public void initData() {
        presenter.setActionView(this);
        presenter.loadHabit();
    }

    public void initEvent() {
        addFAB.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.habitFrm_addFad:
                Intent intent = new Intent(getContext(), HabitCreateActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                break;
        }
    }

    @Override
    public void loadHabit(List<Thing> thingList) {
        this.thingList.clear();
        this.thingList.addAll(thingList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateSuccess(Thing thing) {
        int index = thingList.indexOf(thing);
        adapter.notifyItemChanged(index);
    }

    @Override
    public void removeSuccess(Thing thing) {
        int index = thingList.indexOf(thing);
        thingList.remove(index);
        adapter.notifyItemRemoved(index);
    }
}
