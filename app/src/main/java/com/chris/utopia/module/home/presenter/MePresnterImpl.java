package com.chris.utopia.module.home.presenter;


import android.util.Log;

import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.entity.Idea;
import com.chris.utopia.entity.Plan;
import com.chris.utopia.entity.Role;
import com.chris.utopia.entity.Thing;
import com.chris.utopia.entity.ThingClasses;
import com.chris.utopia.entity.User;
import com.chris.utopia.entity.UserData;
import com.chris.utopia.module.home.activity.MeActionView;
import com.chris.utopia.module.home.interactor.ThingInteractor;
import com.chris.utopia.module.home.interactor.ThingInteractorImpl;
import com.chris.utopia.module.idea.interactor.IdeaInteractor;
import com.chris.utopia.module.idea.interactor.IdeaInteractorImpl;
import com.chris.utopia.module.plan.interactor.PlanInteractor;
import com.chris.utopia.module.plan.interactor.PlanInteractorImpl;
import com.chris.utopia.module.system.interactor.SystemInteractor;
import com.chris.utopia.module.system.interactor.SystemInteractorImpl;
import com.example.requestmanager.NetworkRequest;
import com.example.requestmanager.callBack.DataCallBack;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
        actionView.showProgress("正在同步中，请稍后");
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

                UserData data = new UserData(user, roleList, classesList, ideaList, planList, thingList);
                Gson gson = new Gson();
                String dataStr = gson.toJson(data);
                Log.i("Chris", dataStr);

                new NetworkRequest.Builder(actionView.getLifecycleProvider())
                        .url("http://192.168.1.103:8080/PhotoKnow/security/security_login.action")
                        .dataType(new TypeToken<String>(){}.getType())
                        .method(NetworkRequest.POST_TYPE)
                        .param("userData", dataStr)
                        .call(new DataCallBack<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.i("Chris", "onSuccess");
                                actionView.showMessage("同步数据成功");
                            }

                            @Override
                            public void onFailure(int code, String message) {
                                Log.i("Chris", "onFailure："+message);
                                actionView.showMessage("同步数据失败");
                            }

                            @Override
                            public void onCompleted() {
                                actionView.hideProgress();
                                Log.i("Chris", "onCompleted");
                            }
                        });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
