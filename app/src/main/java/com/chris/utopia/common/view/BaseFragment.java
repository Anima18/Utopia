package com.chris.utopia.common.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;

import com.chris.utopia.common.util.ProgressDialogUtil;

import roboguice.fragment.RoboFragment;

/**
 * Created by Chris on 2016/1/14.
 */
public class BaseFragment extends RoboFragment implements BaseActionView {

    protected ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void showMessage(String message) {}


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
