package com.chris.utopia.module.system.interactor;

import com.chris.utopia.base.DBOpenHelper;
import com.chris.utopia.base.MyApplication;
import com.chris.utopia.common.util.StringUtil;
import com.chris.utopia.entity.Role;
import com.chris.utopia.entity.ThingClasses;
import com.chris.utopia.entity.User;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Chris on 2016/1/18.
 */
public class SystemInteractorImpl implements SystemInteractor {
    private static DBOpenHelper openHelper = null;

    private Dao<User, Integer> userDao = null;
    private Dao<ThingClasses, Integer> thingClassesDao = null;
    private Dao<Role, Integer> roleDao = null;

    public SystemInteractorImpl() {
        try {
            userDao = getOpenHelper().getUserDao();
            thingClassesDao = getOpenHelper().getThingClassesDao();
            roleDao = getOpenHelper().getRoleDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBOpenHelper getOpenHelper() {
        if(openHelper == null) {
            openHelper = OpenHelperManager.getHelper(MyApplication.getInstance(), DBOpenHelper.class);
        }
        return openHelper;
    }

    @Override
    public void addUser(User user) throws SQLException {
        try {
            userDao.createOrUpdate(user);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<User> findUser(User user) throws SQLException {
        try {
            QueryBuilder<User, Integer> qb = userDao.queryBuilder();
            Where<User, Integer> where = qb.where();
            where.isNotNull("userId");

            if(user != null) {
                if(StringUtil.isNotEmpty(user.getUserId())) {
                    where.and().eq("userId", user.getUserId());
                }

                if(StringUtil.isNotEmpty(user.getName())) {
                    where.and().eq("NAME", user.getName());
                }

                if(StringUtil.isNotEmpty(user.getPassword())) {
                    where.and().eq("PASSWORD", user.getPassword());
                }

                if(StringUtil.isNotEmpty(user.getEmail())) {
                    where.and().eq("EMAIL", user.getEmail());
                }
            }

            return qb.query();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public User findUserById(int id) throws SQLException {
        try {
            return userDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<ThingClasses> findThingClassess(ThingClasses classes) throws SQLException {
        try {
            QueryBuilder<ThingClasses, Integer> qb = thingClassesDao.queryBuilder();
            Where<ThingClasses, Integer> where = qb.where();
            where.isNotNull("id");

            if(classes != null) {
                if(StringUtil.isNotEmpty(classes.getName())) {
                    where.and().eq("NAME", classes.getName());
                }
                if(classes.getUserId() != null) {
                    where.and().eq("USER_ID", classes.getUserId());
                }
            }

            qb.orderBy("CREATE_AT", false);

            return qb.query();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void addThingClassess(ThingClasses classes) throws SQLException {
        try {
            thingClassesDao.create(classes);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  e;
        }
    }

    @Override
    public void addThingClassess(final List<ThingClasses> classesList) throws Exception {
        try {
            thingClassesDao.callBatchTasks(new Callable<Void>() {

                @Override
                public Void call() throws Exception {
                    for(ThingClasses thingClasses : classesList) {
                        thingClassesDao.create(thingClasses);
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public ThingClasses findThingClassessById(Integer id) throws SQLException {
        try {
            return thingClassesDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Role> findRole(Role role) throws SQLException {
        try {
            QueryBuilder<Role, Integer> qb = roleDao.queryBuilder();
            Where<Role, Integer> where = qb.where();
            where.isNotNull("id");
            if(role != null) {
                if(role.getUserId() != null) {
                    where.and().eq("USER_ID", role.getUserId());
                }
            }
            qb.orderBy("CREATE_AT", true);
            return qb.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Role findRoleById(int id) throws SQLException {
        try {
            return roleDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
