package com.chris.utopia.module.system.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chris.utopia.R;
import com.chris.utopia.common.util.StringUtil;
import com.chris.utopia.common.view.BaseActivity;
import com.chris.utopia.entity.User;
import com.chris.utopia.module.system.presenter.LoginPresenter;
import com.google.inject.Inject;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by Chris on 2016/1/17.
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginActionView {

    @InjectView(R.id.loginAct_layout)
    private LinearLayout rootView;
    @InjectView(R.id.loginAct_name_ti)
    private TextInputLayout nameTi;
    @InjectView(R.id.loginAct_name_et)
    private EditText nameEt;
    @InjectView(R.id.loginAct_password_ti)
    private TextInputLayout passwordTi;
    @InjectView(R.id.loginAct_password_et)
    private EditText passwordEt;
    @InjectView(R.id.loginAct_login_bt)
    private Button loginButton;
    @InjectView(R.id.loginAct_register_tv)
    private TextView registerTv;

    @Inject
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initEvent();
    }

    public void initView() {
    }

    public void initEvent() {
        presenter.setActionView(this);
        registerTv.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    public void setToolBarTitle() {
        //toolbar.setTitle("登录");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginAct_register_tv:
                Intent intent = new Intent(getContext(), RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                break;
            case R.id.loginAct_login_bt:
                String name = nameEt.getText().toString();
                String password = passwordEt.getText().toString();
                boolean flag = true;
                if(StringUtil.isEmpty(name)) {
                    nameTi.setErrorEnabled(true);
                    nameTi.setError("用户名不能为空");
                    flag = false;
                }else {
                    nameTi.setErrorEnabled(false);
                    nameTi.setError(null);
                }
                if(StringUtil.isEmpty(password)) {
                    passwordTi.setErrorEnabled(true);
                    passwordTi.setError("密码不能为空");
                    flag = false;
                }else {
                    passwordTi.setErrorEnabled(false);
                    passwordTi.setError(null);
                }
                if(flag) {
                    presenter.login(new User(name, password, null));
                }
                break;
        }
    }

    @Override
    public void showLoginMessage(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void toMainPage() {
        this.finish();
        Intent intent = new Intent(getContext(), MainActivity2.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
    }
}
