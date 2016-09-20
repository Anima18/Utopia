package com.chris.utopia.module.home.activity;

import com.chris.utopia.common.view.BaseActionView;

/**
 * Created by Chris on 2016/3/8.
 */
public interface ProfileActionView extends BaseActionView {
    void showResetPasswordFail(String message);
    void showResetPasswordSuccess(String message);
    void showPasswordError(String message);
}
