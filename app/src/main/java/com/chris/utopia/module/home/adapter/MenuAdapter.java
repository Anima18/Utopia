package com.chris.utopia.module.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chris.utopia.R;


/**
 * Created by Admin on 2016/8/6.
 */
public class MenuAdapter extends BaseAdapter {

    private Context context;
    private String[] menuList;

    public MenuAdapter(Context context, String[] menuList) {
        this.context = context;
        this.menuList = menuList;
    }
    @Override
    public int getCount() {
        return menuList.length;
    }

    @Override
    public Object getItem(int i) {
        return menuList[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.listview_menu_item, null);
        TextView textView = (TextView) view.findViewById(R.id.menuList_text);
        textView.setText(menuList[i]);

        return view;
    }
}
