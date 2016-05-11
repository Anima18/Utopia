package com.chris.utopia.module.plan.activity;

import com.chris.utopia.common.view.BaseActionView;
import com.chris.utopia.entity.Thing;

import java.util.List;

/**
 * Created by Chris on 2016/1/31.
 */
public interface PlanCreateActionView extends BaseActionView {
    void saveSuccess(String message);
    void saveSuccessAndAddThing();
    void loadThing(List<Thing> things);
}
