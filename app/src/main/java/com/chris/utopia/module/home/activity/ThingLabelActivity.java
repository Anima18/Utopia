package com.chris.utopia.module.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chris.utopia.R;
import com.chris.utopia.common.view.BaseActivity;
import com.chris.utopia.entity.ThingClasses;
import com.chris.utopia.module.home.adapter.LabelAdapter;
import com.chris.utopia.module.home.presenter.LabelPresenter;
import com.chris.utopia.module.home.presenter.LabelPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by Admin on 2016/12/10.
 */
@ContentView(R.layout.activity_label)
public class ThingLabelActivity extends BaseActivity implements LabelActionView, View.OnClickListener {
    @InjectView(R.id.labelAct_coordinatorLayout)
    private CoordinatorLayout rootView;
    @InjectView(R.id.labelAct_recyclerView)
    private RecyclerView labelRecyclerView;
    @InjectView(R.id.labelAct_addFad)
    private FloatingActionButton addFab;
    private List<ThingClasses> labellist = new ArrayList<>();
    private LabelAdapter adapter;

    private LabelPresenter presenter = new LabelPresenterImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        adapter = new LabelAdapter(getContext(), labellist);
        adapter.setOnItemClickListener(new LabelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("label", labellist.get(position));
                Intent intent = new Intent(getContext(), LabelCreateActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
            }
        });
        labelRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        labelRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
        labelRecyclerView.setLayoutManager(layoutManager);

        presenter.loadLabel();
    }

    private void initEvent() {
        addFab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.labelAct_addFad:
                Intent intent = new Intent(getContext(), LabelCreateActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                break;
        }
    }

    @Override
    public void setToolBarTitle() {
        toolbar.setTitle("事情分类");
    }

    @Override
    public void loadLabelData(List<ThingClasses> labelList) {
        this.labellist.clear();
        this.labellist.addAll(labelList);
        adapter.notifyDataSetChanged();
    }
}
