package com.chris.utopia.module.role.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chris.utopia.R;
import com.chris.utopia.common.view.BaseActivity;
import com.chris.utopia.entity.Role;
import com.chris.utopia.module.role.adapter.RoleAdapter;
import com.chris.utopia.module.role.presenter.RolePresenter;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.ContentView;

/**
 * Created by Chris on 2015/8/16.
 */
@ContentView(R.layout.fragment_role)
public class RoleActivity extends BaseActivity implements View.OnClickListener, RoleActionView {

    private CoordinatorLayout rootView;
    private RecyclerView roleRecyclerView;
    private FloatingActionButton addFab;
    private List<Role> rolelist = new ArrayList<>();
    private RoleAdapter adapter;

    @Inject
    private RolePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void setToolBarTitle() {
        toolbar.setTitle("我的角色");
    }

    public void initView() {
        rootView = (CoordinatorLayout) findViewById(R.id.roleFrm_coordinatorLayout);
        addFab = (FloatingActionButton) findViewById(R.id.roleFrm_addFad);
        roleRecyclerView = (RecyclerView) findViewById(R.id.roleFrm_recyclerView);
        adapter = new RoleAdapter(getContext(), rolelist);
        adapter.setOnItemClickListener(new RoleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("role", rolelist.get(position));
                Intent intent = new Intent(getContext(), RoleCreateActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
            }
        });
        roleRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        roleRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
        roleRecyclerView.setLayoutManager(layoutManager);
    }

    public void initData() {
        presenter.setActionView(this);
        presenter.loadRoleData();
    }

    public void initEvent() {
        addFab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.roleFrm_addFad:
                Intent intent = new Intent(getContext(), RoleCreateActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                break;
        }
    }

    @Override
    public void loadRoleData(List<Role> roleList) {
        this.rolelist.clear();
        this.rolelist.addAll(roleList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }
}
