package com.chris.utopia.module.idea.interactor;

import com.chris.utopia.base.DBOpenHelper;
import com.chris.utopia.base.MyApplication;
import com.chris.utopia.common.util.StringUtil;
import com.chris.utopia.entity.Idea;
import com.chris.utopia.entity.ThingClasses;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Chris on 2016/1/22.
 */
public class IdeaInteractorImpl implements IdeaInteractor {
    private static DBOpenHelper openHelper = null;

    private Dao<Idea, Integer> ideaDao = null;
    private Dao<ThingClasses, Integer> thingClassesDao = null;

    public IdeaInteractorImpl() {
        try {
            ideaDao = getOpenHelper().getIdeaDao();
            thingClassesDao = getOpenHelper().getThingClassesDao();
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
    public ThingClasses findThingClassessById(Integer id) throws SQLException {
        try {
            return thingClassesDao.queryForId(id);
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
    public Idea findIdeaById(Integer id) throws SQLException {
        try {
            return ideaDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Idea> findIdea(Idea idea) throws SQLException {
        try {
            QueryBuilder<Idea, Integer> qb = ideaDao.queryBuilder();
            Where<Idea, Integer> where = qb.where();
            where.isNotNull("id");

            if(idea != null) {
                if(StringUtil.isNotEmpty(idea.getUserId())) {
                    where.and().eq("USER_ID", idea.getUserId());
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
    public void addIdea(Idea idea) throws SQLException {
        try {
            ideaDao.createOrUpdate(idea);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  e;
        }
    }

    @Override
    public void addIdea(final List<Idea> ideas) throws Exception {
        try {
            ideaDao.callBatchTasks(new Callable<Void>() {

                @Override
                public Void call() throws Exception {
                    for(Idea idea : ideas) {
                        ideaDao.create(idea);
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
    public void delete(Idea idea) throws SQLException {
        try {
            ideaDao.delete(idea);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  e;
        }
    }
}
