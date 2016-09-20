package com.chris.utopia.module.idea.presenter;

import android.content.Context;

import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.DateUtil;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.entity.Idea;
import com.chris.utopia.entity.ThingClasses;
import com.chris.utopia.module.idea.activity.IdeaCreateActionView;
import com.chris.utopia.module.idea.interactor.IdeaInteractor;
import com.google.inject.Inject;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Chris on 2016/1/22.
 */
public class IdeaCreatePresenterImpl implements IdeaCreatePresenter {

    private IdeaCreateActionView actionView;
    private Context mContext;

    @Inject
    private IdeaInteractor interactor;

    @Override
    public void setActionView(IdeaCreateActionView actionView) {
        this.actionView = actionView;
        this.mContext = actionView.getContext();
    }

    @Override
    public ThingClasses getThingClassById(Integer id) {
        try {
            return interactor.findThingClassessById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addIdea(Idea idea) {
        try {
            int userId = SharedPrefsUtil.getIntValue(mContext, Constant.SP_KEY_LOGIN_USER_ID, 0);
            String userName = SharedPrefsUtil.getStringValue(mContext, Constant.SP_KEY_LOGIN_USER_NAME, "");
            idea.setUserId(userId);
            idea.setCreateAt(DateUtil.toString(new Date(), Constant.DATETIME_FORMAT_6));
            idea.setCreateBy(userName);
            idea.setUpdateAt(DateUtil.toString(new Date(), Constant.DATETIME_FORMAT_6));
            idea.setUpdateBy(userName);
            interactor.addIdea(idea);
            actionView.saveIdeaMessage("保存Idea成功");
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.saveIdeaMessage("保存Idea失败");
        }
    }
}
