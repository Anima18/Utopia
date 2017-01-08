package com.chris.utopia.module.home.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.chris.utopia.R;
import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.DateUtil;
import com.chris.utopia.common.view.BaseActivity2;
import com.chris.utopia.common.view.SlidingTabLayout;
import com.chris.utopia.module.home.adapter.ViewPagerAdapter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Chris on 2016/3/1.
 */
public class TimerActivity extends BaseActivity2 implements DatePickerDialog.OnDateSetListener {

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    private List<String> fragmetNameList;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private int currentItem = 0;
    private int lastState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutId(R.layout.activity_timer);
        super.onCreate(savedInstanceState);
        for(int i = 0; i < 7; i++) {
            fragmentList.add(new TimerFragment());
        }

        initView();
        initData(new Date());
        initEvent();
    }

    public void initView() {
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.timerAct_slidingTabLayout);
        viewPager = (ViewPager) findViewById(R.id.timerAct_viewpager);
        setToolBarTitle();
    }

    public void initData(Date date) {
        fragmetNameList = DateUtil.getALlweekDays(date);
        String today = DateUtil.toString(date, Constant.DATETIME_FORMAT_4);
        for(int i = 0; i < fragmetNameList.size(); i++) {
            Bundle bundle = fragmentList.get(i).getArguments();
            if(bundle != null) {
                bundle.putString("DATE", fragmetNameList.get(i));
            }else {
                bundle = new Bundle();
                bundle.putString("DATE", fragmetNameList.get(i));
                fragmentList.get(i).setArguments(bundle);
            }

        }
        currentItem = fragmetNameList.indexOf(today);
        initTabView();
    }

    public void initEvent() {}

    @Override
    public void setToolBarTitle() {
        toolbar.setTitle("时间");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);
    }

    public void initTabView() {
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList, fragmetNameList);
        viewPager.setAdapter(adapter);
        slidingTabLayout.setViewPager(viewPager);
        viewPager.setCurrentItem(currentItem);
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tab_text_inactive);
            }
        });
        slidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                /*Log.i("initTabView", "onPageScrolled: "+position + ", " + positionOffset+ ", " + positionOffsetPixels);
                lastSelectIndex = position;*/
                if (lastState == 1 && position == 0 && positionOffsetPixels == 0) {
                    Log.i("TimerActivity", "上一个星期");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(DateUtil.toDate(fragmetNameList.get(position), Constant.DATETIME_FORMAT_4));
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                    initData(calendar.getTime());
                    lastState = 0;
                    return;
                } else if (lastState == 1 && position == 6 && positionOffsetPixels == 0) {
                    Log.i("TimerActivity", "下一个星期");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(DateUtil.toDate(fragmetNameList.get(position), Constant.DATETIME_FORMAT_4));
                    calendar.add(Calendar.DAY_OF_MONTH, +1);
                    initData(calendar.getTime());
                    lastState = 0;
                    return;
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("TimerActivity", "onPageSelected: " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i("TimerActivity", "onPageScrollStateChanged: " + state);
                lastState = state;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.timerAct_action_select:
                showDateDialog();
                return true;
            case android.R.id.home:
                this.finish();
                overridePendingTransition(R.anim.push_in_left, R.anim.push_out_right);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showDateDialog() {
        Calendar now = Calendar.getInstance();
        Date date = DateUtil.toDate(fragmetNameList.get(viewPager.getCurrentItem()), Constant.DATETIME_FORMAT_4);
        now.setTime(date);
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        initData(calendar.getTime());
    }
}
