package com.chris.utopia.module.home.presenter;

import com.chris.utopia.module.home.activity.TimeAnalysisActionView;

/**
 * Created by Chris on 2016/3/12.
 */
public interface TimeAnalysisPresenter {
    void setActionView(TimeAnalysisActionView actionView);
    void loadTimeFinishData();
    void loadTimeRoleData();
    void loadTimeClassesData();
    void loadTimeQuadrantData();
    void loadTimePlanData();
}
