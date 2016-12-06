package com.chris.utopia.module.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chris.utopia.R;
import com.chris.utopia.common.util.StringUtil;
import com.chris.utopia.common.view.BaseFragment;
import com.chris.utopia.common.view.DividerItemDecoration;
import com.chris.utopia.module.home.adapter.ProfileAdapter;
import com.chris.utopia.module.home.presenter.ProfilePresenter;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.ContentView;

/**
 * Created by Chris on 2016/2/29.
 */
@ContentView(R.layout.activity_profile)
public class ProfileActivity extends BaseFragment implements ProfileActionView {

    private RecyclerView dataRv;

    private String userName;
    private ProfileAdapter adapter;
    private List<String> dataList = new ArrayList<>();
    private MaterialDialog resetPwdDialog;
    private View view;

    @Inject
    private ProfilePresenter profilePresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.activity_profile, container, false);
            initView(view);
            initData();
            initEvent();
        }
        return view;
    }

    public void initView(View view) {
        dataRv = (RecyclerView) view.findViewById(R.id.profileAct_menu_rv);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.activity_toolBar);
        toolbar.setTitle("我");
    }

    public void initData() {
        profilePresenter.setActionView(this);

        dataList.add("更改密码");
        dataList.add("我的时间");
        dataList.add("时间分析");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        dataRv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.DIVIDER_TYPE_INSET, layoutManager.getOrientation()));
        dataRv.setLayoutManager(layoutManager);
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
                        getActivity().overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                        break;
                    case 2:
                        Intent intent2 = new Intent(getContext(), TimeAnalysisActivity.class);
                        startActivity(intent2);
                        getActivity().overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                        break;
                }
            }
        });
        dataRv.setAdapter(adapter);
    }

    public void initEvent() {}



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
