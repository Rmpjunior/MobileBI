package com.braida.moura.mobilebi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * Created by Roberto on 20/10/2015.
 */
public class ColorBarFragment extends Fragment {

    private ColumnChartData generateColumnChartData() {
        int numSubcolumns = 1;
        int numColumns = 12;
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));
            }

            columns.add(new Column(values));
        }

        ColumnChartData data = new ColumnChartData(columns);

        data.setAxisXBottom(new Axis().setName("Axis X"));
        data.setAxisYLeft(new Axis().setName("Axis Y").setHasLines(true));
        return data;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_barcolor, container, false);
        RelativeLayout layout = (RelativeLayout) rootView;
        ColumnChartView columnChartView = new ColumnChartView(getActivity());
        columnChartView.setColumnChartData(generateColumnChartData());
        columnChartView.setZoomType(ZoomType.HORIZONTAL);
        columnChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        layout.addView(columnChartView);

        return rootView;
    }
}