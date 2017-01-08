package com.chris.utopia.module.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chris.utopia.R;
import com.chris.utopia.common.view.BaseActivity2;
import com.chris.utopia.entity.Thing;
import com.chris.utopia.module.home.adapter.SearchAdapter;
import com.chris.utopia.module.home.presenter.SearchPresenter;
import com.chris.utopia.module.home.presenter.SearchPresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 2016/2/27.
 */
public class SearchActivity extends BaseActivity2 implements SearchActionView {

    private RecyclerView dataRv;
    private SearchView searchView;

    private SearchAdapter adapter;
    private List<Thing> thingList = new ArrayList<>();

    private SearchPresenter presenter = new SearchPresenterImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutId(R.layout.activity_search);
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    public void initView() {
        dataRv = (RecyclerView) findViewById(R.id.searchAct_data_rv);
        adapter = new SearchAdapter(getContext(), thingList);
        dataRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("THING", thingList.get(position));
                Intent intent = new Intent(getContext(), ThingCreateActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        dataRv.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
        dataRv.setLayoutManager(layoutManager);
    }

    public void initData() {
        presenter.setActionView(this);
    }

    public void initEvent() {}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchMenuItem.getActionView();
        //final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));

        /*SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        AutoCompleteTextView searchText = (AutoCompleteTextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.serach_text_size));
*/
        searchView.setQueryHint("查询条件");
        searchMenuItem.expandActionView();
        MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                SearchActivity.this.finish();
                overridePendingTransition(R.anim.push_in_left, R.anim.push_out_right);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public void loadData(List<Thing> thingList) {
        this.thingList.clear();
        this.thingList.addAll(thingList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setToolBarTitle() {

    }
}
