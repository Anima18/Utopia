package com.chris.utopia.common.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.chris.utopia.R;
import com.chris.utopia.common.util.ProgressDialogUtil;
import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by Chris on 2015/9/2.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseActionView, LifecycleProvider<ActivityEvent> {
    protected Toolbar toolbar;
    protected ProgressDialog progressDialog;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initStatusBar();
        initToolBar();
        ViewGroup content = (ViewGroup)findViewById(android.R.id.content);
        rootView = content.getChildAt(0);
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
    public LifecycleProvider getLifecycleProvider() {
        return this;
    }

    @Override
    public void showProgress(String message) {
        if(progressDialog == null) {
            progressDialog = ProgressDialogUtil.showProgessDialog(getContext(), message);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    lifecycleSubject.onNext(ActivityEvent.PAUSE);
                    lifecycleSubject.onNext(ActivityEvent.CREATE);
                }
            });
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

    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    @Override
    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        setToolBarTitle();
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    @CallSuper
    public void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @Override
    @CallSuper
    public void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    @Override
    @CallSuper
    public void onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
    }
}
