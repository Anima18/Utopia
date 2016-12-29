package com.chris.utopia.module.system.presenter;

import android.content.Context;

import com.chris.utopia.common.util.StringUtil;
import com.chris.utopia.entity.User;
import com.chris.utopia.module.system.activity.RegisterActionView;
import com.chris.utopia.module.system.interactor.SystemInteractor;
import com.google.inject.Inject;

import java.sql.SQLException;

/**
 * Created by Chris on 2016/1/18.
 */
public class RegisterPresenterImpl implements RegisterPresenter {

    private RegisterActionView actionView;
    private Context context;

    @Inject
    private SystemInteractor interactor;

    @Override
    public void setActionView(RegisterActionView actionView) {
        this.actionView = actionView;
        this.context = actionView.getContext();
    }

    @Override
    public void register(User user) {
        try {
            user.setUserId(StringUtil.getUUID());
            interactor.addUser(user);
            actionView.toLoginPage("注册成功");
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showRegisterMessage("注册失败");
        }
    }
}
