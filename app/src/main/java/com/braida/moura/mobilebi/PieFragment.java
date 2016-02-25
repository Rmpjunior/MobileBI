package com.braida.moura.mobilebi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Created by Roberto on 20/10/2015.
 */
public class PieFragment extends Fragment {
    private Context mcontext;

    private PieChartData generatePieChartData() throws JSONException {
        String[] dimensions = ((Activity)mcontext).getIntent().getStringArrayExtra("dimensions");
        float[] values = ((Activity)mcontext).getIntent().getFloatArrayExtra("values");

        int numDimensions = dimensions.length;
        List<SliceValue> sdimensions = new ArrayList<SliceValue>();
        for (int i = 0; i < numDimensions; ++i) {
            sdimensions.add(new SliceValue(values[i], ChartUtils.pickColor()));
        }

        PieChartData data = new PieChartData(sdimensions);
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

        layout.addView(pieChartView);

        return rootView;
    }
}