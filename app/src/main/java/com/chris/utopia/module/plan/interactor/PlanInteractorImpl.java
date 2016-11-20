package com.chris.utopia.module.plan.interactor;

import android.util.Log;

import com.chris.utopia.base.DBOpenHelper;
import com.chris.utopia.base.MyApplication;
import com.chris.utopia.common.util.DateUtil;
import com.chris.utopia.entity.Plan;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Chris on 2016/1/31.
 */
public class PlanInteractorImpl implements PlanInteractor {
    private static DBOpenHelper openHelper = null;

    private Dao<Plan, Integer> planDao = null;

    public PlanInteractorImpl() {
        try {
            planDao = getOpenHelper().getPlanDao();
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
    public void createOrUpdate(Plan plan) throws SQLException {
        try {
            planDao.createOrUpdate(plan);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Plan> findPlan(Plan plan) throws SQLException {
        try {
            QueryBuilder<Plan, Integer> qb = planDao.queryBuilder();
            Where<Plan, Integer> where = qb.where();
            where.isNotNull("id");
            if(plan != null) {
                if(plan.getUserId() != null) {
                    where.and().eq("USER_ID", plan.getUserId());
                }
            }
            return qb.query();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Plan findPlanById(Integer id) throws SQLException {
        try {
            return planDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Plan> findWeekPlan(Plan plan) throws SQLException {
        GenericRawResults<Plan> rawResults = null;
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select p.id, p.role_id, p.name, p.description, p.progress, p.user_id, p.thing_classes_id, p.thing_quadrant, p.begin_date, p.end_date, p.status " +
                    "from plan p left join thing t on p.id = t.plan_id where t.begin_date in ("+ DateUtil.getALlweekDayStr()+") and p.user_id = '"+plan.getUserId()+"' group by p.id ");

            Log.i("findCarton", "Sql:" + sql.toString());
            rawResults =
                    planDao.queryRaw(sql.toString(),
                            new RawRowMapper<Plan>() {
                                public Plan mapRow(String[] columnNames,
                                                       String[] resultColumns) {
                                    return new Plan(resultColumns[0],resultColumns[1],resultColumns[2],resultColumns[3],resultColumns[4],resultColumns[5],resultColumns[6],
                                            resultColumns[7],resultColumns[8],resultColumns[9],resultColumns[10]);
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
    public void addPlan(final List<Plan> plans) throws Exception {
        try {
            planDao.callBatchTasks(new Callable<Void>() {

                @Override
                public Void call() throws Exception {
                    for(Plan plan : plans) {
                        planDao.create(plan);
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
