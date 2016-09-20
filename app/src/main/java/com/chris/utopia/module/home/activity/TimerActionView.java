package com.chris.utopia.module.home.activity;

import com.chris.utopia.common.view.BaseActionView;
import com.chris.utopia.entity.Thing;

import java.util.List;

/**
 * Created by Chris on 2016/3/5.
 */
public interface TimerActionView extends BaseActionView {
    void loadData(List<Thing> thingList);
}
