package com.chris.utopia.module.home.activity;

import com.chris.utopia.common.view.BaseActionView;
import com.trello.rxlifecycle.LifecycleProvider;

/**
 * Created by Admin on 2016/12/18.
 */

public interface MeActionView extends BaseActionView {
    LifecycleProvider getLifecycleProvider();
    void syncSuccess();
    void syncFail();
}
