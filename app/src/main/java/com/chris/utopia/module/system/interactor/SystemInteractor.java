package com.chris.utopia.module.system.interactor;

import com.chris.utopia.entity.Role;
import com.chris.utopia.entity.ThingClasses;
import com.chris.utopia.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Chris on 2016/1/18.
 */
public interface SystemInteractor {
    void addUser(User user) throws SQLException;
    List<User> findUser(User user) throws SQLException;
    List<ThingClasses> findThingClassess(ThingClasses classes) throws SQLException;
    List<Role> findRole(Role role) throws SQLException;
    Role findRoleById(int id) throws SQLException;
    ThingClasses findThingClassessById(Integer id) throws SQLException;
    void addThingClassess(ThingClasses classes) throws SQLException;
    void addThingClassess(List<ThingClasses> classesList) throws Exception;
}
