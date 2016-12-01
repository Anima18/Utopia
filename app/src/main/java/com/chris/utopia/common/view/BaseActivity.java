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

            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.primary);
        }
    }

    public void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.activity_toolBar);
        // App Logo
        //toolbar.setLogo(R.mipmap.ic_launcher);
        // Title

        // Sub Title
        //toolbar.setSubtitle("Sub title");
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
