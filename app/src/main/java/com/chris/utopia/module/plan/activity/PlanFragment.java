package com.chris.utopia.module.plan.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.chris.utopia.R;
import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.view.BaseFragment;
import com.chris.utopia.entity.Plan;
import com.chris.utopia.module.plan.adapter.PlanAdapter;
import com.chris.utopia.module.plan.presenter.PlanPresenter;
import com.chris.utopia.module.plan.presenter.PlanPresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 2015/8/15.
 */
public class PlanFragment extends BaseFragment implements View.OnClickListener, PlanActionView {

    private RecyclerView planRecyclerView;
    private FloatingActionButton addFAB;

    private PlanAdapter adapter;
    private List<Plan> planList = new ArrayList<>();

    private PlanPresenter presenter = new PlanPresenterImpl();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan, container, false);
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
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.activity_toolBar) ;
        toolbar.setTitle("计划实验室");
        addFAB = (FloatingActionButton) view.findViewById(R.id.planFrm_addFad);
        planRecyclerView = (RecyclerView) view.findViewById(R.id.planFrm_recyclerView);

        adapter = new PlanAdapter(getContext(), planList);
        adapter.setOnItemClickListener(new PlanAdapter.OnItemClickListener() {
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
        adapter.setOnMenuItemClickListener(new PlanAdapter.OnMenuItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, int id) {
                Plan plan = planList.get(id);
                if(0 == pos) {
                    plan.setStatus(Constant.PLAN_STATUS_DONE);
                    presenter.updateStatus(plan);
                }else if(1 == pos) {
                    plan.setStatus(Constant.PLAN_STATUS_IGNORE);
                    presenter.updateStatus(plan);
                }else if(2 == pos) {
                    presenter.deletePlan(plan);
                }
            }
        });
        planRecyclerView.setAdapter(adapter);
        planRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void initData() {
        presenter.setActionView(this);
        presenter.loadPlan();
    }

    public void initEvent() {
        addFAB.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.planFrm_addFad:
                Intent intent = new Intent(getContext(), PlanCreateActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                break;
        }
    }

    @Override
    public void loadPlan(List<Plan> plans) {
        this.planList.clear();
        this.planList.addAll(plans);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updatePlanSuccess(Plan plan) {
        adapter.notifyItemChanged(planList.indexOf(plan));
    }


    @Override
    public void deletePlanSuccess(Plan plan) {
        int index = planList.indexOf(plan);
        planList.remove(index);
        adapter.notifyItemRemoved(index);
    }
}
