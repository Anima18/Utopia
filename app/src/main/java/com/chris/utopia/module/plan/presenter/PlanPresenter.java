package com.chris.utopia.module.plan.presenter;

import com.chris.utopia.entity.Plan;
import com.chris.utopia.module.plan.activity.PlanActionView;

/**
 * Created by Chris on 2016/2/12.
 */
public interface PlanPresenter {
    void setActionView(PlanActionView actionView);
    void loadPlan();
    void updateStatus(Plan plan);
    void deletePlan(Plan plan);
}
