package com.chris.utopia.module.home.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chris.utopia.R;
import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.CommonUtil;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.common.view.BaseActivity;
import com.chris.utopia.common.view.BaseFragment;
import com.chris.utopia.common.view.DividerItemDecoration;
import com.chris.utopia.module.home.adapter.ProfileAdapter;
import com.chris.utopia.module.home.presenter.MePresenter;
import com.chris.utopia.module.home.presenter.MePresnterImpl;
import com.chris.utopia.module.role.activity.RoleActivity;
import com.chris.utopia.module.system.activity.LoginActivity;
import com.trello.rxlifecycle.LifecycleProvider;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.ContentView;

/**
 * Created by Chris on 2016/2/29.
 */
@ContentView(R.layout.activity_profile)
public class ProfileActivity extends BaseFragment implements MeActionView {

    private RecyclerView dataRv;
    private RecyclerView settingRv;

    private String userName;
    private ProfileAdapter adapter;
    private ProfileAdapter settingAdapter;
    private List<String> dataList = new ArrayList<>();
    private List<Integer> resList = new ArrayList<>();
    private List<String> settingList = new ArrayList<>();
    private List<Integer> settingResList = new ArrayList<>();
    private MaterialDialog resetPwdDialog;
    private View view;

    private MePresenter presenter = new MePresnterImpl(this);

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
        settingRv = (RecyclerView) view.findViewById(R.id.profileAct_menu_rv2);
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
        resList.add(R.drawable.ic_user);
        dataList.add("事情分类");
        resList.add(R.drawable.ic_label_disable);
        dataList.add("我的角色");
        resList.add(R.drawable.ic_role_group);
        dataList.add("我的时间");
        resList.add(R.drawable.ic_time);
        dataList.add("时间分析");
        resList.add(R.drawable.ic_analyze);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        dataRv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.DIVIDER_TYPE_INSET, layoutManager.getOrientation()));
        dataRv.setLayoutManager(layoutManager);
        adapter = new ProfileAdapter(getContext(), dataList, resList);
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
                        Intent labelIntent = new Intent(getContext(), ThingLabelActivity.class);
                        startActivity(labelIntent);
                        getActivity().overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                        break;
                    case 2:
                        Intent roleIntent = new Intent(getContext(), RoleActivity.class);
                        startActivity(roleIntent);
                        getActivity().overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                        break;
                    case 3:
                        Intent intent = new Intent(getContext(), TimerActivity.class);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                        break;
                    case 4:
                        Intent intent2 = new Intent(getContext(), TimeAnalysisActivity.class);
                        startActivity(intent2);
                        getActivity().overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                        break;
                }
            }
        });
        dataRv.setAdapter(adapter);


        settingList.add("数据同步");
        settingResList.add(R.drawable.ic_palette);
        settingList.add("退出");
        settingResList.add(R.drawable.ic_palette);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        settingRv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.DIVIDER_TYPE_INSET, layoutManager2.getOrientation()));
        settingRv.setLayoutManager(layoutManager2);
        settingAdapter = new ProfileAdapter(getContext(), settingList, settingResList);
        settingAdapter.setOnItemClickListener(new ProfileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                switch (position) {
                    case 0:
                        presenter.checkDataVersion();
                        break;
                    case 1:
                        SharedPrefsUtil.clear(getContext());
                        Intent intent0 = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent0);
                        getActivity().finish();
                        getActivity().overridePendingTransition(R.anim.push_in_left, R.anim.push_out_right);
                        break;

                }
            }
        });
        settingRv.setAdapter(settingAdapter);
    }

    public void initEvent() {}

    @Override
    public void syncFail() {
        Log.i("Chris", "dddddddddddddddddd");
        Snackbar.make(view, "同步数据失败", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void syncSuccess() {
        Snackbar.make(view, "同步数据成功", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public LifecycleProvider getLifecycleProvider() {
        return this;
    }
}
