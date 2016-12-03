package com.chris.utopia.module.home.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chris.utopia.R;
import com.chris.utopia.common.view.SlidingTabLayout;
import com.chris.utopia.module.home.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2015/8/16.
 */
public class HomeFragment extends Fragment {

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.homeFrm_slidingTabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.homeFrm_viewpager);
        toolbar = (Toolbar) view.findViewById(R.id.activity_toolBar) ;
        toolbar.setTitle("Utopia");

        initTabView();
        return view;
    }

    public void initTabView() {
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        List<String> fragmetNameList = new ArrayList<String>();
        fragmentList.add(new TodayTaskFragment());
        fragmentList.add(new WeekPlanFragment());
        fragmentList.add(new MyHabitFragment());
        fragmetNameList.add("今日安排");
        fragmetNameList.add("一周计划");
        fragmetNameList.add("我的习惯");
        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), fragmentList, fragmetNameList));
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tab_text_inactive);
            }
        });
        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) { }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
        //mSlidingTabLayout.setDividerColors(R.color.primary);
    }
}
