package com.chris.utopia.module.system.presenter;

import com.chris.utopia.entity.User;
import com.chris.utopia.module.system.activity.RegisterActionView;

/**
 * Created by Chris on 2016/1/18.
 */
public interface RegisterPresenter {
    void setActionView(RegisterActionView actionView);
    void register(User user);
}
