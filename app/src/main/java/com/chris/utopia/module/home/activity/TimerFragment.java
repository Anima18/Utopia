package com.chris.utopia.module.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chris.utopia.R;
import com.chris.utopia.common.view.BaseFragment;
import com.chris.utopia.entity.Thing;
import com.chris.utopia.module.home.adapter.TimerAdapter;
import com.chris.utopia.module.home.presenter.TimerPresenter;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 2016/3/5.
 */
public class TimerFragment extends BaseFragment implements TimerActionView {

    private RecyclerView dataRv;

    private TimerAdapter timerAdapter;
    private List<Thing> thingList = new ArrayList<>();
    private String dateStr;

    @Inject
    private TimerPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        dateStr = getArguments().getString("DATE");
        initView(view);
        initData();
        initEvent();
        return view;
    }

    public void initView(View view) {
        dataRv = (RecyclerView) view.findViewById(R.id.timerFrm_data_rv);
    }

    public void initData() {
        timerAdapter = new TimerAdapter(getContext(), thingList);
        dataRv.setAdapter(timerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        dataRv.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
        dataRv.setLayoutManager(layoutManager);

        presenter.setActionView(this);
        presenter.loadData(dateStr);
    }

    public void initEvent() {}

    @Override
    public void loadData(List<Thing> thingList) {
        this.thingList.clear();
        this.thingList.addAll(thingList);
        timerAdapter.notifyDataSetChanged();
    }

}
