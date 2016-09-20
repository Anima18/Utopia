package com.chris.utopia.module.home.presenter;

import android.content.Context;

import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.DateUtil;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.entity.Thing;
import com.chris.utopia.module.home.activity.MessageActionView;
import com.chris.utopia.module.home.interactor.ThingInteractor;
import com.google.inject.Inject;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by Chris on 2016/2/28.
 */
public class MessagePresenterImpl implements MessagePresenter {

    private MessageActionView actionView;
    private Context mContext;

    @Inject
    private ThingInteractor interactor;

    @Override
    public void setActionView(MessageActionView actionView) {
        this.actionView = actionView;
        this.mContext = actionView.getContext();
    }

    @Override
    public void loadData() {
        try {
            int userId = SharedPrefsUtil.getIntValue(mContext, Constant.SP_KEY_LOGIN_USER_ID, 0);
            Thing thing = new Thing();
            thing.setUserId(userId);

            List<Thing> thingList = interactor.findMessage(thing);
            actionView.loadData(thingList);
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("获取数据失败");
        }
    }

    @Override
    public void update(Thing thing) {
        try {
            thing.setUpdateAt(DateUtil.toString(new Date(), Constant.DATETIME_FORMAT_6));
            interactor.addThing(thing);
            actionView.updateSuccess(thing);
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("更新数据失败");
        }
    }

    @Override
    public void clearAll(List<Thing> thingList) {
        try {
            String dateStr = DateUtil.toString(new Date(), Constant.DATETIME_FORMAT_6);
            for(Thing thing : thingList) {
                thing.setStatus(Constant.THING_STATUS_IGNORE);
                thing.setUpdateAt(dateStr);
            }
            interactor.addThing(thingList);
            actionView.clearAllSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            actionView.showMessage("更新数据失败");
        }
    }
}
