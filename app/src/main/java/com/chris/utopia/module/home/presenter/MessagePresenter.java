package com.chris.utopia.module.home.presenter;

import com.chris.utopia.entity.Thing;
import com.chris.utopia.module.home.activity.MessageActionView;

import java.util.List;

/**
 * Created by Chris on 2016/2/28.
 */
public interface MessagePresenter {
    void setActionView(MessageActionView actionView);
    void loadData();
    void update(Thing thing);
    void clearAll(List<Thing> thingList);
}
