package com.chris.utopia.module.home.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.chris.utopia.R;
import com.chris.utopia.common.view.BaseActivity;

/**
 * Created by jianjianhong on 2016/12/7.
 */

public class InformationActivity extends BaseActivity {

    private TextInputLayout nameTi;
    private EditText nameEt;
    private TextInputLayout emailTi;
    private EditText emailEt;
    private TextInputLayout introduceTi;
    private EditText introduceEt;
    private CheckBox manCb;
    private CheckBox womenCb;
    private LinearLayout passwordView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        initView();
    }

    private void initView() {
        nameTi = (TextInputLayout) findViewById(R.id.ti_info_name);
        emailTi = (TextInputLayout) findViewById(R.id.ti_info_email);
        introduceTi = (TextInputLayout) findViewById(R.id.ti_info_introduce);
        nameEt = (EditText) findViewById(R.id.et_info_name);
        emailEt = (EditText) findViewById(R.id.et_info_email);
        introduceEt = (EditText) findViewById(R.id.et_info_introduce);
        manCb = (CheckBox) findViewById(R.id.cb_info_man);
        womenCb = (CheckBox) findViewById(R.id.cb_info_women);
        passwordView = (LinearLayout) findViewById(R.id.view_info_password);
    }

    public void initData() {

    }

    @Override
    public void setToolBarTitle() {
        toolbar = (Toolbar) findViewById(R.id.activity_toolBar);
        toolbar.setTitle("编辑个人信息");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_role_create, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
