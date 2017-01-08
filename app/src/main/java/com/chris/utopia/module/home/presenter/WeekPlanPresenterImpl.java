package com.chris.utopia.module.home.presenter;

import android.content.Context;
import android.util.Log;

import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.CollectionUtil;
import com.chris.utopia.common.util.DateUtil;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.entity.Plan;
import com.chris.utopia.entity.Thing;
import com.chris.utopia.module.home.activity.WeekPlanActionView;
import com.chris.utopia.module.home.interactor.ThingInteractor;
import com.chris.utopia.module.home.interactor.ThingInteractorImpl;
import com.chris.utopia.module.plan.interactor.PlanInteractor;
import com.chris.utopia.module.plan.interactor.PlanInteractorImpl;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Chris on 2016/2/14.
 */
public class WeekPlanPresenterImpl implements WeekPlanPresenter {

    private WeekPlanActionView actionView;
    private Context mContext;

    private PlanInteractor interactor;
    private ThingInteractor thingInteractor;

    public WeekPlanPresenterImpl(WeekPlanActionView actionView) {
        this.actionView = actionView;
        this.mContext = actionView.getContext();
        interactor = new PlanInteractorImpl();
        thingInteractor = new ThingInteractorImpl();
    }

    @Override
    public void setActionView(WeekPlanActionView actionView) {
        this.actionView = actionView;
        this.mContext = actionView.getContext();
    }

    @Override
    public void loadWeekPlan() {
        try {
            String userId = SharedPrefsUtil.getStringValue(mContext, Constant.SP_KEY_LOGIN_USER_ID, "");
            Plan plan = new Plan();
            plan.setUserId(userId);
            List<String> dateList = DateUtil.getALlweekDays();
            Log.i("loadWeekPlan", DateUtil.getALlweekDayStr());
            for(String dateStr : dateList) {
                Log.i("loadWeekPlan", DateUtil.getWeek(dateStr));
            }
            List<Plan> planList = interactor.findWeekPlan(plan);
            if(CollectionUtil.isNotEmpty(planList)) {
                for(Plan plan1 : planList) {
                    List<Thing> thingList = thingInteractor.findWeekThing(plan1);
                    for(Thing thing : thingList) {
                        thing.setWhatDay(DateUtil.getWeek(thing.getBeginDate()));
                    }
                    plan1.setThingList(thingList);
                }
            }
            actionView.loadPlan(planList);
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("加载数据失败");
        }
    }
}
