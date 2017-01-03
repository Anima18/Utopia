package com.chris.utopia.common.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.chris.utopia.R;
import com.chris.utopia.common.util.ProgressDialogUtil;

/**
 * Created by Chris on 2015/9/2.
 */
public abstract class BaseActivity extends RoboAppCompatActivity implements BaseActionView {
    protected Toolbar toolbar;
    protected ProgressDialog progressDialog;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStatusBar();
        initToolBar();
        ViewGroup content = (ViewGroup)findViewById(android.R.id.content);
        rootView = content.getChildAt(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setToolBarTitle();
    }

    public void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            /*SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.primary_dark);*/
            Window window = getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            //设置状态栏颜色
            window.setStatusBarColor(getResources().getColor(R.color.primary_dark));
        }
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.activity_toolBar);
        setSupportActionBar(toolbar);

    }
    @Override
    public Context getContext() {
        return BaseActivity.this;
    }

    @Override
    public void showProgress(String message) {
        if(progressDialog == null) {
            progressDialog = ProgressDialogUtil.showProgessDialog(getContext(), message);
        }else {
            progressDialog.setMessage(message);
        }
        progressDialog.show();
    }

    @Override
    public void hideProgress() {

        new Handler().postDelayed(new Runnable() {
            public void run() {
                progressDialog.cancel();
            }
        }, 500);
    }

    @Override
    public void showMessage(String message) {

        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }

    public abstract void setToolBarTitle();

    @Override
    public void onBackPressed() {
        this.finish();
        overridePendingTransition(R.anim.push_in_left, R.anim.push_out_right);
        return;
    }
}
