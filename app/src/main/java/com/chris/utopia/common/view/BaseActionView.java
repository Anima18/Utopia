package com.chris.utopia.common.view;

import android.content.Context;

/**
 * Created by Chris on 2015/9/2.
 */
public interface BaseActionView {
    public Context getContext();

    public void showProgress(String message);

    public void hideProgress();

    public void showMessage(String message);

    public void setToolBarTitle();
}
