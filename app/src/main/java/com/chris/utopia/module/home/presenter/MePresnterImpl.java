package com.chris.utopia.module.home.presenter;


import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.DateUtil;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.entity.Idea;
import com.chris.utopia.entity.Plan;
import com.chris.utopia.entity.ResultData;
import com.chris.utopia.entity.Role;
import com.chris.utopia.entity.Thing;
import com.chris.utopia.entity.ThingClasses;
import com.chris.utopia.entity.User;
import com.chris.utopia.entity.UserData;
import com.chris.utopia.entity.UserSyncRecord;
import com.chris.utopia.module.home.activity.MeActionView;
import com.chris.utopia.module.home.interactor.ThingInteractor;
import com.chris.utopia.module.home.interactor.ThingInteractorImpl;
import com.chris.utopia.module.idea.interactor.IdeaInteractor;
import com.chris.utopia.module.idea.interactor.IdeaInteractorImpl;
import com.chris.utopia.module.plan.interactor.PlanInteractor;
import com.chris.utopia.module.plan.interactor.PlanInteractorImpl;
import com.chris.utopia.module.role.interactor.RoleInteractor;
import com.chris.utopia.module.role.interactor.RoleInteractorImpl;
import com.chris.utopia.module.system.interactor.SystemInteractor;
import com.chris.utopia.module.system.interactor.SystemInteractorImpl;
import com.example.requestmanager.NetworkRequest;
import com.example.requestmanager.callBack.DataCallBack;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.SQLException;
import java.util.Date;
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
    private RoleInteractor roleInteractor;

    public MePresnterImpl(MeActionView actionView) {
        this.actionView = actionView;
        this.interactor = new SystemInteractorImpl();
        this.ideaInteractor = new IdeaInteractorImpl();
        this.planInteractor = new PlanInteractorImpl();
        this.thingInteractor = new ThingInteractorImpl();
        this.roleInteractor = new RoleInteractorImpl();
    }

    public void checkDataVersion() {
        String userId = SharedPrefsUtil.getStringValue(actionView.getContext(), Constant.SP_KEY_LOGIN_USER_ID, null);
        TelephonyManager telephonyManager =(TelephonyManager)actionView.getContext().getSystemService( Context.TELEPHONY_SERVICE );
        String deviceId = telephonyManager.getDeviceId();
        String lastSyncTime = SharedPrefsUtil.getStringValue(actionView.getContext(), Constant.SP_KEY_LAST_SYNC_TIME, "");

        new NetworkRequest.Builder(actionView.getLifecycleProvider())
                .url("http://192.168.1.106:8080/PhotoKnow/security/security_checkDataVersion.action")
                .dataType(new TypeToken<ResultData>(){}.getType())
                .method(NetworkRequest.POST_TYPE)
                .param("userId", userId)
                .param("deviceId", deviceId)
                .param("lastSyncTime", lastSyncTime)
                .call(new DataCallBack<ResultData>() {
                    @Override
                    public void onSuccess(ResultData data) {
                        Log.i("Chris", "onSuccess");
                        int code = data.getCode();
                        if(code == 0) {
                            syncDataToLocal(data.getUserData());
                        }
                        syncData();
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

    public void syncDataToLocal(UserData userData) {
        String lastSyncTime = SharedPrefsUtil.getStringValue(actionView.getContext(), Constant.SP_KEY_LAST_SYNC_TIME, "0");

        try {
            if(userData == null) {
                return;
            }
            User user = userData.getUser();
            if(user != null && user.getUpdateAt().compareTo(lastSyncTime) > 0) {
                User DBUser = interactor.findUserById(user.getUserId());
                DBUser.setGender(user.getGender());
                DBUser.setUpdateAt(user.getUpdateAt());
                DBUser.setIntroduce(user.getIntroduce());
                DBUser.setPassword(user.getPassword());
                interactor.addUser(DBUser);
            }

            List<Idea> ideas = userData.getIdeaList();
            if(ideas != null ) {
                for(Idea idea : ideas) {
                    if(idea.getUpdateAt().compareTo(lastSyncTime) > 0) {
                        Idea DBIdea = ideaInteractor.findIdeaById(idea.getId());
                        if(DBIdea != null) {
                            DBIdea.setUpdateAt(idea.getUpdateAt());
                            DBIdea.setBackground(idea.getBackground());
                            DBIdea.setDescription(idea.getDescription());
                            DBIdea.setName(idea.getName());
                            DBIdea.setRemindTime(idea.getRemindTime());
                        }else {
                            DBIdea = idea;
                        }

                        ideaInteractor.addIdea(DBIdea);
                    }
                }
            }

            List<Role> roles = userData.getRoleList();
            if(roles != null) {
                for(Role role : roles) {
                    if(role.getUpdateAt().compareTo(lastSyncTime) > 0) {
                        Role DBRole = roleInteractor.findRoleById(role.getId());
                        if(DBRole != null) {
                            DBRole.setUpdateAt(role.getUpdateAt());
                            DBRole.setDescription(role.getDescription());
                            DBRole.setName(role.getName());
                        }else {
                            DBRole = role;
                        }

                        roleInteractor.addRole(DBRole);
                    }
                }
            }

            List<ThingClasses> thingClasses = userData.getClassesList();
            if(thingClasses != null) {
                for(ThingClasses classes : thingClasses) {
                    if(classes.getUpdateAt().compareTo(lastSyncTime) > 0) {
                        ThingClasses DBClasses = interactor.findThingClassessById(classes.getId());
                        if(DBClasses != null) {
                            DBClasses.setUpdateAt(classes.getUpdateAt());
                            DBClasses.setDescription(classes.getDescription());
                            DBClasses.setName(classes.getName());
                        }else {
                            DBClasses = classes;
                        }

                        interactor.addThingClassess(DBClasses);
                    }
                }
            }

            List<Plan> planList = userData.getPlanList();
            if(planList != null) {
                for(Plan plan : planList) {
                    if(plan.getUpdateAt().compareTo(lastSyncTime) > 0) {
                        Plan DBPlan = planInteractor.findPlanById(plan.getId());
                        if(DBPlan != null) {
                            DBPlan.setName(plan.getName());
                            DBPlan.setDescription(plan.getDescription());
                            DBPlan.setUpdateAt(plan.getUpdateAt());
                            DBPlan.setBeginDate(plan.getBeginDate());
                            DBPlan.setEndDate(plan.getEndDate());
                            DBPlan.setProgress(plan.getProgress());
                            DBPlan.setStatus(plan.getStatus());
                            DBPlan.setThingQuadrant(plan.getThingQuadrant());
                        }else {
                            DBPlan = plan;
                        }

                        planInteractor.createOrUpdate(DBPlan);
                    }
                }
            }

            List<Thing> things = userData.getThingList();
            if(things != null) {
                for(Thing thing : things) {
                    if(thing.getUpdateAt().compareTo(lastSyncTime) > 0) {
                        Thing DBThing = thingInteractor.findThingById(thing.getId());
                        if(DBThing != null) {
                            DBThing.setTitle(thing.getTitle());
                            DBThing.setDescription(thing.getDescription());
                            DBThing.setUpdateAt(thing.getUpdateAt());
                            DBThing.setBeginDate(thing.getBeginDate());
                            DBThing.setBeginTime(thing.getBeginTime());
                            DBThing.setProgress(thing.getProgress());
                            DBThing.setStatus(thing.getStatus());
                            DBThing.setThingQuadrant(thing.getThingQuadrant());
                            DBThing.setEndTime(thing.getEndTime());
                            DBThing.setHabitStatus(thing.getHabitStatus());
                            DBThing.setHoldOnDay(thing.getHoldOnDay());
                            DBThing.setType(thing.getType());
                            DBThing.setWhatDay(thing.getWhatDay());
                            DBThing.setPeriod(thing.getPeriod());
                            DBThing.setIsPrompting(thing.isPrompting());
                            DBThing.setSaidSomething(thing.getSaidSomething());
                        }else {
                            DBThing = thing;
                        }
                        thingInteractor.addThing(DBThing);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void syncData() {
        actionView.showProgress("正在同步中，请稍后");
        String userId = SharedPrefsUtil.getStringValue(actionView.getContext(), Constant.SP_KEY_LOGIN_USER_ID, null);
        TelephonyManager telephonyManager =(TelephonyManager)actionView.getContext().getSystemService( Context.TELEPHONY_SERVICE );
        String deviceId = telephonyManager.getDeviceId();
        final String syncDate = DateUtil.toString(new Date(), Constant.DATETIME_FORMAT_2);
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
                UserSyncRecord record = new UserSyncRecord(userId, deviceId, syncDate);
                data.setRecord(record);
                Gson gson = new Gson();
                String dataStr = gson.toJson(data);
                Log.i("Chris", dataStr);

                new NetworkRequest.Builder(actionView.getLifecycleProvider())
                        .url("http://192.168.1.106:8080/PhotoKnow/security/security_syncData.action")
                        .dataType(new TypeToken<ResultData>(){}.getType())
                        .method(NetworkRequest.POST_TYPE)
                        .param("userData", dataStr)
                        .call(new DataCallBack<ResultData>() {
                            @Override
                            public void onSuccess(ResultData data) {
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
                                SharedPrefsUtil.putStringValue(actionView.getContext(), Constant.SP_KEY_LAST_SYNC_TIME, syncDate);
                                Log.i("Chris", "onCompleted");
                            }
                        });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
