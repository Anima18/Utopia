package com.chris.utopia.module.plan.presenter;

import android.content.Context;

import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.entity.Plan;
import com.chris.utopia.entity.Thing;
import com.chris.utopia.module.home.interactor.ThingInteractor;
import com.chris.utopia.module.plan.activity.PlanActionView;
import com.chris.utopia.module.plan.interactor.PlanInteractor;
import com.google.inject.Inject;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Chris on 2016/2/12.
 */
public class PlanPresenterImpl implements PlanPresenter {

    private PlanActionView actionView;
    private Context mContext;

    @Inject
    private PlanInteractor interactor;
    @Inject
    private ThingInteractor thingInteractor;

    @Override
    public void setActionView(PlanActionView actionView) {
        this.actionView = actionView;
        this.mContext = actionView.getContext();
    }

    @Override
    public void loadPlan() {
        try {
            String userId = SharedPrefsUtil.getStringValue(mContext, Constant.SP_KEY_LOGIN_USER_ID, "");
            Plan plan = new Plan();
            plan.setUserId(userId);

            List<Plan> planList = interactor.findPlan(plan);
            actionView.loadPlan(planList);
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("获取数据失败");
        }
    }

    @Override
    public void updateStatus(Plan plan) {
        try {
            interactor.createOrUpdate(plan);
            Thing thing = new Thing();
            thing.setPlanId(plan.getId());

            List<Thing> thingList = thingInteractor.findThing(thing);
            for(Thing thing1 : thingList) {
                thing1.setStatus(plan.getStatus());
            }
            thingInteractor.addThing(thingList);

            actionView.updatePlanSuccess(plan);
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("更新数据失败");
        } catch (Exception e) {
            e.printStackTrace();
            actionView.showMessage("更新数据失败");
        }
    }

    @Override
    public void deletePlan(Plan plan) {
        try {
            Thing thing = new Thing();
            thing.setPlanId(plan.getId());
            thingInteractor.deleteThing(thing);

            interactor.deletePlan(plan);
            actionView.deletePlanSuccess(plan);
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("更新数据失败");
        }
    }
}
