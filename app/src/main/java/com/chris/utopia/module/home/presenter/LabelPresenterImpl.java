package com.chris.utopia.module.home.presenter;

import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.entity.ThingClasses;
import com.chris.utopia.module.home.activity.LabelActionView;
import com.chris.utopia.module.system.interactor.SystemInteractor;
import com.chris.utopia.module.system.interactor.SystemInteractorImpl;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Admin on 2016/12/11.
 */

public class LabelPresenterImpl implements LabelPresenter {

    private LabelActionView actionView;
    private SystemInteractor interactor;

    public LabelPresenterImpl(LabelActionView actionView) {
        this.actionView = actionView;
        this.interactor = new SystemInteractorImpl();
    }

    @Override
    public void loadLabel() {
        try {
            Integer userId = SharedPrefsUtil.getIntValue(actionView.getContext(), Constant.SP_KEY_LOGIN_USER_ID, 0);
            ThingClasses classes = new ThingClasses();
            classes.setUserId(userId);
            List<ThingClasses> classesList =  interactor.findThingClassess(classes);
            actionView.loadLabelData(classesList);
        }catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("获取事情类别失败");
        }

    }
}
