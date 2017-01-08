package com.chris.utopia.module.idea.presenter;

import android.content.Context;

import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.entity.Idea;
import com.chris.utopia.module.idea.activity.IdeaActionView;
import com.chris.utopia.module.idea.interactor.IdeaInteractor;
import com.chris.utopia.module.idea.interactor.IdeaInteractorImpl;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Chris on 2016/1/24.
 */
public class IdeaPresenterImpl implements IdeaPresenter {
    private IdeaActionView actionView;
    private Context mContext;

    private IdeaInteractor interactor = new IdeaInteractorImpl();

    @Override
    public void setActionView(IdeaActionView actionView) {
        this.actionView = actionView;
        this.mContext = actionView.getContext();
    }

    @Override
    public void loadIdeaData() {
        try {
            String userId = SharedPrefsUtil.getStringValue(mContext, Constant.SP_KEY_LOGIN_USER_ID, "");
            Idea idea = new Idea();
            idea.setUserId(userId);
            List<Idea> ideas = interactor.findIdea(idea);
            actionView.loadIdeaData(ideas);
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("获取Idea失败");
        }
    }
}
