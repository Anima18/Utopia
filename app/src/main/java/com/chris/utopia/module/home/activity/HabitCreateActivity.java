package com.chris.utopia.module.home.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.chris.utopia.R;
import com.chris.utopia.common.callback.LabelCallBack;
import com.chris.utopia.common.callback.QuadrantCallBack;
import com.chris.utopia.common.callback.RoleCallBack;
import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.service.NotificationService;
import com.chris.utopia.common.util.DateUtil;
import com.chris.utopia.common.util.StringUtil;
import com.chris.utopia.common.view.BaseActivity;
import com.chris.utopia.common.view.LabelDialog;
import com.chris.utopia.common.view.QuadrantDialog;
import com.chris.utopia.common.view.RoleDialog;
import com.chris.utopia.common.view.ThingTypeDialog;
import com.chris.utopia.common.view.WhatDayDialog;
import com.chris.utopia.entity.Role;
import com.chris.utopia.entity.Thing;
import com.chris.utopia.entity.ThingClasses;
import com.chris.utopia.module.home.presenter.ThingCreatePresenter;
import com.google.inject.Inject;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Date;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by Chris on 2016/2/20.
 */
@ContentView(R.layout.activity_habit_create)
public class HabitCreateActivity extends BaseActivity  implements View.OnClickListener, View.OnFocusChangeListener,
        TimePickerDialog.OnTimeSetListener, ThingCreateActionView {

    @InjectView(R.id.hcAct_layout)
    private View rootView;
    @InjectView(R.id.hcAct_title_ti)
    private TextInputLayout nameTi;
    @InjectView(R.id.hcAct_title_et)
    private EditText nameEt;
    @InjectView(R.id.hcAct_desc_ti)
    private TextInputLayout descTi;
    @InjectView(R.id.hcAct_desc_et)
    private EditText descEt;
    @InjectView(R.id.hcAct_label_et)
    private EditText labelEt;
    @InjectView(R.id.hcAct_role_et)
    private EditText roleEt;
    @InjectView(R.id.hcAct_quadrant_et)
    private EditText quadrantEt;
    @InjectView(R.id.hcAct_whatDay_ti)
    private TextInputLayout whatDayTi;
    @InjectView(R.id.hcAct_whatDay_et)
    private EditText whatDayEt;
    @InjectView(R.id.hcAct_begin_time_ti)
    private TextInputLayout fromTimeTi;
    @InjectView(R.id.hcAct_begin_time_et)
    private EditText fromTimeEt;
    @InjectView(R.id.hcAct_prompting_cb)
    private AppCompatCheckBox promptingCb;
    @InjectView(R.id.hcAct_type_et)
    private EditText typeEt;
    @InjectView(R.id.hcAct_time_layout)
    private View timeView;

    private Thing thing = null;

    @Inject
    private ThingCreatePresenter presenter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        thing = (Thing) getIntent().getSerializableExtra("THING");
        initView();
        initData();
        initEvent();
    }

    public void initView() {
        setToolBarTitle();
    }

    public void initData() {
        presenter.setActionView(this);
        if(thing != null) {
            nameEt.setText(thing.getTitle());
            descEt.setText(thing.getDescription());
            labelEt.setText(presenter.getThingClassById(thing.getClassessId()).getName());
            roleEt.setText(presenter.getRoleById(thing.getRoleId()).getName());
            quadrantEt.setText(thing.getThingQuadrant());
            promptingCb.setChecked(thing.isPrompting());
            typeEt.setText(thing.getPeriod());
            timeView.setVisibility(View.VISIBLE);
            if(thing.getPeriod().equals("每周")) {
                whatDayTi.setVisibility(View.VISIBLE);
                whatDayEt.setText(thing.getWhatDay());
            }

            fromTimeEt.setText(thing.getBeginTime());
        }
    }

    public void initEvent() {
        labelEt.setOnFocusChangeListener(this);
        labelEt.setOnClickListener(this);
        roleEt.setOnClickListener(this);
        roleEt.setOnFocusChangeListener(this);
        quadrantEt.setOnClickListener(this);
        quadrantEt.setOnFocusChangeListener(this);
        whatDayEt.setOnClickListener(this);
        whatDayEt.setOnFocusChangeListener(this);
        fromTimeEt.setOnClickListener(this);
        fromTimeEt.setOnFocusChangeListener(this);
        typeEt.setOnClickListener(this);
        typeEt.setOnFocusChangeListener(this);

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
        toolbar.setTitle("创建习惯");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hcAct_label_et:
                showLabelDialog();
                break;
            case R.id.hcAct_role_et:
                showRoleDialog();
                break;
            case R.id.hcAct_quadrant_et:
                showQuadrantDialog();
                break;
            case R.id.hcAct_type_et:
                showTypeDialog();
                break;
            case R.id.hcAct_whatDay_et:
                showWhatDayDialog();
                break;
            case R.id.hcAct_begin_time_et:
                showTimeDialog();
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.hcAct_label_et:
                if(hasFocus) {
                    showLabelDialog();
                }
                break;
            case R.id.hcAct_role_et:
                if(hasFocus) {
                    showRoleDialog();
                }
                break;
            case R.id.hcAct_quadrant_et:
                if(hasFocus) {
                    showQuadrantDialog();
                }
                break;
            case R.id.hcAct_type_et:
                if(hasFocus) {
                    showTypeDialog();
                }
                break;
            case R.id.hcAct_whatDay_et:
                if(hasFocus) {
                    showWhatDayDialog();
                }
                break;
            case R.id.hcAct_begin_time_et:
                if(hasFocus) {
                    showTimeDialog();
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

    public void showTypeDialog() {
        ThingTypeDialog typeDialog = ThingTypeDialog.initInstance();
        typeDialog.setmContext(getContext());
        String value = (thing != null && StringUtil.isNotEmpty(thing.getPeriod())) ? thing.getPeriod() : "";
        typeDialog.showTypeDialog(value, new QuadrantCallBack() {
            @Override
            public void callBack(String type) {
                createThing();
                thing.setPeriod(type);
                typeEt.setText(type);

                if (timeView.getVisibility() != View.VISIBLE) {
                    timeView.setVisibility(View.VISIBLE);
                }

                if (type.equals("每天")) {
                    whatDayTi.setVisibility(View.GONE);
                } else if (type.equals("每周")) {
                    whatDayTi.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void fail(String message) {
                showMessage(message);
            }
        });
    }

    public void showWhatDayDialog() {
        WhatDayDialog whatDayDialog = WhatDayDialog.initInstance();
        whatDayDialog.setmContext(getContext());
        String value = (thing != null && StringUtil.isNotEmpty(thing.getWhatDay())) ? thing.getWhatDay() : "";
        whatDayDialog.showWhatDayDialog(value, new QuadrantCallBack() {
            @Override
            public void callBack(String whatDay) {
                createThing();
                thing.setWhatDay(whatDay);
                whatDayEt.setText(whatDay);
            }

            @Override
            public void fail(String message) {
                showMessage(message);
            }
        });
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
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String timeStr = (hourOfDay < 10 ? "0"+hourOfDay : hourOfDay) + ":" + (minute < 10 ? "0"+minute : minute) + ":" + (second < 10 ? "0"+second : second);
        fromTimeEt.setText(timeStr);
        createThing();
        thing.setBeginTime(fromTimeEt.getText().toString());
    }

    public void createThing() {
        if(thing == null) {
            thing = new Thing();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_habit_create, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                overridePendingTransition(R.anim.push_in_left, R.anim.push_out_right);
                return true;
            case R.id.hcAct_action_save:
                save();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void save() {
        String name = nameEt.getText().toString();
        String desc = descEt.getText().toString();

        if(thing == null) {
            thing = new Thing();
        }
        thing.setTitle(name);
        thing.setDescription(desc);
        thing.setType(Constant.THING_TYPE_HABIT);

        if(StringUtil.isEmpty(thing.getTitle())) {
            showMessage("Title不能为空");
            return;
        }
        if(StringUtil.isEmpty(thing.getDescription())) {
            showMessage("Desc不能为空");
            return;
        }
        if(thing.getClassessId() == null) {
            showMessage("Label不能为空");
            return;
        }
        if(thing.getRoleId() == null) {
            showMessage("Role不能为空");
            return;
        }
        if(StringUtil.isEmpty(thing.getThingQuadrant())) {
            showMessage("Quadrant不能为空");
            return;
        }
        if(StringUtil.isEmpty(thing.getType())) {
            showMessage("Type不能为空");
            return;
        }
        if(thing.getPeriod().equals("每周") && StringUtil.isEmpty(thing.getWhatDay())) {
            showMessage("WhatDay不能为空");
            return;
        }else if(thing.getPeriod().equals("每天")){
            thing.setWhatDay("");
        }
        if(StringUtil.isEmpty(thing.getBeginTime())) {
            showMessage("开始时间不能为空");
            return;
        }

        presenter.save(thing);
    }

    @Override
    public void saveSuccess(String message) {
        showMessage(message);

       /* Bundle bundle = new Bundle();
        bundle.putSerializable("THING", thing);
        Intent intent = new Intent(getContext(), NotificationService.class);
        intent.putExtras(bundle);
        PendingIntent sender = PendingIntent.getService(getContext(), thing.getId(), intent, PendingIntent.FLAG_ONE_SHOT);

        String timeStr = DateUtil.toString(new Date(), Constant.DATETIME_FORMAT_4) + " " + thing.getBeginTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.toDate(timeStr, Constant.DATETIME_FORMAT_6));

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        int period = 0;
        if("每天".equals(thing.getPeriod())) {
            period = 24*60*60*1000;
        }else if("每周".equals(thing.getPeriod())) {
            period = 7*24*60*60*1000;
        }
        am.setRepeating(AlarmManager.RTC_WAKEUP
                , calendar.getTimeInMillis(), period, sender);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                HabitCreateActivity.this.finish();
                overridePendingTransition(R.anim.push_in_left, R.anim.push_out_right);
            }
        }, 1000);*/
    }

}
