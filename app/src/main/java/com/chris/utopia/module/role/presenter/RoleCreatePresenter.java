package com.chris.utopia.module.role.presenter;

import com.chris.utopia.entity.Role;
import com.chris.utopia.module.role.activity.RoleCreateActionView;

/**
 * Created by Chris on 2016/1/25.
 */
public interface RoleCreatePresenter {
    void setActionView(RoleCreateActionView actionView);
    void saveRole(Role role);
}
