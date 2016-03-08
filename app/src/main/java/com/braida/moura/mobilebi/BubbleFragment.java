package com.braida.moura.mobilebi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.BubbleChartData;
import lecho.lib.hellocharts.model.BubbleValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.BubbleChartView;

/**
 * Created by Roberto on 20/10/2015.
 */


public class BubbleFragment extends Fragment {

    public ArrayList<String> uniqueDim = new ArrayList<String>();

    private BubbleChartData generateBubbleChartData() {
        int numBubbles = 10;
        Bundle bundle = getActivity().getIntent().getExtras();
        ArrayList<String> dimensions_items = bundle.getStringArrayList("dimensions_items");
        ArrayList<String> values_items = bundle.getStringArrayList("values_items");
        ArrayList<String> dimensions_data = bundle.getStringArrayList("dimensions_data");
        ArrayList<String> values_data = bundle.getStringArrayList("values_data");
        List<BubbleValue> values = new ArrayList<BubbleValue>();
        ListView list;
        list = (ListView) getActivity().findViewById(R.id.subtitle);

        for (int i = 0; i < dimensions_data.size(); i++) {
            if (!uniqueDim.contains(dimensions_data.get(i))) {
                uniqueDim.add(dimensions_data.get(i));
            }
        }

        Coloradapter adapter = new Coloradapter(getActivity(), uniqueDim);


        for (int i = 0; i < numBubbles; ++i) {
            BubbleValue value = new BubbleValue(i, (float) Math.random() * 100, (float) Math.random() * 1000);
            value.setColor(ChartUtils.pickColor());
            values.add(value);
        }

        BubbleChartData data = new BubbleChartData(values);

        data.setAxisXBottom(new Axis().setName("Axis X"));
        data.setAxisYLeft(new Axis().setName("Axis Y").setHasLines(true));
        return data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_bubble, container, false);
        RelativeLayout layout = (RelativeLayout) rootView;
        BubbleChartView bubbleChartView = new BubbleChartView(getActivity());
        bubbleChartView.setBubbleChartData(generateBubbleChartData());
        bubbleChartView.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);
        bubbleChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);

        layout.addView(bubbleChartView);

        return rootView;
    }


}