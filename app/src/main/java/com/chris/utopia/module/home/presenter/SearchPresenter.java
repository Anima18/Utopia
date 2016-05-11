package com.chris.utopia.module.home.presenter;

import com.chris.utopia.module.home.activity.SearchActionView;

/**
 * Created by Chris on 2016/2/27.
 */
public interface SearchPresenter {
    void setActionView(SearchActionView actionView);
    void search(String searchStr);
}
