package com.chris.utopia.module.home.activity;

import com.chris.utopia.common.view.BaseActionView;
import com.chris.utopia.entity.Plan;

import java.util.List;

/**
 * Created by Chris on 2016/2/14.
 */
public interface WeekPlanActionView extends BaseActionView {
    void loadPlan(List<Plan> planList);
}
