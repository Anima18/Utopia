package com.chris.utopia.module.home.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chris.utopia.R;
import com.chris.utopia.common.view.BaseFragment;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by Chris on 2016/3/9.
 */
public class ThingActionFragment extends BaseFragment implements OnChartValueSelectedListener {
    private PieChart mChart;
    private Typeface tf;

    protected String[] mParties = new String[] {
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thing_action, container, false);
        initView(view);
        initEvent();
        return view;
    }

    public void initView(View view) {
        mChart = (PieChart) view.findViewById(R.id.thingActionFrg_chart);
        initChart();
    }

    public void initData() {

    }

    public void initEvent() {

    }

    public void initChart() {
        mChart.setDescription("");

        //mChart.setCenterTextTypeface(Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf"));
        mChart.setCenterText("Quarterly\nRevenue");
        mChart.setCenterTextSize(22f);

        // radius of the center hole in percent of maximum radius
        mChart.setHoleRadius(45f);
        mChart.setTransparentCircleRadius(50f);

        // enable / disable drawing of x- and y-values
//        mChart.setDrawYValues(false);
//        mChart.setDrawXValues(false);

        mChart.setData(generatePieData());

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
    }

    private PieData generatePieData() {
        int count = 4;

        ArrayList<Entry> entries1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("Quarter 1");
        xVals.add("Quarter 2");
        xVals.add("Quarter 3");
        xVals.add("Quarter 4");

        for(int i = 0; i < count; i++) {
            //xVals.add("entry" + (i+1));

            entries1.add(new Entry(50, i));
        }

        PieDataSet ds1 = new PieDataSet(entries1, "Quarterly Revenues 2014");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);

        PieData d = new PieData(xVals, ds1);
        d.setValueTypeface(tf);
        return d;
    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }
}
