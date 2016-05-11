package com.chris.utopia.module.idea.presenter;

import com.chris.utopia.module.idea.activity.IdeaActionView;

/**
 * Created by Chris on 2016/1/24.
 */
public interface IdeaPresenter {
    void setActionView(IdeaActionView actionView);
    void loadIdeaData();
}
