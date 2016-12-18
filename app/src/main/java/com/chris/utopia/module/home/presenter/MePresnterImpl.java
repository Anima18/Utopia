package com.chris.utopia2.module.home.presenter;

import com.chris.utopia2.common.constant.Constant;
import com.chris.utopia2.common.util.SharedPrefsUtil;
import com.chris.utopia2.entity.Idea;
import com.chris.utopia2.entity.Plan;
import com.chris.utopia2.entity.Role;
import com.chris.utopia2.entity.Thing;
import com.chris.utopia2.entity.ThingClasses;
import com.chris.utopia2.entity.User;
import com.chris.utopia2.module.home.activity.MeActionView;
import com.chris.utopia2.module.home.interactor.ThingInteractor;
import com.chris.utopia2.module.home.interactor.ThingInteractorImpl;
import com.chris.utopia2.module.idea.interactor.IdeaInteractor;
import com.chris.utopia2.module.idea.interactor.IdeaInteractorImpl;
import com.chris.utopia2.module.plan.interactor.PlanInteractor;
import com.chris.utopia2.module.plan.interactor.PlanInteractorImpl;
import com.chris.utopia2.module.system.interactor.SystemInteractor;
import com.chris.utopia2.module.system.interactor.SystemInteractorImpl;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Admin on 2016/12/18.
 */

public class MePresnterImpl implements MePresenter {

    private MeActionView actionView;
    private SystemInteractor interactor;
    private IdeaInteractor ideaInteractor;
    private PlanInteractor planInteractor;
    private ThingInteractor thingInteractor;

    public MePresnterImpl(MeActionView actionView) {
        this.actionView = actionView;
        this.interactor = new SystemInteractorImpl();
        this.ideaInteractor = new IdeaInteractorImpl();
        this.planInteractor = new PlanInteractorImpl();
        this.thingInteractor = new ThingInteractorImpl();
    }

    @Override
    public void syncData() {
        int userId = SharedPrefsUtil.getIntValue(actionView.getContext(), Constant.SP_KEY_LOGIN_USER_ID, 0);
        try {
            User user = interactor.findUserById(userId);
            if(user == null) {
                actionView.showMessage("获取用户数据失败");
            }else {
                Role role = new Role();
                role.setUserId(user.getUserId());
                List<Role> roleList = interactor.findRole(role);

                ThingClasses classes = new ThingClasses();
                classes.setUserId(user.getUserId());
                List<ThingClasses> classesList = interactor.findThingClassess(classes);

                Idea idea = new Idea();
                idea.setUserId(user.getUserId());
                List<Idea> ideaList = ideaInteractor.findIdea(idea);

                Plan plan = new Plan();
                plan.setUserId(user.getUserId());
                List<Plan> planList = planInteractor.findPlan(plan);

                Thing thing = new Thing();
                thing.setUserId(user.getUserId());
                List<Thing> thingList = thingInteractor.findThing(thing);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
