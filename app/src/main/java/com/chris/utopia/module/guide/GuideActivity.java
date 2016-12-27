package com.chris.utopia.module.guide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chris.utopia.R;
import com.chris.utopia.module.system.activity.LoginActivity;
import com.chris.utopia.module.system.activity.RegisterActivity;

public class GuideActivity extends Activity {

    private ViewPager         vp;
    private ViewPagerAdapter2 vpAdapter;
    private ImageView[]       dots;
    private int               currentIndex;
    private int[] layouts = new int[]{R.layout.what_new_one, R.layout.what_new_two, R.layout.what_new_three, R.layout.what_new_four};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initViews();
        initDots();
    }

    private void initViews() {

        vpAdapter = new ViewPagerAdapter2(GuideActivity.this, layouts, onBtnClickListener);
        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(vpAdapter);
        vp.addOnPageChangeListener(onPageChangeListener);
    }

    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

        dots = new ImageView[layouts.length];

        for (int i = 0; i < layouts.length; i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(true);
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(false);
    }

    private void setCurrentDot(int position) {
        if (position < 0 || position > layouts.length - 1 || currentIndex == position) {
            return;
        }

        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);

        currentIndex = position;
    }

    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setCurrentDot(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private ViewPagerAdapter2.OnBtnClickListener onBtnClickListener = new ViewPagerAdapter2.OnBtnClickListener() {
        @Override
        public void onLoginClick() {
            startActivity(new Intent(GuideActivity.this, LoginActivity.class));
            finish();
        }

        @Override
        public void onRegisterClick() {
            startActivity(new Intent(GuideActivity.this, RegisterActivity.class));
            finish();
        }
    };
}
