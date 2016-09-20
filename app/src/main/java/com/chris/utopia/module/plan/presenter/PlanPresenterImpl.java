package com.chris.utopia.module.plan.presenter;

import android.content.Context;

import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.entity.Plan;
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

    @Override
    public void setActionView(PlanActionView actionView) {
        this.actionView = actionView;
        this.mContext = actionView.getContext();
    }

    @Override
    public void loadPlan() {
        try {
            Integer userId = SharedPrefsUtil.getIntValue(mContext, Constant.SP_KEY_LOGIN_USER_ID, 0);
            Plan plan = new Plan();
            plan.setUserId(userId);

            List<Plan> planList = interactor.findPlan(plan);
            actionView.loadPlan(planList);
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("获取数据失败");
        }
    }
}
