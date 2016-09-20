package com.chris.utopia.module.home.presenter;

import android.content.Context;

import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.AlarmManagerUtil;
import com.chris.utopia.common.util.DateUtil;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.common.util.StringUtil;
import com.chris.utopia.entity.Thing;
import com.chris.utopia.module.home.activity.TodayTaskActionView;
import com.chris.utopia.module.home.interactor.ThingInteractor;
import com.google.inject.Inject;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by Chris on 2016/2/13.
 */
public class TodayTaskPresenterImpl implements TodayTaskPresenter {

    private TodayTaskActionView actionView;
    private Context mContext;

    @Inject
    private ThingInteractor interactor;

    @Override
    public void setActionView(TodayTaskActionView actionView) {
        this.actionView = actionView;
        this.mContext = actionView.getContext();
    }

    @Override
    public void loadTodayTask() {
        try {
            Integer userId = SharedPrefsUtil.getIntValue(mContext, Constant.SP_KEY_LOGIN_USER_ID, 0);
            String today = DateUtil.toString(new Date(), Constant.DATETIME_FORMAT_4);
            Thing thing = new Thing();
            thing.setUserId(userId);
            thing.setBeginDate(today);
            thing.setType(Constant.THING_TYPE_THING);
            List<Thing> thingList = interactor.findThing(thing);

            Thing habit = new Thing();
            habit.setUserId(userId);
            habit.setWhatDay(DateUtil.getWeek(new Date()));
            habit.setType(Constant.THING_TYPE_HABIT);
            List<Thing> habits = interactor.findHabitOneDay(habit);

            String todayStr = DateUtil.toString(new Date(), Constant.DATETIME_FORMAT_4);
            for(Thing thing1 : habits) {
                if(StringUtil.isEmpty(thing1.getUpdateAt()) || !todayStr.equals(thing1.getUpdateAt().substring(0, 10))) {
                    thing1.setStatus(Constant.THING_STATUS_NEW);
                }
            }

            thingList.addAll(habits);
            actionView.loadTodayTask(thingList);
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("获取数据失败");
        }
    }

    @Override
    public void updateThing(Thing thing) {
        try {
            if(Constant.THING_TYPE_HABIT.equals(thing.getType())) {
                Thing habit = interactor.findThingById(thing.getId());
                habit.setStatus(thing.getStatus());
                habit.setHoldOnDay(habit.getHoldOnDay() == null ? 0 : habit.getHoldOnDay() + 1);
                habit.setUpdateAt(DateUtil.toString(new Date(), Constant.DATETIME_FORMAT_6));
                interactor.addThing(habit);
            }else {
                thing.setUpdateAt(DateUtil.toString(new Date(), Constant.DATETIME_FORMAT_6));
                interactor.addThing(thing);
            }

            if(thing.getType().equals(Constant.THING_TYPE_THING)) {
                if(thing.getStatus().equals(Constant.THING_STATUS_NEW)) {
                    AlarmManagerUtil.addAlarm(mContext, thing);
                }else {
                    AlarmManagerUtil.removeAlarm(mContext, thing);
                }
            }

            actionView.updateTodayTask(thing);
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("更新失败");
        }
    }

    @Override
    public void deleteThing(Thing thing) {
        try {
            interactor.deleteThing(thing);
            AlarmManagerUtil.removeAlarm(mContext, thing);
            actionView.removeTodayTask(thing);
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("删除失败");
        }
    }
}
