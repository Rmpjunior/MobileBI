package com.braida.moura.mobilebi;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * Created by Roberto on 20/10/2015.
 */
public class BarFragment extends Fragment {

    public ArrayList<String> uniqueDim = new ArrayList<String>();
    int labeltype = 0;

    private ColumnChartData generateColumnChartData() {
        Bundle bundle = getActivity().getIntent().getExtras();
        ArrayList<String> dimensions_items = bundle.getStringArrayList("dimensions_items");
        ArrayList<String> values_items = bundle.getStringArrayList("values_items");
        ArrayList<String> dimensions_data = bundle.getStringArrayList("dimensions_data");
        ArrayList<String> values_data = bundle.getStringArrayList("values_data");
        ArrayList<Column> columns = new ArrayList<Column>();
        ArrayList<SubcolumnValue> subcolumn = new ArrayList<SubcolumnValue>();
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

        ArrayList<AxisValue> axisvalues = new ArrayList<AxisValue>();

        for (int i = 0; i < uniqueDim.size(); ++i) {
            subcolumn.add(new SubcolumnValue(values[i], ChartUtils.COLOR_BLUE));
            subcolumn.get(i).setLabel(uniqueDim.get(i).toString());
        }


        Column column = new Column(subcolumn);
        column.setHasLabels(true);
        columns.add(column);
        ColumnChartData data = new ColumnChartData(columns);

        Axis axisX = new Axis();
        Axis axisy = new Axis();

        axisX.setName(dimensions_items.get(0));
        axisy.setValues(null);
        axisy.setName(values_items.get(0));
        axisX.setValues(null);
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisy);
        data.setValueLabelBackgroundColor(ChartUtils.COLOR_GREEN);
        return data;

    }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_bar, container, false);
            RelativeLayout layout = (RelativeLayout) rootView;
            ColumnChartView columnChartView = new ColumnChartView(getActivity());
            columnChartView.setColumnChartData(generateColumnChartData());
            columnChartView.setZoomType(ZoomType.HORIZONTAL);
            columnChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
            columnChartView.setValueSelectionEnabled(false);
            columnChartView.setZoomEnabled(false);
            columnChartView.setOnValueTouchListener(new ValueTouchListener());
            layout.addView(columnChartView);
            return rootView;
        }

    private class ValueTouchListener implements ColumnChartOnValueSelectListener {

        @Override
        public void onValueDeselected() {
        }


        @Override
        public void onValueSelected(int i, int i1, SubcolumnValue subcolumnValue) {
            if(labeltype!=0){
                subcolumnValue.setLabel(uniqueDim.get(i1));
                labeltype=0;
            } else {
                subcolumnValue.setLabel(Float.toString( subcolumnValue.getValue()));
                labeltype=1;
            }
        }
      }
    }


