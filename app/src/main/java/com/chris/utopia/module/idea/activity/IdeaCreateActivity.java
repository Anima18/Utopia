package com.chris.utopia.module.idea.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.chris.utopia.R;
import com.chris.utopia.common.callback.LabelCallBack;
import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.DateUtil;
import com.chris.utopia.common.util.StringUtil;
import com.chris.utopia.common.view.BaseActivity;
import com.chris.utopia.common.view.LabelDialog;
import com.chris.utopia.entity.Idea;
import com.chris.utopia.entity.ThingClasses;
import com.chris.utopia.module.idea.presenter.IdeaCreatePresenter;
import com.chris.utopia.module.plan.activity.PlanCreateActivity;
import com.google.inject.Inject;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by Chris on 2016/1/20.
 */
@ContentView(R.layout.activity_idea_create)
public class IdeaCreateActivity extends BaseActivity implements IdeaCreateActionView, View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, ColorChooserDialog.ColorCallback {

    @InjectView(R.id.icAct_layout)
    private LinearLayout rootView;
    @InjectView(R.id.icAct_title_textInput)
    private TextInputLayout titleTi;
    @InjectView(R.id.icAct_title_et)
    private EditText titleEt;
    @InjectView(R.id.icAct_desc_ti)
    private TextInputLayout descTi;
    @InjectView(R.id.icAct_desc_et)
    private EditText descEt;
    @InjectView(R.id.icAct_label_layout)
    private LinearLayout labelLayout;
    @InjectView(R.id.icAct_label_value)
    private TextView labelValue;
    @InjectView(R.id.icAct_clock_layout)
    private LinearLayout clockLayout;
    @InjectView(R.id.icAct_clock_value)
    private TextView clockValue;
    @InjectView(R.id.icAct_palette_layout)
    private LinearLayout paletteLayout;
    @InjectView(R.id.icAct_palette_value)
    private TextView paletteValue;

    private Idea idea = null;
    private List<ThingClasses> classesList = new ArrayList<>();
    private Calendar calendar = null;

    private MaterialDialog labelDialog = null;

    @Inject
    private IdeaCreatePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idea = (Idea)getIntent().getSerializableExtra("Idea");
        initView();
        initData();
        initEvent();
    }

    public void initView() {
        setToolBarTitle();
        invalidateOptionsMenu();
    }

    public void initData() {
        presenter.setActionView(this);
        if(idea != null) {
            titleEt.setText(idea.getName());
            descEt.setText(idea.getDescription());
            labelValue.setText(presenter.getThingClassById(idea.getThingClassesId()).getName());
            clockValue.setText(idea.getRemindTime());
            paletteValue.setText(idea.getBackground());
        }
    }

    public void initEvent() {
        labelLayout.setOnClickListener(this);
        clockLayout.setOnClickListener(this);
        paletteLayout.setOnClickListener(this);
    }

    @Override
    public void setToolBarTitle() {
        toolbar.setTitle("创建想法");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icAct_label_layout:
                String labelId = idea == null ? null : idea.getThingClassesId();
                LabelDialog labelDialog = LabelDialog.initInstance();
                labelDialog.setmContext(getContext());
                labelDialog.showLableDialog(labelId, new LabelCallBack() {
                    @Override
                    public void callBack(ThingClasses classes) {
                        if(idea == null) {
                            idea = new Idea();
                        }
                        idea.setThingClassesId(classes.getId());
                        labelValue.setText(classes.getName());
                    }

                    @Override
                    public void fail(String message) {

                    }
                });
                break;
            case R.id.icAct_clock_layout:
                Calendar now = Calendar.getInstance();
                if(idea != null && StringUtil.isNotEmpty(idea.getRemindTime())) {
                    Date date = DateUtil.toDate(idea.getRemindTime(), Constant.DATETIME_FORMAT_6);
                    now.setTime(date);
                }
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        IdeaCreateActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
                break;
            case R.id.icAct_palette_layout:
                /*new ColorChooserDialog.Builder(IdeaCreateActivity.this, R.string.dialog_title)
                    .titleSub(R.string.dialog_title)  // title of dialog when viewing shades of a color
                    .accentMode(true)  // when true, will display accent palette instead of primary palette
                    .doneButton(R.string.md_done_label)  // changes label of the done button
                    .cancelButton(R.string.md_cancel_label)  // changes label of the cancel button
                    .backButton(R.string.md_back_label)  // changes label of the back button
                    .preselect(getResources().getColor(R.color.accent))  // optionally preselects a color
                    .dynamicButtonColor(true)  // defaults to true, false will disable changing action buttons' color to currently selected color
                    .show();*/
                int backgroundRes = 0;
                if(idea == null || StringUtil.isEmpty(idea.getBackground())) {
                    backgroundRes = getResources().getColor(R.color.idea_create_default_color);
                }else {
                    backgroundRes = Color.parseColor(idea.getBackground());
                }
                new ColorChooserDialog.Builder(this, R.string.dialog_title)
                        .titleSub(R.string.dialog_title)
                        .preselect(backgroundRes)
                        .customColors(R.array.idea_background_custom_colors, null)
                        .show();
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        calendar = Calendar.getInstance();
        if(idea != null && StringUtil.isNotEmpty(idea.getRemindTime())) {
            Date date = DateUtil.toDate(idea.getRemindTime(), Constant.DATETIME_FORMAT_6);
            calendar.setTime(date);
        }
        TimePickerDialog tpd = TimePickerDialog.newInstance(IdeaCreateActivity.this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), false);
        tpd.show(getFragmentManager(), "Timepickerdialog");
        calendar.set(year, monthOfYear, dayOfMonth);

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);

        String timeStr = DateUtil.toString(calendar.getTime(), Constant.DATETIME_FORMAT_6);
        if(idea == null) {
            idea = new Idea();
        }
        idea.setRemindTime(timeStr);
        clockValue.setText(timeStr);
    }

    @Override
    public void onColorSelection(ColorChooserDialog dialog, int selectedColor) {
        String hexColor = String.format("#%06X", (0xFFFFFF & selectedColor));
        if(idea == null) {
            idea = new Idea();
        }
        idea.setBackground(hexColor);
        paletteValue.setText(hexColor);
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void saveIdeaMessage(String message) {
        showMessage(message);
        new Handler().postDelayed(new Runnable(){
            public void run() {
                IdeaCreateActivity.this.finish();
                overridePendingTransition(R.anim.push_in_left, R.anim.push_out_right);
            }
        }, 1000);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                overridePendingTransition(R.anim.push_in_left, R.anim.push_out_right);
                return true;
            case R.id.icMenu_idea_save:
                String title = titleEt.getText().toString();
                String desc = descEt.getText().toString();
                boolean flag = true;
                if(StringUtil.isEmpty(title)) {
                    showMessage("title不能为空");
                    flag = false;
                }
                if(flag == true && StringUtil.isEmpty(desc)) {
                    showMessage("description不能为空");
                    flag = false;
                }

                if(flag) {
                    if(idea == null) {
                        idea = new Idea();
                    }
                    idea.setName(title);
                    idea.setDescription(desc);
                    presenter.addIdea(idea);
                }
                return true;
            case R.id.icMenu_idea_on:
                Bundle bundle = new Bundle();
                bundle.putSerializable("IDEA", idea);
                Intent intent = new Intent(getContext(), PlanCreateActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                this.finish();
                overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_idea_create, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(idea != null) {
            menu.findItem(R.id.icMenu_idea_on).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }
}
