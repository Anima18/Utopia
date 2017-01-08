package com.chris.utopia.module.home.presenter;

import android.content.Context;

import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.DateUtil;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.common.util.StringUtil;
import com.chris.utopia.entity.Plan;
import com.chris.utopia.entity.Role;
import com.chris.utopia.entity.Thing;
import com.chris.utopia.entity.ThingClasses;
import com.chris.utopia.module.home.activity.ThingCreateActionView;
import com.chris.utopia.module.home.interactor.ThingInteractor;
import com.chris.utopia.module.home.interactor.ThingInteractorImpl;
import com.chris.utopia.module.plan.interactor.PlanInteractor;
import com.chris.utopia.module.plan.interactor.PlanInteractorImpl;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Chris on 2016/2/8.
 */
public class ThingCreatePresenterImpl implements ThingCreatePresenter {

    private ThingCreateActionView actionView;
    private Context mContext;

    private ThingInteractor interactor = new ThingInteractorImpl();
    private PlanInteractor planInteractor = new PlanInteractorImpl();

    @Override
    public void setActionView(ThingCreateActionView actionView) {
        this.mContext = actionView.getContext();
        this.actionView = actionView;
    }

    @Override
    public ThingClasses getThingClassById(String id) {
        try {
            return interactor.findThingClassessById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Role getRoleById(String id) {
        try {
            return interactor.findRoleById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Plan getPlanById(String id) {
        try {
            return planInteractor.findPlanById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void save(Plan plan, Thing thing) {
        try {
            if(plan != null) {
                planInteractor.createOrUpdate(plan);
            }
            String userId = SharedPrefsUtil.getStringValue(mContext, Constant.SP_KEY_LOGIN_USER_ID, "");
            String userName = SharedPrefsUtil.getStringValue(mContext, Constant.SP_KEY_LOGIN_USER_NAME, "");
            String dateStr = DateUtil.toString(new Date(), Constant.DATETIME_FORMAT_6);
            if(thing.getId() == null) {
                thing.setId(StringUtil.getUUID());
                thing.setCreateBy(userName);
                thing.setCreateAt(dateStr);
            }
            thing.setUserId(userId);
            thing.setUpdateAt(dateStr);
            thing.setUpdateBy(userName);
            if(StringUtil.isEmpty(thing.getStatus())) {
                thing.setStatus(Constant.THING_STATUS_NEW);
            }
            interactor.addThing(thing);
            actionView.saveSuccess("保存成功");
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("保存失败");
        }
    }

    @Override
    public void save(Thing thing) {
        try {
            String userId = SharedPrefsUtil.getStringValue(mContext, Constant.SP_KEY_LOGIN_USER_ID, "");
            String userName = SharedPrefsUtil.getStringValue(mContext, Constant.SP_KEY_LOGIN_USER_NAME, "");
            String dateStr = DateUtil.toString(new Date(), Constant.DATETIME_FORMAT_6);
            if(thing.getId() == null) {
                thing.setId(StringUtil.getUUID());
                thing.setCreateBy(userName);
                thing.setCreateAt(dateStr);
                thing.setHabitStatus(Constant.HABIT_STATUS_ACTION);
            }
            thing.setUserId(userId);
            thing.setUpdateAt(dateStr);
            thing.setUpdateBy(userName);

            if(StringUtil.isEmpty(thing.getStatus())) {
                thing.setStatus(Constant.THING_STATUS_NEW);
            }
            interactor.addThing(thing);
            actionView.saveSuccess("保存成功");
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("保存失败");
        }
    }
}
