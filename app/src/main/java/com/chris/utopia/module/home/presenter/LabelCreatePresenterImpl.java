package com.chris.utopia.module.home.presenter;

import android.content.Context;

import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.DateUtil;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.common.util.StringUtil;
import com.chris.utopia.entity.ThingClasses;
import com.chris.utopia.module.home.activity.LabelCreateActionView;
import com.chris.utopia.module.system.interactor.SystemInteractor;
import com.chris.utopia.module.system.interactor.SystemInteractorImpl;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Chris on 2016/1/25.
 */
public class LabelCreatePresenterImpl implements LabelCreatePresenter {

    private LabelCreateActionView actionView;
    private Context context;
    private SystemInteractor interactor;

    public LabelCreatePresenterImpl(LabelCreateActionView actionView) {
        this.actionView = actionView;
        this.context = actionView.getContext();
        this.interactor = new SystemInteractorImpl();
    }

    @Override
    public void saveLabel(ThingClasses label) {
        try {
            String userId = SharedPrefsUtil.getStringValue(context, Constant.SP_KEY_LOGIN_USER_ID, "");
            String userName = SharedPrefsUtil.getStringValue(context, Constant.SP_KEY_LOGIN_USER_NAME, "");
            String dateStr = DateUtil.toString(new Date(), Constant.DATETIME_FORMAT_6);

            if(label.getId() == null) {
                label.setId(StringUtil.getUUID());
                label.setCreateAt(dateStr);
                label.setCreateBy(userName);
            }

            label.setUserId(userId);
            label.setUpdateBy(userName);
            label.setUpdateAt(dateStr);

            interactor.addThingClassess(label);
            actionView.showCreateLabelSuccess("创建事情分类成功");
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("创建角色失败");
        }
    }
}
