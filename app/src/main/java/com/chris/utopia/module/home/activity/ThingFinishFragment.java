package com.chris.utopia.module.home.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chris.utopia.R;
import com.chris.utopia.common.view.BaseFragment;
import com.chris.utopia.module.home.presenter.TimeAnalysisPresenter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Chris on 2016/3/9.
 */
public class ThingFinishFragment extends BaseFragment implements OnChartValueSelectedListener, TimeAnalysisActionView {
    private PieChart mChart;
    private Typeface tf;

    @Inject
    private TimeAnalysisPresenter presenter;

    /*protected String[] mParties = new String[] {
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thing_action, container, false);
        initView(view);
        initData();
        initEvent();
        return view;
    }

    public void initView(View view) {
        mChart = (PieChart) view.findViewById(R.id.thingActionFrg_chart);
    }

    public void initData() {
        presenter.setActionView(this);
        presenter.loadTimeFinishData();
    }

    public void initEvent() {

    }

    public void initChart(Map<String, Integer> value) {
        mChart.setDescription("");

        //mChart.setCenterTextTypeface(Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf"));
        mChart.setCenterText("事情达成");
        mChart.setCenterTextSize(20f);

        // radius of the center hole in percent of maximum radius
        mChart.setHoleRadius(45f);
        mChart.setTransparentCircleRadius(50f);

        // enable / disable drawing of x- and y-values
//        mChart.setDrawYValues(false);
//        mChart.setDrawXValues(false);

        mChart.setData(getPieData(value));

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
    }

    private PieData getPieData(Map<String, Integer> value) {
        ArrayList<Entry> entries1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();

        Iterator<String> it = value.keySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            String key = it.next();
            xVals.add(key);
            entries1.add(new Entry(value.get(key), i));
            i++;
        }

        PieDataSet ds1 = new PieDataSet(entries1, "");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);

        PieData d = new PieData(xVals, ds1);
        d.setValueTextSize(10f);
        return d;
    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void loadDate(Map<String, Integer> value) {
        initChart(value);
    }
}
