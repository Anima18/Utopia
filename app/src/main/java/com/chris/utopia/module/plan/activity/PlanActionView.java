package com.chris.utopia.module.plan.activity;

import com.chris.utopia.common.view.BaseActionView;
import com.chris.utopia.entity.Plan;

import java.util.List;

/**
 * Created by Chris on 2016/2/12.
 */
public interface PlanActionView extends BaseActionView {
    void loadPlan(List<Plan> plans);
}
