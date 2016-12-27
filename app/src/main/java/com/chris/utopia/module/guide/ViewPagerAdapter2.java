package com.chris.utopia.module.guide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chris.utopia.R;

public class ViewPagerAdapter2 extends PagerAdapter implements View.OnClickListener {

    private int[]              layouts;
    private Context            context;
    private LayoutInflater     inflater;
    private OnBtnClickListener listener;

    public ViewPagerAdapter2(@NonNull Context context, @NonNull int[] layouts, @NonNull OnBtnClickListener listener) {
        this.layouts = layouts;
        this.context = context;
        this.listener = listener;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return layouts == null ? 0 : layouts.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(layouts[position], container, false);
        if (position == layouts.length - 1) {
            view.findViewById(R.id.bt_guide_register).setOnClickListener(ViewPagerAdapter2.this);
            view.findViewById(R.id.bt_guide_login).setOnClickListener(ViewPagerAdapter2.this);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (listener == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.bt_guide_register:
                listener.onRegisterClick();
                break;
            case R.id.bt_guide_login:
                listener.onLoginClick();
                break;
        }
    }

    public interface OnBtnClickListener {

        void onLoginClick();

        void onRegisterClick();
    }
}


