package com.chris.utopia.module.system.presenter;

import android.content.Context;

import com.chris.utopia.common.util.StringUtil;
import com.chris.utopia.entity.ResultData;
import com.chris.utopia.entity.User;
import com.chris.utopia.module.system.activity.RegisterActionView;
import com.chris.utopia.module.system.interactor.SystemInteractor;
import com.chris.utopia.module.system.interactor.SystemInteractorImpl;
import com.example.requestmanager.NetworkRequest;
import com.example.requestmanager.callBack.DataCallBack;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.SQLException;

/**
 * Created by Chris on 2016/1/18.
 */
public class RegisterPresenterImpl implements RegisterPresenter {

    private RegisterActionView actionView;
    private Context context;

    private SystemInteractor interactor = new SystemInteractorImpl();

    public RegisterPresenterImpl(RegisterActionView actionView) {
        this.actionView = actionView;
        this.context = actionView.getContext();

        this.interactor = new SystemInteractorImpl();
    }

    @Override
    public void setActionView(RegisterActionView actionView) {
        this.actionView = actionView;
        this.context = actionView.getContext();
    }

    @Override
    public void register(final User user) {
        actionView.showProgress("正在注册中，请稍等...");
        user.setUserId(StringUtil.getUUID());
        Gson gson = new Gson();
        String userStr = gson.toJson(user);
        new NetworkRequest.Builder(actionView.getLifecycleProvider())
                .url("http://192.168.1.106:8080/PhotoKnow/security/security_register.action")
                .dataType(new TypeToken<ResultData>(){}.getType())
                .method(NetworkRequest.POST_TYPE)
                .param("user", userStr)
                .call(new DataCallBack<ResultData>() {
                    @Override
                    public void onSuccess(ResultData data) {
                        if(1 == data.getCode()) {
                            try {
                                interactor.addUser(user);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            actionView.toLoginPage(data.getInfo());
                        }else {
                            actionView.showRegisterMessage(data.getInfo());
                        }
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        actionView.showRegisterMessage(s);
                    }

                    @Override
                    public void onCompleted() {
                        actionView.hideProgress();
                    }
                });

        /*try {
            user.setUserId(StringUtil.getUUID());
            interactor.addUser(user);
            actionView.toLoginPage("注册成功");
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showRegisterMessage("注册失败");
        }*/
    }
}
