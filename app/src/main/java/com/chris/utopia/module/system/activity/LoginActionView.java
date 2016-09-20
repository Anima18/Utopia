package com.chris.utopia.module.system.activity;

import com.chris.utopia.common.view.BaseActionView;

/**
 * Created by Chris on 2016/1/19.
 */
public interface LoginActionView extends BaseActionView {
    void toMainPage();
    void showLoginMessage(String message);
}
