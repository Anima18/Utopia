package com.chris.utopia.module.role.interactor;

import com.chris.utopia.base.DBOpenHelper;
import com.chris.utopia.base.MyApplication;
import com.chris.utopia.entity.Role;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Chris on 2016/1/25.
 */
public class RoleInteractorImpl implements RoleInteractor {
    private static DBOpenHelper openHelper = null;

    private Dao<Role, String> roleDao = null;

    public RoleInteractorImpl() {
        try {
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
    public void addRole(Role role) throws SQLException {
        try {
            roleDao.createOrUpdate(role);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Role findRoleById(String roleId) throws SQLException {
        return roleDao.queryForId(roleId);
    }

    @Override
    public void addRole(final List<Role> roleList) throws Exception {
        try {
            roleDao.callBatchTasks(new Callable<Void>() {

                @Override
                public Void call() throws Exception {
                    for(Role r : roleList) {
                        roleDao.create(r);
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
    public List<Role> findRole(Role role) throws SQLException {
        try {
            QueryBuilder<Role, String> qb = roleDao.queryBuilder();
            Where<Role, String> where = qb.where();
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
}
