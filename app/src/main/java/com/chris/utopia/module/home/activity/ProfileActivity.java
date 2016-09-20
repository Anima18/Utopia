package com.chris.utopia.module.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chris.utopia.R;
import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.common.util.StringUtil;
import com.chris.utopia.common.view.BaseActivity;
import com.chris.utopia.common.view.DividerItemDecoration;
import com.chris.utopia.module.home.adapter.ProfileAdapter;
import com.chris.utopia.module.home.presenter.ProfilePresenter;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by Chris on 2016/2/29.
 */
@ContentView(R.layout.activity_profile)
public class ProfileActivity extends BaseActivity implements ProfileActionView {

    @InjectView(R.id.profileAct_user_im)
    private CircleImageView userIm;
    @InjectView(R.id.profileAct_userName_tv)
    private TextView userNameTv;
    @InjectView(R.id.profileAct_menu_rv)
    private RecyclerView dataRv;

    private String userName;
    private ProfileAdapter adapter;
    private List<String> dataList = new ArrayList<>();
    private MaterialDialog resetPwdDialog;

    @Inject
    private ProfilePresenter profilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userName = SharedPrefsUtil.getStringValue(getContext(), Constant.SP_KEY_LOGIN_USER_NAME, "");

        initView();
        initData();
        initEvent();
    }

    public void initView() {
        setToolBarTitle();
    }

    public void initData() {
        profilePresenter.setActionView(this);
        userNameTv.setText(userName);

        dataList.add("更改密码");
        dataList.add("我的时间");
        dataList.add("时间分析");
        adapter = new ProfileAdapter(getContext(), dataList);
        adapter.setOnItemClickListener(new ProfileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                switch (position) {
                    case 0:
                        resetPwdDialog = new MaterialDialog.Builder(getContext())
                                .title("更改密码")
                                .customView(R.layout.dialog_reset_password, true)
                                .positiveText("确认")
                                .show();
                        View view = resetPwdDialog.getCustomView();
                        final TextInputLayout pwTi = (TextInputLayout) view.findViewById(R.id.resetPasswordDialog_password_textInput);
                        final EditText pwEt = (EditText) view.findViewById(R.id.resetPasswordDialog_password_et);
                        final TextInputLayout npwTi = (TextInputLayout) view.findViewById(R.id.resetPasswordDialog_new_password_textInput);
                        final EditText npwEt = (EditText) view.findViewById(R.id.resetPasswordDialog_new_password_et);
                        final TextInputLayout cpwTi = (TextInputLayout) view.findViewById(R.id.resetPasswordDialog_confirm_password_textInput);
                        final EditText cpwEt = (EditText) view.findViewById(R.id.resetPasswordDialog_confirm_password_et);

                        resetPwdDialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean flag = true;
                                String pwd = pwEt.getText().toString();
                                String newPwd = npwEt.getText().toString();
                                String comfirmPwd = cpwEt.getText().toString();

                                if(StringUtil.isEmpty(pwd)) {
                                    pwTi.setErrorEnabled(true);
                                    pwTi.setError("旧密码不能为空");
                                    flag = false;
                                }else {
                                    pwTi.setErrorEnabled(false);
                                    pwTi.setError(null);
                                }
                                if(StringUtil.isEmpty(newPwd)) {
                                    npwTi.setErrorEnabled(true);
                                    npwTi.setError("密码不能为空");
                                    flag = false;
                                }else {
                                    npwTi.setErrorEnabled(false);
                                    npwTi.setError(null);
                                }
                                if(StringUtil.isEmpty(comfirmPwd)) {
                                    cpwTi.setErrorEnabled(true);
                                    cpwTi.setError("确认密码不能为空");
                                    flag = false;
                                }else {
                                    cpwTi.setErrorEnabled(false);
                                    cpwTi.setError(null);
                                }
                                if(StringUtil.isNotEmpty(newPwd) && StringUtil.isNotEmpty(comfirmPwd)) {
                                    if(!newPwd.equals(comfirmPwd)) {
                                        cpwTi.setErrorEnabled(true);
                                        cpwTi.setError("确认密码不正确");
                                        flag = false;
                                    }else {
                                        cpwTi.setErrorEnabled(false);
                                        cpwTi.setError(null);
                                    }
                                }

                                if(flag) {
                                    profilePresenter.resetPassword(pwd, newPwd);
                                }
                            }
                        });

                        break;
                    case 1:
                        Intent intent = new Intent(getContext(), TimerActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                        break;
                    case 2:
                        Intent intent2 = new Intent(getContext(), TimeAnalysisActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                        break;
                }
            }
        });
        dataRv.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        dataRv.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
        dataRv.setLayoutManager(layoutManager);
    }

    public void initEvent() {}

    @Override
    public void setToolBarTitle() {
        toolbar.setTitle(userName);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                overridePendingTransition(R.anim.push_in_left, R.anim.push_out_right);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showResetPasswordFail(String message) {
        resetPwdDialog.dismiss();
        new MaterialDialog.Builder(getContext())
                .title(R.string.dialog_title)
                .content(message)
                .show();
    }

    @Override
    public void showResetPasswordSuccess(String message) {
        resetPwdDialog.dismiss();
        showMessage(message);
    }

    @Override
    public void showPasswordError(String message) {
        View view = resetPwdDialog.getCustomView();
        TextInputLayout pwTi = (TextInputLayout) view.findViewById(R.id.resetPasswordDialog_password_textInput);
        pwTi.setErrorEnabled(true);
        pwTi.setError(message);
    }
}
