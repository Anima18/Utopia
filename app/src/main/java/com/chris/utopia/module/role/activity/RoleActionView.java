package com.chris.utopia.module.role.activity;

import com.chris.utopia.common.view.BaseActionView;
import com.chris.utopia.entity.Role;

import java.util.List;

/**
 * Created by Chris on 2016/1/26.
 */
public interface RoleActionView extends BaseActionView {
    void loadRoleData(List<Role> roleList);
}
