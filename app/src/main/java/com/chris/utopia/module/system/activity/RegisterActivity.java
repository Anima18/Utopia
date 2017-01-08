package com.chris.utopia.module.system.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chris.utopia.R;
import com.chris.utopia.common.util.StringUtil;
import com.chris.utopia.common.view.BaseActivity2;
import com.chris.utopia.entity.User;
import com.chris.utopia.module.system.presenter.RegisterPresenter;
import com.chris.utopia.module.system.presenter.RegisterPresenterImpl;


/**
 * Created by Chris on 2016/1/17.
 */
public class RegisterActivity extends BaseActivity2 implements View.OnClickListener, RegisterActionView {

    /*@InjectView(R.id.registerAct_layout)
    private LinearLayout rootLayout;
    @InjectView(R.id.registerAct_name_ti)
    private TextInputLayout nameTextInput;
    @InjectView(R.id.registerAct_name_et)
    private EditText nameEt;
    @InjectView(R.id.registerAct_password_ti)
    private TextInputLayout passwordTextInput;
    @InjectView(R.id.registerAct_password_et)
    private EditText passwordEt;
    @InjectView(R.id.registerAct_email_ti)
    private TextInputLayout emailTextInput;
    @InjectView(R.id.registerAct_email_et)
    private EditText emailEt;
    @InjectView(R.id.registerAct_register_bt)
    private Button registerButton;
    @InjectView(R.id.tv_registerAct_login)
    private TextView loginTv;*/

    private LinearLayout rootLayout;
    private TextInputLayout nameTextInput;
    private EditText nameEt;
    private TextInputLayout passwordTextInput;
    private EditText passwordEt;
    private TextInputLayout emailTextInput;
    private EditText emailEt;
    private Button registerButton;
    private TextView loginTv;

    private RegisterPresenter presenter = new RegisterPresenterImpl(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutId(R.layout.activity_register);
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
    }

    public void initView() {
        rootLayout = (LinearLayout) findViewById(R.id.registerAct_layout);
        nameTextInput =(TextInputLayout) findViewById(R.id.registerAct_name_ti);
        nameEt = (EditText) findViewById(R.id.registerAct_name_et);
        passwordTextInput =(TextInputLayout) findViewById(R.id.registerAct_password_ti);
        passwordEt = (EditText) findViewById(R.id.registerAct_password_et);
        emailTextInput =(TextInputLayout) findViewById(R.id.registerAct_email_ti);
        emailEt = (EditText) findViewById(R.id.registerAct_email_et);
        registerButton = (Button) findViewById(R.id.registerAct_register_bt);
        loginTv = (TextView) findViewById(R.id.tv_registerAct_login);
    }

    public void initEvent() {
        registerButton.setOnClickListener(this);
        loginTv.setOnClickListener(this);
    }

    @Override
    public void setToolBarTitle() {
        //toolbar.setTitle("注册");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerAct_register_bt:
                String name = nameEt.getText().toString();
                String password = passwordEt.getText().toString();
                String email = emailEt.getText().toString();
                boolean flag = true;
                if(StringUtil.isEmpty(name)) {
                    nameTextInput.setErrorEnabled(true);
                    nameTextInput.setError("用户名不能为空");
                    flag = false;
                }else {
                    nameTextInput.setErrorEnabled(false);
                    nameTextInput.setError(null);
                }
                if(StringUtil.isEmpty(password)) {
                    passwordTextInput.setErrorEnabled(true);
                    passwordTextInput.setError("密码不能为空");
                    flag = false;
                }else {
                    passwordTextInput.setErrorEnabled(false);
                    passwordTextInput.setError(null);
                }
                if(StringUtil.isEmpty(email)) {
                    emailTextInput.setErrorEnabled(true);
                    emailTextInput.setError("邮件不能为空");
                    flag = false;
                }else {
                    emailTextInput.setErrorEnabled(false);
                    emailTextInput.setError(null);
                }

                if(flag) {
                    presenter.setActionView(this);
                    presenter.register(new User(name, password, email));
                }
                break;
            case R.id.tv_registerAct_login:
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                break;
        }
    }

    @Override
    public void toLoginPage(String message) {
        showRegisterMessage(message);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                RegisterActivity.this.finish();
            }
        }, 1000);
    }

    @Override
    public void showRegisterMessage(String message) {
        Snackbar.make(rootLayout, message, Snackbar.LENGTH_LONG).show();
    }
}
