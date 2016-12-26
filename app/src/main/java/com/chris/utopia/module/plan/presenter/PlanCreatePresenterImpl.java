package com.chris.utopia.module.plan.presenter;

import android.content.Context;

import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.DateUtil;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.entity.Idea;
import com.chris.utopia.entity.Plan;
import com.chris.utopia.entity.Role;
import com.chris.utopia.entity.Thing;
import com.chris.utopia.entity.ThingClasses;
import com.chris.utopia.module.home.interactor.ThingInteractor;
import com.chris.utopia.module.idea.interactor.IdeaInteractor;
import com.chris.utopia.module.plan.activity.PlanCreateActionView;
import com.chris.utopia.module.plan.interactor.PlanInteractor;
import com.google.inject.Inject;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by Chris on 2016/1/31.
 */
public class PlanCreatePresenterImpl implements PlanCreatePresenter {

    private PlanCreateActionView actionView;
    private Context mContext;

    @Inject
    private PlanInteractor planInteractor;
    @Inject
    private ThingInteractor thingInteractor;
    @Inject
    private IdeaInteractor ideaInteractor;

    @Override
    public void setActionView(PlanCreateActionView actionView) {
        this.actionView = actionView;
        this.mContext = actionView.getContext();
    }

    @Override
    public void loadThing(Plan plan) {
        try {
            Thing thing = new Thing();
            thing.setPlanId(plan.getId());
            thing.setUserId(SharedPrefsUtil.getIntValue(mContext, Constant.SP_KEY_LOGIN_USER_ID, 0));
            List<Thing> thingList = thingInteractor.findThing(thing);

            actionView.loadThing(thingList);
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("获取Thing失败");
        }
    }

    @Override
    public void save(Plan plan, Idea idea, boolean isClose) {
        try {
            int userId = SharedPrefsUtil.getIntValue(mContext, Constant.SP_KEY_LOGIN_USER_ID, 0);
            String userName = SharedPrefsUtil.getStringValue(mContext, Constant.SP_KEY_LOGIN_USER_NAME, "");
            String dateStr = DateUtil.toString(new Date(), Constant.DATETIME_FORMAT_6);
            if(plan.getId() == null) {
                plan.setCreateBy(userName);
                plan.setCreateAt(dateStr);
                //plan.setProgress(0+"%");
                plan.setProgress(0+"");
                plan.setBeginDate(dateStr);
                plan.setStatus(Constant.PLAN_STATUS_NEW);
            }
            plan.setUpdateBy(userName);
            plan.setUpdateAt(dateStr);
            plan.setUserId(userId);

            planInteractor.createOrUpdate(plan);
            if(idea != null) {
                ideaInteractor.delete(idea);
            }
            if(isClose) {
                actionView.saveSuccess("保存成功");
            }else {
                actionView.saveSuccessAndAddThing();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("保存失败");
        }
    }

    @Override
    public ThingClasses getThingClassById(Integer id) {
        try {
            return thingInteractor.findThingClassessById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Role getRoleById(Integer id) {
        try {
            return thingInteractor.findRoleById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
