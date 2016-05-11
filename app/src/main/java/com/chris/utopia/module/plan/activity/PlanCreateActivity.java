package com.chris.utopia.module.plan.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chris.utopia.R;
import com.chris.utopia.common.callback.LabelCallBack;
import com.chris.utopia.common.callback.QuadrantCallBack;
import com.chris.utopia.common.callback.RoleCallBack;
import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.StringUtil;
import com.chris.utopia.common.view.BaseActivity;
import com.chris.utopia.common.view.LabelDialog;
import com.chris.utopia.common.view.QuadrantDialog;
import com.chris.utopia.common.view.RoleDialog;
import com.chris.utopia.entity.Idea;
import com.chris.utopia.entity.Plan;
import com.chris.utopia.entity.Role;
import com.chris.utopia.entity.Thing;
import com.chris.utopia.entity.ThingClasses;
import com.chris.utopia.module.home.activity.ThingCreateActivity;
import com.chris.utopia.module.plan.adapter.PlanThingAdapter;
import com.chris.utopia.module.plan.presenter.PlanCreatePresenter;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by Chris on 2016/1/28.
 */
@ContentView(R.layout.activity_plan_create)
public class PlanCreateActivity extends BaseActivity implements View.OnClickListener, PlanCreateActionView {

    @InjectView(R.id.pcAct_layout)
    private View rootView;
    @InjectView(R.id.pcAct_thing_rv)
    private RecyclerView thingRv;
    @InjectView(R.id.pcAct_label_layout)
    private View labelView;
    @InjectView(R.id.pcAct_role_layout)
    private View roleView;
    @InjectView(R.id.pcAct_quadrant_layout)
    private View quadrantView;
    @InjectView(R.id.pcAct_upAndDown_layout)
    private View upDownView;
    @InjectView(R.id.pcAct_other_layout)
    private View otherView;
    @InjectView(R.id.pcAct_label_value)
    private TextView lableTv;
    @InjectView(R.id.pcAct_role_value)
    private TextView roleTv;
    @InjectView(R.id.pcAct_quadrant_value)
    private TextView quadrantTv;
    @InjectView(R.id.pcAct_upAndDown_iv)
    private ImageView upDownIv;
    @InjectView(R.id.pcAct_addFad)
    private FloatingActionButton addFAB;
    @InjectView(R.id.pcAct_title_textInput)
    private TextInputLayout titleTi;
    @InjectView(R.id.pcAct_title_et)
    private EditText titleEt;
    @InjectView(R.id.pcAct_desc_et)
    private EditText descEt;

    private PlanThingAdapter adapter;
    private List<Thing> thingList = new ArrayList<>();
    private List<ThingClasses> classesList = new ArrayList<>();
    private List<Role> roleList = new ArrayList<>();
    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    @Inject
    private PlanCreatePresenter presenter;

    private Plan plan;
    private Idea idea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        plan = (Plan)getIntent().getSerializableExtra("PLAN");
        idea = (Idea)getIntent().getSerializableExtra("IDEA");
        initView();
        initData();
        initEvent();
    }

    public void initView() {
        setToolBarTitle();
        adapter = new PlanThingAdapter(getContext(), thingList);
        adapter.setOnItemClickListener(new PlanThingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("THING", thingList.get(position));
                Intent intent = new Intent(getContext(), ThingCreateActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, Constant.ADD_THING_RESULT_CODE);
                overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
            }
        });
        thingRv.setAdapter(adapter);
        thingRv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void initData() {
        presenter.setActionView(this);
        if(plan != null) {
            titleEt.setText(plan.getName());
            descEt.setText(plan.getDescription());
            lableTv.setText(presenter.getThingClassById(plan.getThingClassesId()).getName());
            roleTv.setText(presenter.getRoleById(plan.getRoleId()).getName());
            quadrantTv.setText(plan.getThingQuadrant());
            presenter.loadThing(plan);
        }else if(idea != null) {
            titleEt.setText(idea.getName());
            descEt.setText(idea.getDescription());
            lableTv.setText(presenter.getThingClassById(idea.getThingClassesId()).getName());
            createPlan();
            plan.setThingClassesId(idea.getThingClassesId());
        }
    }

    public void initEvent() {
        labelView.setOnClickListener(this);
        roleView.setOnClickListener(this);
        quadrantView.setOnClickListener(this);
        upDownView.setOnClickListener(this);
        addFAB.setOnClickListener(this);
    }

    @Override
    public void setToolBarTitle() {
        toolbar.setTitle("创建计划");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pcAct_label_layout:
                LabelDialog labelDialog = LabelDialog.initInstance();
                labelDialog.setmContext(getContext());
                Integer index = null;
                if(plan != null && plan.getThingClassesId() != null) {
                    index =  plan.getThingClassesId();
                }else if(idea != null && idea.getThingClassesId() != null){
                    index = idea.getThingClassesId();
                }
                labelDialog.showLableDialog(index, new LabelCallBack() {
                    @Override
                    public void callBack(ThingClasses classes) {
                        createPlan();
                        plan.setThingClassesId(classes.getId());
                        lableTv.setText(classes.getName());
                    }

                    @Override
                    public void fail(String message) {
                        showMessage(message);
                    }
                });
                break;
            case R.id.pcAct_role_layout:
                RoleDialog roleDialog = RoleDialog.initInstance();
                roleDialog.setmContext(getContext());
                Integer index2 = (plan != null && plan.getRoleId() != null) ? plan.getRoleId() : null;
                roleDialog.showRoleDialog(index2, new RoleCallBack() {
                    @Override
                    public void callBack(Role role) {
                        createPlan();
                        plan.setRoleId(role.getId());
                        roleTv.setText(role.getName());
                    }

                    @Override
                    public void fail(String message) {
                        showMessage(message);
                    }
                });
                break;
            case R.id.pcAct_quadrant_layout:
                QuadrantDialog quadrantDialog = QuadrantDialog.initInstance();
                quadrantDialog.setmContext(getContext());
                String value = (plan != null && StringUtil.isNotEmpty(plan.getThingQuadrant())) ? plan.getThingQuadrant() : "";
                quadrantDialog.showQuadrantDialog(value, new QuadrantCallBack() {
                    @Override
                    public void callBack(String quadrant) {
                        createPlan();
                        plan.setThingQuadrant(quadrant);
                        quadrantTv.setText(quadrant);
                    }

                    @Override
                    public void fail(String message) {
                        showMessage(message);
                    }
                });
                break;
            case R.id.pcAct_upAndDown_layout:
                if(otherView.getVisibility() == View.GONE) {
                    otherView.setVisibility(View.VISIBLE);
                    //ViewCompat.animate(otherView).withLayer().setListener(null).start();
                    upDownIv.setImageDrawable(getResources().getDrawable(R.drawable.ic_up));
                    showAddFAB(false);
                }else {
                    otherView.setVisibility(View.GONE);
                    //ViewCompat.animate(otherView).withLayer().setListener(null).start();
                    upDownIv.setImageDrawable(getResources().getDrawable(R.drawable.ic_down));
                    showAddFAB(true);
                }
                break;
            case R.id.pcAct_addFad:
                if(plan == null || plan.getId() == null) {
                    final MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                            .title(R.string.dialog_title)
                            .content("是否要保存?")
                            .positiveText(R.string.dialog_button_positive)
                            .negativeText(R.string.dialog_button_negative)
                            .show();

                    dialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            save(false);
                        }
                    });
                }else {
                    saveSuccessAndAddThing();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_plan_create, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                PlanCreateActivity.this.finish();
                overridePendingTransition(R.anim.push_in_left, R.anim.push_out_right);
                return true;
            case R.id.pcMenu_idea_save:
                save(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void createPlan() {
        if(plan == null) {
            plan = new Plan();
        }
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }

    public void save(boolean isClose) {
        createPlan();
        String title = titleEt.getText().toString();
        String desc = descEt.getText().toString();


        if(StringUtil.isEmpty(title)) {
            showMessage("Title不能为空");
            return;
        }
        if(StringUtil.isEmpty(desc)) {
            showMessage("Desc不能为空");
            return;
        }
        if(plan.getThingClassesId() == null) {
            showMessage("Label不能为空");
            return;
        }
        if(plan.getRoleId() == null) {
            showMessage("Role不能为空");
            return;
        }
        if(StringUtil.isEmpty(plan.getThingQuadrant())) {
            showMessage("Quadrant不能为空");
            return;
        }
        plan.setName(title);
        plan.setDescription(desc);

        presenter.save(plan, idea, isClose);
    }

    @Override
    public void saveSuccess(String message) {
        showMessage(message);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                PlanCreateActivity.this.finish();
                overridePendingTransition(R.anim.push_in_left, R.anim.push_out_right);
            }
        }, 1000);
    }

    @Override
    public void saveSuccessAndAddThing() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("PLAN", plan);
        Intent intent = new Intent(getContext(), ThingCreateActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, Constant.ADD_THING_RESULT_CODE);
        overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
    }

    @Override
    public void loadThing(List<Thing> things) {
        this.thingList.clear();
        this.thingList.addAll(things);

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constant.ADD_THING_RESULT_CODE:
                presenter.loadThing(plan);
                break;
            default:
                break;
        }
    }

    public void showAddFAB(boolean flag) {
        //show addFab,if it is gone
        if (flag) {
            addFAB.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= 14) {
                ViewCompat.animate(addFAB).scaleX(1.0F).scaleY(1.0F).alpha(1.0F)
                        .setInterpolator(INTERPOLATOR).withLayer().setListener(null)
                        .start();
            } else {
                Animation anim = AnimationUtils.loadAnimation(addFAB.getContext(), R.anim.fab_in);
                anim.setDuration(200L);
                anim.setInterpolator(INTERPOLATOR);
                addFAB.startAnimation(anim);
            }
        }else {
            if (Build.VERSION.SDK_INT >= 14) {
                ViewCompat.animate(addFAB).scaleX(0.0F).scaleY(0.0F).alpha(0.0F).setInterpolator(INTERPOLATOR).withLayer()
                        .start();
            } else {
                Animation anim = AnimationUtils.loadAnimation(addFAB.getContext(), R.anim.fab_out);
                anim.setInterpolator(INTERPOLATOR);
                anim.setDuration(200L);
                addFAB.startAnimation(anim);
            }
        }
    }
}
