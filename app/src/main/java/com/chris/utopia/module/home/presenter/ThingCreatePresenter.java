package com.chris.utopia.module.home.presenter;

import com.chris.utopia.entity.Plan;
import com.chris.utopia.entity.Role;
import com.chris.utopia.entity.Thing;
import com.chris.utopia.entity.ThingClasses;
import com.chris.utopia.module.home.activity.ThingCreateActionView;

/**
 * Created by Chris on 2016/2/8.
 */
public interface ThingCreatePresenter {
    void setActionView(ThingCreateActionView actionView);
    ThingClasses getThingClassById(String id);
    Role getRoleById(String id);
    Plan getPlanById(String id);
    void save(Plan plan, Thing thing);
    void save(Thing thing);
}
