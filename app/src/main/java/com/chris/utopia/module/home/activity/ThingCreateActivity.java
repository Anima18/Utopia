package com.chris.utopia.module.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chris.utopia.R;
import com.chris.utopia.common.callback.LabelCallBack;
import com.chris.utopia.common.callback.QuadrantCallBack;
import com.chris.utopia.common.callback.RoleCallBack;
import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.DateUtil;
import com.chris.utopia.common.util.StringUtil;
import com.chris.utopia.common.view.BaseActivity2;
import com.chris.utopia.common.view.LabelDialog;
import com.chris.utopia.common.view.QuadrantDialog;
import com.chris.utopia.common.view.RoleDialog;
import com.chris.utopia.entity.Plan;
import com.chris.utopia.entity.Role;
import com.chris.utopia.entity.Thing;
import com.chris.utopia.entity.ThingClasses;
import com.chris.utopia.module.home.presenter.ThingCreatePresenter;
import com.chris.utopia.module.home.presenter.ThingCreatePresenterImpl;
import com.gc.materialdesign.views.Slider;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Chris on 2016/2/2.
 */
public class ThingCreateActivity extends BaseActivity2 implements View.OnClickListener, View.OnFocusChangeListener,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, ThingCreateActionView {
    private View rootView;
    private TextInputLayout nameTi;
    private EditText nameEt;
    private TextInputLayout descTi;
    private EditText descEt;
    private TextInputLayout labelTi;
    private EditText labelEt;
    private TextInputLayout roleTi;
    private EditText roleEt;
    private TextInputLayout quadrantTi;
    private EditText quadrantEt;
    private TextInputLayout fromDateTi;
    private EditText fromDateEt;
    private TextInputLayout fromTimeTi;
    private EditText fromTimeEt;
    private AppCompatCheckBox promptingCb;
    private TextInputLayout progressTi;
    private EditText progressEt;

    private Plan plan = null;
    private Thing thing = null;

    private MaterialDialog dialog;

    private ThingCreatePresenter presenter = new ThingCreatePresenterImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutId(R.layout.activity_thing_create);
        super.onCreate(savedInstanceState);

        plan = (Plan)getIntent().getSerializableExtra("PLAN");
        thing = (Thing)getIntent().getSerializableExtra("THING");

        initView();
        initData();
        initEvent();
    }

    public void initView() {

        rootView = findViewById(R.id.tcAct_layout);
        nameTi = (TextInputLayout) findViewById(R.id.tcAct_title_ti);
        nameEt = (EditText) findViewById(R.id.tcAct_title_et);
        descTi = (TextInputLayout) findViewById(R.id.tcAct_desc_ti);
        descEt = (EditText) findViewById(R.id.tcAct_desc_et);
        labelTi = (TextInputLayout) findViewById(R.id.tcAct_label_ti);
        labelEt = (EditText) findViewById(R.id.tcAct_label_et);
        roleTi = (TextInputLayout) findViewById(R.id.tcAct_role_ti);
        roleEt = (EditText) findViewById(R.id.tcAct_role_et);
        quadrantTi = (TextInputLayout) findViewById(R.id.tcAct_quadrant_ti);
        quadrantEt = (EditText) findViewById(R.id.tcAct_quadrant_et);
        fromDateTi = (TextInputLayout) findViewById(R.id.tcAct_begin_date_ti);
        fromDateEt = (EditText) findViewById(R.id.tcAct_begin_date_et);
        fromTimeTi = (TextInputLayout) findViewById(R.id.tcAct_begin_time_ti);
        fromTimeEt = (EditText) findViewById(R.id.tcAct_begin_time_et);
        promptingCb = (AppCompatCheckBox) findViewById(R.id.tcAct_prompting_cb);
        progressTi = (TextInputLayout) findViewById(R.id.tcAct_progress_ti);
        progressEt = (EditText) findViewById(R.id.tcAct_progress_et);

        setToolBarTitle();
        presenter.setActionView(this);
        if(plan != null || (thing != null && thing.getPlanId() != null)) {
            progressTi.setVisibility(View.VISIBLE);
        }
        if(thing != null && thing.getPlanId() != null) {
            plan = presenter.getPlanById(thing.getPlanId());
        }
    }

    public void initData() {
        if(thing != null) {
            nameEt.setText(thing.getTitle());
            descEt.setText(thing.getDescription());
            labelEt.setText(presenter.getThingClassById(thing.getClassessId()).getName());
            roleEt.setText(presenter.getRoleById(thing.getRoleId()).getName());
            quadrantEt.setText(thing.getThingQuadrant());
            promptingCb.setChecked(thing.isPrompting());
            fromDateEt.setText(thing.getBeginDate());
            fromTimeEt.setText(thing.getBeginTime());
            progressEt.setText(thing.getProgress());
        }else if(plan != null) {
            labelEt.setText(presenter.getThingClassById(plan.getThingClassesId()).getName());
            roleEt.setText(presenter.getRoleById(plan.getRoleId()).getName());
            quadrantEt.setText(plan.getThingQuadrant());
            progressEt.setText(plan.getProgress());

            createThing();
            thing.setClassessId(plan.getThingClassesId());
            thing.setRoleId(plan.getRoleId());
            thing.setThingQuadrant(plan.getThingQuadrant());
            thing.setProgress(plan.getProgress());
        }
    }

    public void initEvent() {
        labelEt.setOnFocusChangeListener(this);
        labelEt.setOnClickListener(this);
        roleEt.setOnClickListener(this);
        roleEt.setOnFocusChangeListener(this);
        quadrantEt.setOnClickListener(this);
        quadrantEt.setOnFocusChangeListener(this);
        fromDateEt.setOnClickListener(this);
        fromDateEt.setOnFocusChangeListener(this);
        fromTimeEt.setOnClickListener(this);
        fromTimeEt.setOnFocusChangeListener(this);
        progressEt.setOnClickListener(this);
        progressEt.setOnFocusChangeListener(this);

        promptingCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                createThing();
                thing.setIsPrompting(isChecked);
            }
        });
    }

    @Override
    public void setToolBarTitle() {
        toolbar.setTitle("创建事情");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_thing_create, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ThingCreateActivity.this.finish();
                overridePendingTransition(R.anim.push_in_left, R.anim.push_out_right);
                return true;
            case R.id.tcMenu_idea_save:
                save();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tcAct_label_et:
                showLabelDialog();
                break;
            case R.id.tcAct_role_et:
                showRoleDialog();
                break;
            case R.id.tcAct_quadrant_et:
                showQuadrantDialog();
                break;
            case R.id.tcAct_begin_date_et:
                showDateDialog();
                break;
            case R.id.tcAct_begin_time_et:
                showTimeDialog();
                break;
            case R.id.tcAct_progress_et:
                showProgressDialog();
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.tcAct_label_et:
                if(hasFocus) {
                    showLabelDialog();
                }
                break;
            case R.id.tcAct_role_et:
                if(hasFocus) {
                    showRoleDialog();
                }
                break;
            case R.id.tcAct_quadrant_et:
                if(hasFocus) {
                    showQuadrantDialog();
                }
                break;
            case R.id.tcAct_begin_date_et:
                if(hasFocus) {
                    showDateDialog();
                }
                break;
            case R.id.tcAct_begin_time_et:
                if(hasFocus) {
                    showTimeDialog();
                }
                break;
            case R.id.tcAct_progress_et:
                if(hasFocus) {
                    showProgressDialog();
                }
                break;
        }
    }

    public void showLabelDialog() {
        LabelDialog labelDialog = LabelDialog.initInstance();
        labelDialog.setmContext(getContext());
        String index = (thing != null && thing.getClassessId() != null) ? thing.getClassessId() : null;
        labelDialog.showLableDialog(index, new LabelCallBack() {
            @Override
            public void callBack(ThingClasses classes) {
                createThing();
                thing.setClassessId(classes.getId());
                labelEt.setText(classes.getName());
            }

            @Override
            public void fail(String message) {
                showMessage(message);
            }
        });
    }

    public void showRoleDialog() {
        RoleDialog roleDialog = RoleDialog.initInstance();
        roleDialog.setmContext(getContext());
        String index = (thing != null && thing.getRoleId() != null) ? thing.getRoleId() : null;
        roleDialog.showRoleDialog(index, new RoleCallBack() {
            @Override
            public void callBack(Role role) {
                createThing();
                thing.setRoleId(role.getId());
                roleEt.setText(role.getName());
            }

            @Override
            public void fail(String message) {
                showMessage(message);
            }
        });
    }

    public void showQuadrantDialog() {
        QuadrantDialog quadrantDialog = QuadrantDialog.initInstance();
        quadrantDialog.setmContext(getContext());
        String value = (thing != null && StringUtil.isNotEmpty(thing.getThingQuadrant())) ? thing.getThingQuadrant() : "";
        quadrantDialog.showQuadrantDialog(value, new QuadrantCallBack() {
            @Override
            public void callBack(String quadrant) {
                createThing();
                thing.setThingQuadrant(quadrant);
                quadrantEt.setText(quadrant);
            }

            @Override
            public void fail(String message) {
                showMessage(message);
            }
        });
    }

    public void showDateDialog() {
        Calendar now = Calendar.getInstance();
        if(thing != null && StringUtil.isNotEmpty(thing.getBeginDate())) {
            Date date = DateUtil.toDate(thing.getBeginDate(), Constant.DATETIME_FORMAT_4);
            now.setTime(date);
        }
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    public void showTimeDialog() {
        Calendar calendar = Calendar.getInstance();
        if(thing != null && StringUtil.isNotEmpty(thing.getBeginTime())) {
            Date date = DateUtil.toDate(thing.getBeginTime(), Constant.DATETIME_FORMAT_5);
            calendar.setTime(date);
        }
        TimePickerDialog tpd = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), false);
        tpd.show(getFragmentManager(), "Timepickerdialog");
        //calendar.set(year, monthOfYear, dayOfMonth);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        fromDateEt.setText(DateUtil.toString(calendar.getTime(), Constant.DATETIME_FORMAT_4));
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String timeStr = (hourOfDay < 10 ? "0"+hourOfDay : hourOfDay) + ":" + (minute < 10 ? "0"+minute : minute) + ":" + (second < 10 ? "0"+second : second);
        fromTimeEt.setText(timeStr);
    }

    public void showProgressDialog() {
        if(dialog == null) {
            dialog = new MaterialDialog.Builder(this)
                    .title(R.string.dialog_title)
                    .customView(R.layout.dialog_progress, false)
                    .positiveText(R.string.dialog_button_positive)
                    .show();
        }else {
            dialog.show();
        }

        View view = dialog.getCustomView();
        final Slider slider = (Slider) view.findViewById(R.id.dialog_slider);
        String progressStr = progressEt.getText().toString();
        int progress = StringUtil.isNotEmpty(progressStr) ? Integer.parseInt(progressStr.split("%")[0]) :  0;
        slider.setValue(progress);

        dialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //progressEt.setText(slider.getValue() + "%");
                progressEt.setText(slider.getValue() + "");
            }
        });
    }

    public void save() {
        String name = nameEt.getText().toString();
        String desc = descEt.getText().toString();
        String fromDate = fromDateEt.getText().toString();
        String fromTime = fromTimeEt.getText().toString();
        String progress = progressEt.getText().toString();
        boolean flag = true;
        if(thing == null) {
            thing = new Thing();
        }
        thing.setTitle(name);
        thing.setDescription(desc);
        thing.setBeginDate(fromDate);
        thing.setBeginTime(fromTime);
        thing.setProgress(progress);
        thing.setType(Constant.THING_TYPE_THING);
        if(plan != null) {
            thing.setPlanId(plan.getId());
            //plan.setProgress(progress);
        }

        if(StringUtil.isEmpty(thing.getTitle())) {
            nameTi.setErrorEnabled(true);
            nameTi.setError("Title不能为空");
            flag = false;
        }else {
            nameTi.setErrorEnabled(false);
            nameTi.setError(null);
        }
        if(StringUtil.isEmpty(thing.getDescription())) {
            descTi.setErrorEnabled(true);
            descTi.setError("Desc 不能为空");
            flag = false;
        }else {
            descTi.setErrorEnabled(false);
            descTi.setError(null);
        }
        if(thing.getClassessId() == null) {
            labelTi.setErrorEnabled(true);
            labelTi.setError("Label 不能为空");
            flag = false;
        }else {
            labelTi.setErrorEnabled(false);
            labelTi.setError(null);
        }
        if(thing.getRoleId() == null) {
            roleTi.setErrorEnabled(true);
            roleTi.setError("Role 不能为空");
            flag = false;
        }else {
            roleTi.setErrorEnabled(false);
            roleTi.setError(null);
        }
        if(StringUtil.isEmpty(thing.getThingQuadrant())) {
            quadrantTi.setErrorEnabled(true);
            quadrantTi.setError("Quadrant 不能为空");
            flag = false;
        }else {
            quadrantTi.setErrorEnabled(false);
            quadrantTi.setError(null);
        }

        if(progressTi.getVisibility() == View.VISIBLE && StringUtil.isEmpty(thing.getProgress())) {
            progressTi.setErrorEnabled(true);
            progressTi.setError("Progress 不能为空");
            flag = false;
        }else if(plan != null) {
            int thingProgress = Integer.parseInt(thing.getProgress().split("%")[0]);
            int planProgress = Integer.parseInt(plan.getProgress().split("%")[0]);
            if(thingProgress < planProgress) {
                progressTi.setErrorEnabled(true);
                progressTi.setError("Thing的Progress不能小于" + plan.getProgress());
                flag = false;
            }else {
                plan.setProgress(progress);
                progressTi.setErrorEnabled(false);
                progressTi.setError(null);
            }
        }else {
            progressTi.setErrorEnabled(false);
            progressTi.setError(null);
        }

        if(StringUtil.isEmpty(thing.getBeginDate())) {
            fromDateTi.setErrorEnabled(true);
            fromDateTi.setError("开始日期不能为空");
            flag = false;
        }else {
            fromDateTi.setErrorEnabled(false);
            fromDateTi.setError(null);
        }
        if(StringUtil.isEmpty(thing.getBeginTime())) {
            fromTimeTi.setErrorEnabled(true);
            fromTimeTi.setError("开始时间不能为空");
            flag = false;
        }else {
            fromTimeTi.setErrorEnabled(false);
            fromTimeTi.setError(null);
        }

        if(flag) {
            presenter.save(plan, thing);
        }
    }

    public void createThing() {
        if(thing == null) {
            thing = new Thing();
        }
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void saveSuccess(String message) {
        showMessage(message);

        /*Bundle bundle = new Bundle();
        bundle.putSerializable("THING", thing);
        Intent intent = new Intent(getContext(), NotificationService.class);
        intent.putExtras(bundle);
        PendingIntent sender = PendingIntent.getService(getContext(), thing.getId(), intent, PendingIntent.FLAG_ONE_SHOT);

        String timeStr = thing.getBeginDate() + " " + thing.getBeginTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.toDate(timeStr, Constant.DATETIME_FORMAT_6));

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);*/

        /*AlarmManagerUtil.addAlarm(getContext(), thing);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                ThingCreateActivity.this.setResult(Constant.ADD_THING_RESULT_CODE, new Intent());
                ThingCreateActivity.this.finish();
                overridePendingTransition(R.anim.push_in_left, R.anim.push_out_right);
            }
        }, 1000);*/
    }

    @Override
    public void onBackPressed() {
        /*if(plan != null) {
            ThingCreateActivity.this.setResult(Constant.ADD_THING_RESULT_CODE, new Intent());
        }*/
        ThingCreateActivity.this.setResult(Constant.ADD_THING_RESULT_CODE, new Intent());
        this.finish();
        overridePendingTransition(R.anim.push_in_left, R.anim.push_out_right);
    }
}
