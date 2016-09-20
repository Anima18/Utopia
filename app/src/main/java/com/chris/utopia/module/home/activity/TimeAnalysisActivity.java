package com.chris.utopia.module.home.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.chris.utopia.R;
import com.chris.utopia.common.view.BaseActivity;
import com.chris.utopia.common.view.SlidingTabLayout;
import com.chris.utopia.module.home.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by Chris on 2016/3/8.
 */
@ContentView(R.layout.activity_timer_analysis)
public class TimeAnalysisActivity extends BaseActivity {

    @InjectView(R.id.timerAnalysisAct_slidingTabLayout)
    private SlidingTabLayout slidingTabLayout;
    @InjectView(R.id.timerAnalysisAct_viewpager)
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    private List<String> fragmetNameList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
        initEvent();
    }

    public void initView() {
        setToolBarTitle();
    }

    public void initData() {
        fragmentList.add(new ThingFinishFragment());
        fragmentList.add(new ThingRoleFragment());
        fragmentList.add(new ThingClassesFragment());
        fragmentList.add(new ThingQuadrantFragment());
        //fragmentList.add(new ThingActionFragment());
        fragmentList.add(new ThingPlanFragment());
        fragmetNameList.add("事情达成");
        fragmetNameList.add("事情角色");
        fragmetNameList.add("事情分类");
        fragmetNameList.add("事情象限");
        //fragmetNameList.add("事情时间");
        fragmetNameList.add("事情计划");
        initTabView();
    }

    public void initEvent() {}

    public void initTabView() {
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList, fragmetNameList);
        viewPager.setAdapter(adapter);
        slidingTabLayout.setViewPager(viewPager);
        viewPager.setCurrentItem(0);
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tab_text_inactive);
            }
        });
        slidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) { }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

    @Override
    public void setToolBarTitle() {
        toolbar.setTitle("时间分析");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                overridePendingTransition(R.anim.push_in_left, R.anim.push_out_right);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
