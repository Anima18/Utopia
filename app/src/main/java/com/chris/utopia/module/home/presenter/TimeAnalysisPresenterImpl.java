package com.chris.utopia.module.home.presenter;

import android.content.Context;

import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.CollectionUtil;
import com.chris.utopia.common.util.CommonUtil;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.entity.Thing;
import com.chris.utopia.module.home.activity.TimeAnalysisActionView;
import com.chris.utopia.module.home.interactor.ThingInteractor;
import com.chris.utopia.module.system.interactor.SystemInteractor;
import com.google.inject.Inject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Chris on 2016/3/12.
 */
public class TimeAnalysisPresenterImpl implements TimeAnalysisPresenter {

    private TimeAnalysisActionView actionView;
    private Context mContext;

    @Inject
    private ThingInteractor interactor;
    @Inject
    private SystemInteractor systemInteractor;

    @Override
    public void setActionView(TimeAnalysisActionView actionView) {
        this.actionView = actionView;
        this.mContext = actionView.getContext();
    }

    @Override
    public void loadTimeFinishData() {
        try {
            int userId = SharedPrefsUtil.getIntValue(mContext, Constant.SP_KEY_LOGIN_USER_ID, 0);
            Thing thing = new Thing();
            thing.setUserId(userId);

            List<Thing> thingList = interactor.findThing(thing);
            List<Thing> finishThingList = new ArrayList<>();
            List<Thing> ignoreThingList = new ArrayList<>();
            List<Thing> unFinishThingList = new ArrayList<>();
            for(Thing t : thingList) {
                if(Constant.THING_STATUS_DONE.equals(t.getStatus())) {
                    finishThingList.add(t);
                }else if(Constant.THING_STATUS_IGNORE.equals(t.getStatus())) {
                    ignoreThingList.add(t);
                }else if(Constant.THING_STATUS_NEW.equals(t.getStatus())) {
                    unFinishThingList.add(t);
                }
            }
            Map<String, Integer> dataMap = new HashMap<>();
            dataMap.put("已完成", CommonUtil.percent(finishThingList.size(), thingList.size()));
            dataMap.put("未完成", CommonUtil.percent(unFinishThingList.size(), thingList.size()));
            dataMap.put("忽略", CommonUtil.percent(ignoreThingList.size(), thingList.size()));
            actionView.loadDate(dataMap);
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("获取数据失败");
        }
    }

    @Override
    public void loadTimeRoleData() {
        try{
            int userId = SharedPrefsUtil.getIntValue(mContext, Constant.SP_KEY_LOGIN_USER_ID, 0);
            Thing thing = new Thing();
            thing.setUserId(userId);

            List<Thing> thingList = interactor.findThing(thing);
            Map<Integer, List<Thing>> dataIdMap = new HashMap<>();
            for(Thing t : thingList) {
                if(CollectionUtil.isNotEmpty(dataIdMap.get(t.getRoleId()))) {
                    dataIdMap.get(t.getRoleId()).add(t);
                }else {
                    List<Thing> thingList1 = new ArrayList<>();
                    thingList1.add(t);
                    dataIdMap.put(t.getRoleId(), thingList1);
                }
            }
            Map<String, Integer> dataMap = new HashMap<>();
            Iterator<Integer> it = dataIdMap.keySet().iterator();
            while (it.hasNext()) {
                int key = it.next();
                dataMap.put(systemInteractor.findRoleById(key).getName(), CommonUtil.percent(dataIdMap.get(key).size(), thingList.size()));
            }
            actionView.loadDate(dataMap);
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("获取数据失败");
        }
    }

    @Override
    public void loadTimeClassesData() {
        try {
            int userId = SharedPrefsUtil.getIntValue(mContext, Constant.SP_KEY_LOGIN_USER_ID, 0);
            Thing thing = new Thing();
            thing.setUserId(userId);

            List<Thing> thingList = interactor.findThing(thing);
            Map<Integer, List<Thing>> dataIdMap = new HashMap<>();
            for(Thing t : thingList) {
                if(CollectionUtil.isNotEmpty(dataIdMap.get(t.getClassessId()))) {
                    dataIdMap.get(t.getClassessId()).add(t);
                }else {
                    List<Thing> thingList1 = new ArrayList<>();
                    thingList1.add(t);
                    dataIdMap.put(t.getClassessId(), thingList1);
                }
            }
            Map<String, Integer> dataMap = new HashMap<>();
            Iterator<Integer> it = dataIdMap.keySet().iterator();
            while (it.hasNext()) {
                int key = it.next();
                dataMap.put(systemInteractor.findThingClassessById(key).getName(), CommonUtil.percent(dataIdMap.get(key).size(), thingList.size()));
            }
            actionView.loadDate(dataMap);
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("获取数据失败");
        }
    }

    @Override
    public void loadTimeQuadrantData() {
        try {
            int userId = SharedPrefsUtil.getIntValue(mContext, Constant.SP_KEY_LOGIN_USER_ID, 0);
            Thing thing = new Thing();
            thing.setUserId(userId);

            List<Thing> thingList = interactor.findThing(thing);
            Map<String, List<Thing>> dataIdMap = new HashMap<>();
            for(Thing t : thingList) {
                if(CollectionUtil.isNotEmpty(dataIdMap.get(t.getThingQuadrant()))) {
                    dataIdMap.get(t.getThingQuadrant()).add(t);
                }else {
                    List<Thing> thingList1 = new ArrayList<>();
                    thingList1.add(t);
                    dataIdMap.put(t.getThingQuadrant(), thingList1);
                }
            }
            Map<String, Integer> dataMap = new HashMap<>();
            Iterator<String> it = dataIdMap.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                dataMap.put(key, CommonUtil.percent(dataIdMap.get(key).size(), thingList.size()));
            }
            actionView.loadDate(dataMap);
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("获取数据失败");
        }
    }

    @Override
    public void loadTimePlanData() {
        try {
            int userId = SharedPrefsUtil.getIntValue(mContext, Constant.SP_KEY_LOGIN_USER_ID, 0);
            Thing thing = new Thing();
            thing.setUserId(userId);

            List<Thing> thingList = interactor.findThing(thing);
            List<Thing> planThingList = new ArrayList<>();
            List<Thing> otherThingList = new ArrayList<>();
            for(Thing t : thingList) {
                if(t.getPlanId() != null) {
                    planThingList.add(t);
                }else {
                    otherThingList.add(t);
                }
            }
            Map<String, Integer> dataMap = new HashMap<>();
            dataMap.put("计划", CommonUtil.percent(planThingList.size(), thingList.size()));
            dataMap.put("其他", CommonUtil.percent(otherThingList.size(), thingList.size()));
            actionView.loadDate(dataMap);
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("获取数据失败");
        }
    }
}
