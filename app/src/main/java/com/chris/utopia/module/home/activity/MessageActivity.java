package com.chris.utopia.module.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chris.utopia.R;
import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.view.BaseActivity;
import com.chris.utopia.entity.Thing;
import com.chris.utopia.module.home.adapter.MessageAdapter;
import com.chris.utopia.module.home.presenter.MessagePresenter;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by Chris on 2016/2/27.
 */
@ContentView(R.layout.activity_message)
public class MessageActivity extends BaseActivity implements MessageActionView {

    @InjectView(R.id.messageAct_data_rv)
    private RecyclerView dataRv;

    private MessageAdapter adapter;
    private List<Thing> thingList = new ArrayList<>();

    @Inject
    private MessagePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
        initEvent();
    }

    public void initView() {
        setToolBarTitle();

        adapter = new MessageAdapter(getContext(), thingList);
        dataRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
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
        adapter.setOnItemLongClickListener(new MessageAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View itemView, int position) {
                final Thing thing = thingList.get(position);
                new MaterialDialog.Builder(getContext())
                        .items(R.array.message_action)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                if(text.equals("标记完成")) {
                                    thing.setStatus(Constant.THING_STATUS_DONE);
                                    presenter.update(thing);
                                }else if(text.equals("忽略事件")) {
                                    thing.setStatus(Constant.THING_STATUS_IGNORE);
                                    presenter.update(thing);
                                }
                            }
                        })
                        .show();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        dataRv.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
        dataRv.setLayoutManager(layoutManager);
    }

    public void initData() {
        presenter.setActionView(this);
        presenter.loadData();
    }

    public void initEvent() {}

    @Override
    public void setToolBarTitle() {
        toolbar.setTitle("消息");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);
    }

    @Override
    public void loadData(List<Thing> thingList) {
        this.thingList.clear();
        this.thingList.addAll(thingList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateSuccess(Thing thing) {
        final int index = thingList.indexOf(thing);
        adapter.notifyItemChanged(index);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                thingList.remove(index);
                adapter.notifyItemRemoved(index);
            }
        }, 1000);
    }

    @Override
    public void clearAllSuccess() {
        this.thingList.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_message, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                overridePendingTransition(R.anim.push_in_left, R.anim.push_out_right);
                return true;
            case R.id.messageAct_action_message:
                presenter.clearAll(thingList);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
