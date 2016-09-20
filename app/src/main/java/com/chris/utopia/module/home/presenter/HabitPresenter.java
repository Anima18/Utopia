package com.chris.utopia.module.home.presenter;

import com.chris.utopia.entity.Thing;
import com.chris.utopia.module.home.activity.MyHabitActionView;

/**
 * Created by Chris on 2016/2/21.
 */
public interface HabitPresenter {
    void setActionView(MyHabitActionView actionView);
    void loadHabit();
    void updateHabit(Thing thing);
    void deleteHabit(Thing thing);
}
