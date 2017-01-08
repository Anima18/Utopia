package com.chris.utopia.common.view;

import android.content.Context;

import com.trello.rxlifecycle.LifecycleProvider;

/**
 * Created by Chris on 2015/9/2.
 */
public interface BaseActionView {
    Context getContext();

    void showProgress(String message);

    void hideProgress();

    void showMessage(String message);

    LifecycleProvider getLifecycleProvider();
    //void setToolBarTitle();
}
