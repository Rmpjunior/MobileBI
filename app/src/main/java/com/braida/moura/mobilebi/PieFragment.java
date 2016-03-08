package com.braida.moura.mobilebi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.datatype.Duration;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SelectedValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Created by Roberto on 20/10/2015.
 */
public class PieFragment extends Fragment {

    public ArrayList<String> uniqueDim = new ArrayList<String>();
    public int labeltype = 0;


    private PieChartData generatePieChartData() throws JSONException {
        Bundle bundle = getActivity().getIntent().getExtras();
        ArrayList<String> dimensions_items = bundle.getStringArrayList("dimensions_items");
        ArrayList<String> values_items = bundle.getStringArrayList("values_items");
        ArrayList<String> dimensions_data = bundle.getStringArrayList("dimensions_data");
        ArrayList<String> values_data = bundle.getStringArrayList("values_data");
        ArrayList<Float> avalues = new ArrayList<Float>();
        for (int i = 0; i < dimensions_data.size(); i++) {
            if (!uniqueDim.contains(dimensions_data.get(i))) {
                uniqueDim.add(dimensions_data.get(i));
                avalues.add(Float.parseFloat("0"));
            }
        }
        for (int i = 0; i < dimensions_data.size(); i++) {
            for (int j = 0; j < uniqueDim.size(); j++) {
                if (uniqueDim.get(j).equals(dimensions_data.get(i))) {
                    float f = avalues.get(j);
                    avalues.set(j, f + Float.parseFloat(values_data.get(i)));
                }
            }
        }
        float[] values = new float[uniqueDim.size()];
        for (int i = 0; i < uniqueDim.size(); i++) {
            values[i] = avalues.get(i);
        }
        List<SliceValue> sdimensions = new ArrayList<SliceValue>();
        int[] colors = new int[uniqueDim.size()];
        for (int i = 0; i < uniqueDim.size(); i++) {
            colors[i] = ChartUtils.COLORS[i];
        }
        for (int i = 0; i < uniqueDim.size(); ++i) {
            sdimensions.add(new SliceValue(values[i], colors[i]));
            sdimensions.get(i).setLabel(uniqueDim.get(i).toString());
        }

        PieChartData data = new PieChartData(sdimensions);
        data.setHasLabels(true);
        data.setHasLabelsOutside(true);
        return data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pie, container, false);
        RelativeLayout layout = (RelativeLayout) rootView;
        PieChartView pieChartView = new PieChartView(getActivity());
        try {
            pieChartView.setPieChartData(generatePieChartData());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /** Note: Chart is within ViewPager so enable container scroll mode. **/
        pieChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        pieChartView.setCircleFillRatio(0.7f);
        pieChartView.setOnValueTouchListener(new ValueTouchListener());
        layout.addView(pieChartView);

        return rootView;
    }

    private class ValueTouchListener implements PieChartOnValueSelectListener {

        @Override
        public void onValueDeselected() {
        }

        @Override
        public void onValueSelected(int i, SliceValue sliceValue) {
            if(labeltype!=0){
                sliceValue.setLabel(uniqueDim.get(i));
                labeltype=0;
            }
            else {
                sliceValue.setLabel(Float.toString( sliceValue.getValue()));
                labeltype=1;
            }
        }
    }
}