package com.braida.moura.mobilebi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
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
        ArrayList<Float> values1 = new ArrayList<Float>();
        ArrayList<Float> values2 = new ArrayList<Float>();
        ArrayList<Float> values3 = new ArrayList<Float>();

        for(int i=0;i<values_data.size()-2;i++){
            values1.add(Float.parseFloat(values_data.get(i)));
            values2.add(Float.parseFloat(values_data.get(i+1)));
            values3.add(Float.parseFloat(values_data.get(i+2)));
        }

        for (int i = 0; i < dimensions_data.size(); i++) {
            if (!uniqueDim.contains(dimensions_data.get(i))) {
                uniqueDim.add(dimensions_data.get(i));
            }
        }

        BubbleValue value_zero = new BubbleValue(values1.get(0).floatValue(),values2.get(0).floatValue(),values3.get(0).floatValue());
        values.add(value_zero);

        for (int i = 1; i < values1.size(); ++i) {
            int j;
            int c=0;
            BubbleValue value = new BubbleValue(values1.get(i).floatValue(),values2.get(i).floatValue(),values3.get(i).floatValue());
            for(j=0;j<uniqueDim.size();j++){
                if(dimensions_data.get((i+2)/3).equals(uniqueDim.get(j))){
                value.setColor(ChartUtils.COLORS[j%5]);
                    value.setLabel(dimensions_data.get(j));
                }
            }
            values.add(value);
        }

        BubbleChartData data = new BubbleChartData(values);

        data.setAxisXBottom(new Axis().setName(values_items.get(0)));
        data.setAxisYLeft(new Axis().setName(values_items.get(1)).setHasLines(true));
        data.setHasLabelsOnlyForSelected(true);
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