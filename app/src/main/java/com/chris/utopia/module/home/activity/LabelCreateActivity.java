package com.chris.utopia.module.home.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.chris.utopia.R;
import com.chris.utopia.common.util.StringUtil;
import com.chris.utopia.common.view.BaseActivity2;
import com.chris.utopia.entity.ThingClasses;
import com.chris.utopia.module.home.presenter.LabelCreatePresenter;
import com.chris.utopia.module.home.presenter.LabelCreatePresenterImpl;

/**
 * Created by Chris on 2016/1/25.
 */
public class LabelCreateActivity extends BaseActivity2 implements LabelCreateActionView {

    private LinearLayout rootView;
    private EditText titleEt;
    private EditText descEt;

    private ThingClasses label;

    private LabelCreatePresenter presenter = new LabelCreatePresenterImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutId(R.layout.activity_role_create);
        super.onCreate(savedInstanceState);

        label = (ThingClasses)getIntent().getSerializableExtra("label");
        initView();
        initData();
    }

    public void initView() {
        rootView = (LinearLayout) findViewById(R.id.rcAct_layout);
        titleEt = (EditText) findViewById(R.id.rcAct_title_et);
        descEt = (EditText) findViewById(R.id.rcAct_desc_et);
        setToolBarTitle();
    }

    public void initData() {
        if(label != null) {
            titleEt.setText(label.getName());
            descEt.setText(label.getDescription());
        }
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showCreateLabelSuccess(String message) {
        showMessage(message);
        new Handler().postDelayed(new Runnable(){
            public void run() {
                LabelCreateActivity.this.finish();
                overridePendingTransition(R.anim.push_in_left, R.anim.push_out_right);;
            }
        }, 1000);
    }

    @Override
    public void setToolBarTitle() {
        toolbar.setTitle("创建事情分类");
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
            if(label == null) {
                label = new ThingClasses(title, desc);
            }else {
                label.setName(title);
                label.setDescription(desc);
            }
            presenter.saveLabel(label);
        }
    }
}
