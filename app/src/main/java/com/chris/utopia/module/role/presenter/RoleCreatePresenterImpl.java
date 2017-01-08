package com.chris.utopia.module.role.presenter;

import android.content.Context;

import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.DateUtil;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.common.util.StringUtil;
import com.chris.utopia.entity.Role;
import com.chris.utopia.module.role.activity.RoleCreateActionView;
import com.chris.utopia.module.role.interactor.RoleInteractor;
import com.chris.utopia.module.role.interactor.RoleInteractorImpl;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Chris on 2016/1/25.
 */
public class RoleCreatePresenterImpl implements RoleCreatePresenter {

    private RoleCreateActionView actionView;
    private Context context;

    private RoleInteractor interactor = new RoleInteractorImpl();

    @Override
    public void setActionView(RoleCreateActionView actionView) {
        this.actionView = actionView;
        this.context = actionView.getContext();
    }

    @Override
    public void saveRole(Role role) {
        try {
            String userId = SharedPrefsUtil.getStringValue(context, Constant.SP_KEY_LOGIN_USER_ID, "");
            String userName = SharedPrefsUtil.getStringValue(context, Constant.SP_KEY_LOGIN_USER_NAME, "");
            String dateStr = DateUtil.toString(new Date(), Constant.DATETIME_FORMAT_6);

            role.setId(StringUtil.getUUID());
            role.setUserId(userId);
            role.setCreateAt(dateStr);
            role.setCreateBy(userName);
            role.setUpdateBy(userName);
            role.setUpdateAt(dateStr);

            interactor.addRole(role);
            actionView.showCreateRoleSuccess("创建角色成功");
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("创建角色失败");
        }
    }
}
