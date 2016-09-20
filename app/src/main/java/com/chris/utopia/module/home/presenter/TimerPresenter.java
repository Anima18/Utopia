package com.chris.utopia.module.home.presenter;

import com.chris.utopia.module.home.activity.TimerActionView;

/**
 * Created by Chris on 2016/3/5.
 */
public interface TimerPresenter {
    void setActionView(TimerActionView actionView);
    void loadData(String dateStr);
}
