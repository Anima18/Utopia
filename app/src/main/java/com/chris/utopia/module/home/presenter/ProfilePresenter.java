package com.chris.utopia.module.home.presenter;

import com.chris.utopia.entity.User;
import com.chris.utopia.module.home.activity.ProfileActionView;

/**
 * Created by Chris on 2016/3/8.
 */
public interface ProfilePresenter {
    void setActionView(ProfileActionView actionView);
    void resetPassword(String password, String newPassword);
    void loadUserInfo();
    void saveUser(User user);
}
