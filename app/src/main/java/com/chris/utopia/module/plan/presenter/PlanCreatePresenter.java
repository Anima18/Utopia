package com.chris.utopia.module.plan.presenter;

import com.chris.utopia.entity.Idea;
import com.chris.utopia.entity.Plan;
import com.chris.utopia.entity.Role;
import com.chris.utopia.entity.ThingClasses;
import com.chris.utopia.module.plan.activity.PlanCreateActionView;

/**
 * Created by Chris on 2016/1/31.
 */
public interface PlanCreatePresenter {
    void setActionView(PlanCreateActionView actionView);
    void loadThing(Plan plan);
    void save(Plan plan, Idea idea, boolean isClose);
    ThingClasses getThingClassById(Integer id);
    Role getRoleById(Integer id);
}
