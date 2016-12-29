package com.chris.utopia.module.home.presenter;

import android.content.Context;

import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.entity.Thing;
import com.chris.utopia.module.home.activity.MyHabitActionView;
import com.chris.utopia.module.home.interactor.ThingInteractor;
import com.google.inject.Inject;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Chris on 2016/2/21.
 */
public class HabitPresenterImpl implements HabitPresenter {

    private MyHabitActionView actionView;
    private Context mContext;

    @Inject
    private ThingInteractor interactor;

    @Override
    public void setActionView(MyHabitActionView actionView) {
        this.actionView = actionView;
        this.mContext =actionView.getContext();
    }

    @Override
    public void loadHabit() {
        try {
            String userId = SharedPrefsUtil.getStringValue(mContext, Constant.SP_KEY_LOGIN_USER_ID, "");
            Thing thing = new Thing();
            thing.setUserId(userId);
            thing.setType(Constant.THING_TYPE_HABIT);

            List<Thing> thingList = interactor.findHabit(thing);
            actionView.loadHabit(thingList);
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("获取数据失败");
        }
    }

    @Override
    public void updateHabit(Thing thing) {
        try {
            Thing habit = interactor.findThingById(thing.getId());
            habit.setHabitStatus(thing.getHabitStatus());
            interactor.addThing(habit);
            /*if(thing.getHabitStatus().equals(Constant.HABIT_STATUS_ACTION)) {
                AlarmManagerUtil.addAlarm(mContext, thing);
            }else if(thing.getHabitStatus().equals(Constant.HABIT_STATUS_PAUSE)) {
                AlarmManagerUtil.removeAlarm(mContext, thing);
            }*/
            actionView.updateSuccess(thing);
        }catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("更新数据失败");
        }
    }

    @Override
    public void deleteHabit(Thing thing) {
        try {
            interactor.deleteThing(thing);
            //AlarmManagerUtil.removeAlarm(mContext, thing);
            actionView.removeSuccess(thing);
        }catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("删除数据失败");
        }
    }
}
