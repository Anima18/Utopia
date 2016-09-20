package com.chris.utopia.module.idea.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chris.utopia.R;
import com.chris.utopia.common.view.BaseFragment;
import com.chris.utopia.entity.Idea;
import com.chris.utopia.module.idea.adapter.IdeaAdapter;
import com.chris.utopia.module.idea.presenter.IdeaPresenter;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2015/8/16.
 */
public class IdeaFragment extends BaseFragment implements View.OnClickListener, IdeaActionView {

    private CoordinatorLayout rootView;
    private RecyclerView ideaRecyclerView;
    private FloatingActionButton addFAB;
    private IdeaAdapter adapter;
    private List<Idea> ideaList = new ArrayList<>();

    @Inject
    private IdeaPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_idea, container, false);
        initView(view);
        initEvent();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setActionView(this);
        presenter.loadIdeaData();
    }

    public void initView(View view) {
        rootView = (CoordinatorLayout) view.findViewById(R.id.stFrm_coordinatorLayout);
        addFAB = (FloatingActionButton) view.findViewById(R.id.ideaFrm_addFad);
        ideaRecyclerView = (RecyclerView) view.findViewById(R.id.ideaFrm_recyclerView);
        adapter = new IdeaAdapter(getContext(), ideaList);
        adapter.setOnItemClickListener(new IdeaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("Idea", ideaList.get(position));
                Intent intent = new Intent(getContext(), IdeaCreateActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
            }
        });
        ideaRecyclerView.setAdapter(adapter);
        ideaRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

    }

    public void initEvent() {
        addFAB.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ideaFrm_addFad:
                Intent intent = new Intent(getActivity(), IdeaCreateActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                break;
        }
    }

    @Override
    public void loadIdeaData(List<Idea> ideaList) {
        this.ideaList.clear();
        this.ideaList.addAll(ideaList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }
}
