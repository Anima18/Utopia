package com.chris.utopia.module.home.presenter;

import com.chris.utopia.entity.Thing;
import com.chris.utopia.module.home.activity.TodayTaskActionView;

/**
 * Created by Chris on 2016/2/13.
 */
public interface TodayTaskPresenter {
    void setActionView(TodayTaskActionView actionView);
    void loadTodayTask();
    void updateThing(Thing thing);
    void deleteThing(Thing thing);
}
