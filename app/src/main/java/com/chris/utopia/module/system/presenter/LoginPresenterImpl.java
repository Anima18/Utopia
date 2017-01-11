package com.chris.utopia.module.system.presenter;

import android.content.Context;

import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.CollectionUtil;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.entity.User;
import com.chris.utopia.module.system.activity.LoginActionView;
import com.chris.utopia.module.system.interactor.SystemInteractor;
import com.chris.utopia.module.system.interactor.SystemInteractorImpl;
import com.example.requestmanager.NetworkRequest;
import com.example.requestmanager.callBack.DataCallBack;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Chris on 2016/1/19.
 */
public class LoginPresenterImpl implements LoginPresenter {

    private LoginActionView actionView;
    private Context context;
    private SystemInteractor interactor = new SystemInteractorImpl();

    public LoginPresenterImpl(LoginActionView actionView) {
        this.actionView = actionView;
        this.context = actionView.getContext();
    }

    @Override
    public void setActionView(LoginActionView actionView) {
        this.actionView = actionView;
        this.context = actionView.getContext();
    }

    @Override
    public void login(User user) {

        try {
            List<User> userList = interactor.findUser(user);
            if(CollectionUtil.isNotEmpty(userList)) {
                User user1 = userList.get(0);
                SharedPrefsUtil.putStringValue(context, Constant.SP_KEY_LOGIN_USER_ID, user1.getUserId());
                SharedPrefsUtil.putStringValue(context, Constant.SP_KEY_LOGIN_USER_NAME, user1.getName());
                SharedPrefsUtil.putStringValue(context, Constant.SP_KEY_LOGIN_USER_EMAIL, user1.getEmail());
                actionView.toMainPage();
            }else {
                actionView.showLoginMessage("用户名或密码有误");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showLoginMessage("用户名或密码有误");
        }

        /*actionView.showProgress("正在登陆中，请稍等...");
        Gson gson = new Gson();
        String userStr = gson.toJson(user);
        new NetworkRequest.Builder(actionView.getLifecycleProvider())
                .url("http://192.168.1.106:8080/PhotoKnow/security/security_login.action")
                .dataType(new TypeToken<User>(){}.getType())
                .method(NetworkRequest.POST_TYPE)
                .param("user", userStr)
                .call(new DataCallBack<User>() {
                    @Override
                    public void onSuccess(User user1) {
                        if(user1 != null) {
                            //init login
                            SharedPrefsUtil.putStringValue(context, Constant.SP_KEY_LOGIN_USER_ID, user1.getUserId());
                            SharedPrefsUtil.putStringValue(context, Constant.SP_KEY_LOGIN_USER_NAME, user1.getName());
                            SharedPrefsUtil.putStringValue(context, Constant.SP_KEY_LOGIN_USER_EMAIL, user1.getEmail());
                            actionView.toMainPage();
                        }else {
                            actionView.showLoginMessage("用户名或密码有误");
                        }
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        actionView.showLoginMessage(s);
                    }

                    @Override
                    public void onCompleted() {
                        actionView.hideProgress();
                    }
                });*/
    }

}
