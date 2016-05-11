package com.chris.utopia.module.system.presenter;

import com.chris.utopia.entity.User;
import com.chris.utopia.module.system.activity.LoginActionView;

/**
 * Created by Chris on 2016/1/19.
 */
public interface LoginPresenter {
    void setActionView(LoginActionView actionView);
    void login(User user);
    void initData();
}
