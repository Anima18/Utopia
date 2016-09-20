package com.chris.utopia.module.role.presenter;

import com.chris.utopia.module.role.activity.RoleActionView;

/**
 * Created by Chris on 2016/1/26.
 */
public interface RolePresenter {
    void setActionView(RoleActionView actionView);
    void loadRoleData();
}
