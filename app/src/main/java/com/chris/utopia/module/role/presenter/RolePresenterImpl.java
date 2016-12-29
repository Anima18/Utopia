package com.chris.utopia.module.role.presenter;

import android.content.Context;

import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.entity.Role;
import com.chris.utopia.module.role.activity.RoleActionView;
import com.chris.utopia.module.role.interactor.RoleInteractor;
import com.google.inject.Inject;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Admin on 2016/1/26.
 */
public class RolePresenterImpl implements RolePresenter {

    private RoleActionView actionView;
    private Context context;

    @Inject
    private RoleInteractor interactor;

    @Override
    public void setActionView(RoleActionView actionView) {
        this.actionView = actionView;
        this.context = actionView.getContext();
    }

    @Override
    public void loadRoleData() {
        try {
            String userId = SharedPrefsUtil.getStringValue(context, Constant.SP_KEY_LOGIN_USER_ID, "");
            Role role = new Role();
            role.setUserId(userId);

            List<Role> roles = interactor.findRole(role);
            actionView.loadRoleData(roles);
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("获取角色数据失败");
        }
    }
}
