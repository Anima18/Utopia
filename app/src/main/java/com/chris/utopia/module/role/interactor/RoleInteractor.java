package com.chris.utopia.module.role.interactor;

import com.chris.utopia.entity.Role;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Chris on 2016/1/25.
 */
public interface RoleInteractor {
    void addRole(Role role) throws SQLException;
    List<Role> findRole(Role role) throws SQLException;
    Role findRoleById(String roleId) throws SQLException;
    void addRole(List<Role> roleList) throws Exception;
}
