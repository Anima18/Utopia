package com.chris.utopia.module.role.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.chris.utopia.R;
import com.chris.utopia.common.util.StringUtil;
import com.chris.utopia.common.view.BaseActivity;
import com.chris.utopia.entity.Role;
import com.chris.utopia.module.role.presenter.RoleCreatePresenter;
import com.google.inject.Inject;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by Chris on 2016/1/25.
 */
@ContentView(R.layout.activity_role_create)
public class RoleCreateActivity extends BaseActivity implements RoleCreateActionView {

    @InjectView(R.id.rcAct_layout)
    private LinearLayout rootView;
    @InjectView(R.id.rcAct_title_et)
    private EditText titleEt;
    @InjectView(R.id.rcAct_desc_et)
    private EditText descEt;

    private Role role;

    @Inject
    private RoleCreatePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        role = (Role)getIntent().getSerializableExtra("role");
        initView();
        initData();
        presenter.setActionView(this);
    }

    public void initView() {
        setToolBarTitle();
    }

    public void initData() {
        if(role != null) {
            titleEt.setText(role.getName());
            descEt.setText(role.getDescription());
        }
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showCreateRoleSuccess(String message) {
        showMessage(message);
        new Handler().postDelayed(new Runnable(){
            public void run() {
                RoleCreateActivity.this.finish();
                overridePendingTransition(R.anim.push_in_left, R.anim.push_out_right);;
            }
        }, 1000);
    }

    @Override
    public void setToolBarTitle() {
        toolbar.setTitle("创建角色");
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
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                overridePendingTransition(R.anim.push_in_left, R.anim.push_out_right);
                return true;
            case R.id.rcAct_action_save:
                save();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void save() {
        String title = titleEt.getText().toString();
        String desc = descEt.getText().toString();
        boolean flag = true;

        if(StringUtil.isEmpty(title)) {
            showMessage("Title不能为空");
            flag = false;
        }
        if(StringUtil.isEmpty(desc)) {
            showMessage("Description不能为空");
            flag = false;
        }

        if(flag) {
            if(role == null) {
                role = new Role(title, desc);
            }else {
                role.setName(title);
                role.setDescription(desc);
            }
            presenter.saveRole(role);
        }
    }
}
