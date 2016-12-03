package com.chris.utopia.module.system.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chris.utopia.R;

/**
 * Created by Admin on 2015/8/16.
 */
public class OtherFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_other, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.activity_toolBar) ;
        toolbar.setTitle("Utopia");
        TextView textView = (TextView) view.findViewById(R.id.id_tv_content);
        textView.setText("Other Fragment");
        return view;

    }
}
