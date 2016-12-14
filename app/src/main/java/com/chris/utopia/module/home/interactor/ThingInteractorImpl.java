package com.chris.utopia.module.home.interactor;

import android.content.Intent;
import android.util.Log;

import com.chris.utopia.base.DBOpenHelper;
import com.chris.utopia.base.MyApplication;
import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.DateUtil;
import com.chris.utopia.common.util.StringUtil;
import com.chris.utopia.entity.Plan;
import com.chris.utopia.entity.Role;
import com.chris.utopia.entity.Thing;
import com.chris.utopia.entity.ThingClasses;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Chris on 2016/2/8.
 */
public class ThingInteractorImpl implements ThingInteractor {

    private static DBOpenHelper openHelper = null;

    private Dao<Thing, Integer> thingDao = null;
    private Dao<ThingClasses, Integer> thingClassesDao = null;
    private Dao<Role, Integer> roleDao = null;

    public ThingInteractorImpl() {
        try {
            thingDao = getOpenHelper().getThingDao();
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
    public void addThing(Thing thing) throws SQLException {
        try {
            thingDao.createOrUpdate(thing);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteThing(Thing thing) throws SQLException {
        try {
            DeleteBuilder<Thing, Integer> db = thingDao.deleteBuilder();
            Where<Thing, Integer> where = db.where();
            where.isNotNull("id");
            if(thing != null) {
                if(StringUtil.isNotEmpty(thing.getUserId())) {
                    where.and().eq("USER_ID", thing.getUserId());
                }

                if(StringUtil.isNotEmpty(thing.getBeginDate())) {
                    where.and().eq("BEGIN_DATE", thing.getBeginDate());
                }

                if(StringUtil.isNotEmpty(thing.getPlanId())) {
                    where.and().eq("PLAN_ID", thing.getPlanId());
                }

                if(StringUtil.isNotEmpty(thing.getType())) {
                    where.and().eq("TYPE", thing.getType());
                }
            }
            db.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll(final List<Thing> thingList) throws SQLException {
        try {
            thingDao.callBatchTasks(new Callable<Void>() {

                @Override
                public Void call() throws Exception {
                    for(Thing thing : thingList) {
                        thingDao.delete(thing);
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Thing findThingById(Integer id) throws SQLException {
        try {
            return thingDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Thing> findThing(Thing thing) throws SQLException {
        try {
            QueryBuilder<Thing, Integer> qb = thingDao.queryBuilder();
            Where<Thing, Integer> where = qb.where();
            where.isNotNull("id");

            if(thing != null) {
                if(StringUtil.isNotEmpty(thing.getUserId())) {
                    where.and().eq("USER_ID", thing.getUserId());
                }

                if(StringUtil.isNotEmpty(thing.getBeginDate())) {
                    where.and().eq("BEGIN_DATE", thing.getBeginDate());
                }

                if(StringUtil.isNotEmpty(thing.getPlanId())) {
                    where.and().eq("PLAN_ID", thing.getPlanId());
                }

                if(StringUtil.isNotEmpty(thing.getType())) {
                    where.and().eq("TYPE", thing.getType());
                }
            }
            qb.orderBy("BEGIN_DATE", true);
            qb.orderBy("BEGIN_TIME", true);

            return qb.query();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Thing> findWeekThing(Plan plan) throws SQLException {
        try {
            QueryBuilder<Thing, Integer> qb = thingDao.queryBuilder();
            Where<Thing, Integer> where = qb.where();
            where.isNotNull("id");

            if(plan != null) {
                if(StringUtil.isNotEmpty(plan.getUserId())) {
                    where.and().eq("USER_ID", plan.getUserId());
                }

                if(StringUtil.isNotEmpty(plan.getBeginDate())) {
                    where.and().in("BEGIN_DATE", DateUtil.getALlweekDays());
                }

                if(StringUtil.isNotEmpty(plan.getId())) {
                    where.and().eq("PLAN_ID", plan.getId());
                }
            }

            qb.orderBy("BEGIN_TIME", true);

            return qb.query();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Thing> search(Thing thing) throws SQLException {
        try {
            QueryBuilder<Thing, Integer> qb = thingDao.queryBuilder();
            Where<Thing, Integer> where = qb.where();
            where.isNotNull("id");

            if(thing != null) {
                if(StringUtil.isNotEmpty(thing.getUserId())) {
                    where.and().eq("USER_ID", thing.getUserId());
                }

                if(StringUtil.isNotEmpty(thing.getBeginDate())) {
                    where.and().eq("BEGIN_DATE", thing.getBeginDate()).and().eq("TYPE", Constant.THING_TYPE_THING);
                }

                if(StringUtil.isNotEmpty(thing.getTitle())) {
                    where.or().like("TITLE", "%" + thing.getTitle() + "%").and().eq("TYPE", Constant.THING_TYPE_THING);
                }
            }

            qb.orderBy("BEGIN_TIME", true);
            Log.i("search", qb.prepareStatementString());
            return qb.query();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Thing> findMessage(Thing thing) throws SQLException {
        try {
            QueryBuilder<Thing, Integer> qb = thingDao.queryBuilder();
            Where<Thing, Integer> where = qb.where();
            where.isNotNull("id");

            if(thing != null) {
                if(StringUtil.isNotEmpty(thing.getUserId())) {
                    where.and().eq("USER_ID", thing.getUserId());
                }

                where.and().eq("TYPE", Constant.THING_TYPE_THING).and().eq("STATUS", "NEW")
                        .and().lt("BEGIN_DATE", DateUtil.toString(new Date(), Constant.DATETIME_FORMAT_4));

            }
            qb.orderBy("BEGIN_TIME", true);
            Log.i("findMessage", qb.prepareStatementString());
            return qb.query();
        } catch (SQLException e) {
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
    public Role findRoleById(Integer id) throws SQLException {
        try {
            return roleDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Thing> findHabit(Thing thing) throws SQLException {
        try {
            QueryBuilder<Thing, Integer> qb = thingDao.queryBuilder();
            Where<Thing, Integer> where = qb.where();
            where.isNotNull("id");

            if(thing != null) {
                if(StringUtil.isNotEmpty(thing.getUserId())) {
                    where.and().eq("USER_ID", thing.getUserId());
                }

                if(StringUtil.isNotEmpty(thing.getType())) {
                    where.and().eq("TYPE", thing.getType());
                }

            }

            qb.orderBy("BEGIN_TIME", true);

            return qb.query();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Thing> findHabitOneDay(Thing thing) throws SQLException {
        GenericRawResults<Thing> rawResults = null;
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT id, TITLE, DESCRIPTION, SAID_SOMETHING, STATUS, BEGIN_DATE, BEGIN_TIME, END_TIME, IS_PROMPTING, CLASSESS_ID, ");
            sql.append(" THING_QUADRANT, PLAN_ID, ROLE_ID, USER_ID, PROGRESS, TYPE, PERIOD, WHAT_DAY, HOLD_ON_DAY, HABIT_STATUS, UPDATE_AT");
            sql.append(" FROM thing t");
            sql.append(" WHERE t.type = 'HABIT' ");
            sql.append(" AND t.habit_status = 'ACTION' ");
            sql.append(" AND t.period = '每天' ");
            sql.append(" OR t.period = '每周' ");
            sql.append(" AND t.what_day = '"+thing.getWhatDay()+"'");
            sql.append(" AND t.habit_status = 'ACTION' ");

            Log.i("findCarton", "Sql:" + sql.toString());
            rawResults =
                    thingDao.queryRaw(sql.toString(),
                            new RawRowMapper<Thing>() {
                                public Thing mapRow(String[] columnNames,
                                                   String[] resultColumns) {
                                    return new Thing(resultColumns[0],resultColumns[1],resultColumns[2],resultColumns[3],resultColumns[4],resultColumns[5],resultColumns[6],
                                            resultColumns[7],resultColumns[8],resultColumns[9],resultColumns[10],resultColumns[11],resultColumns[12],resultColumns[13],resultColumns[14],
                                            resultColumns[15],resultColumns[16],resultColumns[17],resultColumns[18],resultColumns[19],resultColumns[20]);
                                }
                            });
            return rawResults.getResults();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (rawResults != null) {
                    rawResults.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }
    @Override
    public void addThing(final List<Thing> thingList) throws Exception {
        try {
            thingDao.callBatchTasks(new Callable<Void>() {

                @Override
                public Void call() throws Exception {
                    for(Thing thing : thingList) {
                        thingDao.createOrUpdate(thing);
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
