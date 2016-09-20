package com.chris.utopia.module.home.activity;

import com.chris.utopia.common.view.BaseActionView;
import com.chris.utopia.entity.Thing;

import java.util.List;

/**
 * Created by Chris on 2016/2/13.
 */
public interface TodayTaskActionView extends BaseActionView {
    void loadTodayTask(List<Thing> thingList);
    void updateTodayTask(Thing thing);
    void removeTodayTask(Thing thing);
}
