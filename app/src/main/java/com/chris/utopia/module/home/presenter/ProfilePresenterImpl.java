package com.chris.utopia.module.home.presenter;

import android.content.Context;

import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.entity.User;
import com.chris.utopia.module.home.activity.ProfileActionView;
import com.chris.utopia.module.system.interactor.SystemInteractor;
import com.chris.utopia.module.system.interactor.SystemInteractorImpl;
import com.google.inject.Inject;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Chris on 2016/3/8.
 */
public class ProfilePresenterImpl implements ProfilePresenter {

    private ProfileActionView actionView;
    private Context mContext;

    private SystemInteractor systemInteractor = new SystemInteractorImpl();

    @Override
    public void setActionView(ProfileActionView actionView) {
        this.actionView = actionView;
        this.mContext = actionView.getContext();
    }

    @Override
    public void resetPassword(String password, String newPssword) {
        try {
            Integer userId = SharedPrefsUtil.getIntValue(mContext, Constant.SP_KEY_LOGIN_USER_ID, 0);
            User searchUser = new User();
            searchUser.setUserId(userId);

            List<User> userList = systemInteractor.findUser(searchUser);
            User user = userList.get(0);
            if(!user.getPassword().equals(password)) {
                actionView.showPasswordError("旧密码不正确");
            }else {
                user.setPassword(newPssword);
                systemInteractor.addUser(user);
                actionView.showResetPasswordSuccess("更改密码成功");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showResetPasswordFail("更改密码失败");
        }
    }

    @Override
    public void loadUserInfo() {
        try {
            int userId = SharedPrefsUtil.getIntValue(actionView.getContext(), Constant.SP_KEY_LOGIN_USER_ID, 0);
            User user = systemInteractor.findUserById(userId);
            actionView.loadUserInfo(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(User user) {
        try {
            systemInteractor.addUser(user);
            actionView.showSaveUserMessage("保存个人信息成功");
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showSaveUserMessage("保存个人信息失败");
        }
    }
}
