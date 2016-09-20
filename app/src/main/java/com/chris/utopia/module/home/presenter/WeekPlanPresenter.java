package com.chris.utopia.module.home.presenter;

import com.chris.utopia.module.home.activity.WeekPlanActionView;

/**
 * Created by Chris on 2016/2/14.
 */
public interface WeekPlanPresenter {
    void setActionView(WeekPlanActionView actionView);
    void loadWeekPlan();
}
