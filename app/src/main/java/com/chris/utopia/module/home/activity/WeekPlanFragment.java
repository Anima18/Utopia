package com.chris.utopia.module.home.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chris.utopia.R;
import com.chris.utopia.common.view.BaseFragment;
import com.chris.utopia.entity.Plan;
import com.chris.utopia.module.home.adapter.WeekPlanAdapter;
import com.chris.utopia.module.home.presenter.WeekPlanPresenter;
import com.chris.utopia.module.plan.activity.PlanCreateActivity;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2015/8/15.
 */
public class WeekPlanFragment extends BaseFragment implements WeekPlanActionView {

    private RecyclerView weekPlanRecyclerView;

    private WeekPlanAdapter adapter;
    private List<Plan> planList = new ArrayList<>();

    @Inject
    private WeekPlanPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week_plan, container, false);
        initView(view);
        initData();
        return view;
    }

    public void initView(View view) {
        weekPlanRecyclerView = (RecyclerView) view.findViewById(R.id.weekPlanFrm_recyclerView);
        adapter = new WeekPlanAdapter(getContext(), planList);
        adapter.setOnItemClickListener(new WeekPlanAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("PLAN", planList.get(position));
                Intent intent = new Intent(getActivity(), PlanCreateActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
            }
        });
        weekPlanRecyclerView.setAdapter(adapter);
        weekPlanRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void initData() {
        presenter.setActionView(this);
        presenter.loadWeekPlan();
    }

    @Override
    public void loadPlan(List<Plan> planList) {
        this.planList.clear();
        this.planList.addAll(planList);

        adapter.notifyDataSetChanged();
    }
}
