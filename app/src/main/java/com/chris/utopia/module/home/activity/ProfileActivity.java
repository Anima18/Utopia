package com.chris.utopia.module.home.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chris.utopia.R;
import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.CommonUtil;
import com.chris.utopia.common.util.SharedPrefsUtil;
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
public class ProfileActivity extends BaseFragment /*implements ProfileActionView*/ {

    private RecyclerView dataRv;

    private String userName;
    private ProfileAdapter adapter;
    private List<String> dataList = new ArrayList<>();
    private MaterialDialog resetPwdDialog;
    private View view;

    /*@Inject
    private ProfilePresenter profilePresenter;*/

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

        String userName = SharedPrefsUtil.getStringValue(getContext(), Constant.SP_KEY_LOGIN_USER_NAME, "");
        toolbar.setTitle(userName);

        ImageView imageView = (ImageView) view.findViewById(R.id.pcAct_title_layout);
        String filePath = getContext().getFilesDir().getPath()+"/chris.jpg";
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, CommonUtil.getBitmapOption(3));
        if(bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
    }

    public void initData() {
        //profilePresenter.setActionView(this);

        dataList.add("个人信息");
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
                        Intent intent0 = new Intent(getContext(), InformationActivity.class);
                        startActivity(intent0);
                        getActivity().overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                        break;
                    case 1:


                        break;
                    case 2:
                        Intent intent = new Intent(getContext(), TimerActivity.class);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                        break;
                    case 3:
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



    /*@Override
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
    }*/
}
