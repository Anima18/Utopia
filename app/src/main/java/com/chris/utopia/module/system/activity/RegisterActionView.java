package com.chris.utopia.module.system.activity;

import com.chris.utopia.common.view.BaseActionView;

/**
 * Created by Chris on 2016/1/18.
 */
public interface RegisterActionView extends BaseActionView {
    void toLoginPage(String message);
    void showRegisterMessage(String message);
}
