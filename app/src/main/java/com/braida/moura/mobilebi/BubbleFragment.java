package com.braida.moura.mobilebi;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.BubbleChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.BubbleChartData;
import lecho.lib.hellocharts.model.BubbleValue;
import lecho.lib.hellocharts.view.BubbleChartView;

/**
 * Created by Roberto on 20/10/2015.
 */


public class BubbleFragment extends Fragment {

    public ArrayList<String> uniqueDim = new ArrayList<String>();
    public int nro;
    public int label=0;
    ArrayList<String> dimensions_items;
    ArrayList<String> values_items;
    ArrayList<String> dimensions_data;
    ArrayList<String> values_data;

    private BubbleChartData generateBubbleChartData() {
        int numBubbles = 10;
        Bundle bundle = getActivity().getIntent().getExtras();
        dimensions_items = bundle.getStringArrayList("dimensions_items");
        values_items = bundle.getStringArrayList("values_items");
        dimensions_data = bundle.getStringArrayList("dimensions_data");
        values_data = bundle.getStringArrayList("values_data");
        nro=values_data.size();
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

        BubbleValue value_zero = new BubbleValue(values1.get(0),values2.get(0),values3.get(0));
        values.add(value_zero);
        int [] colors = new int[uniqueDim.size()];
        Random rand= new Random();

        for(int i=0;i<colors.length;i++)
        {
            colors[i]= Color.rgb(55+rand.nextInt(200),55+rand.nextInt(200),55+rand.nextInt(200));
        }

        for (int i = 1; i < values1.size(); ++i) {
            int j;
            BubbleValue value = new BubbleValue(values1.get(i),values2.get(i),values3.get(i));
            for(j=0;j<uniqueDim.size();j++){
                if(dimensions_data.get((i+2)/3).equals(uniqueDim.get(j))){
                value.setColor(colors[j]);
                    value.setLabel("");
                }
            }
            values.add(value);
        }

        BubbleChartData data = new BubbleChartData(values);

        data.setAxisXBottom(new Axis().setName(values_items.get(0)));
        data.setAxisYLeft(new Axis().setName(values_items.get(1)).setHasLines(true));
        data.setHasLabels(true);
        if(nro>10 && nro<=40) {data.setBubbleScale(0.4f);}
        if(nro>40 && nro<=100) {data.setBubbleScale(0.3f);}
        if(nro>100 && nro<=130) {data.setBubbleScale(0.2f);}
        if(nro>130 && nro<=200) {data.setBubbleScale(0.1f);}
        if(nro>200 && nro<=250) {data.setBubbleScale(0.05f);}
        if(nro>250) {data.setBubbleScale(0.02f);}

        return data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {

        View rootView = inflater.inflate(R.layout.fragment_bubble, container, false);
        RelativeLayout layout = (RelativeLayout) rootView;
        BubbleChartView bubbleChartView = new BubbleChartView(getActivity());
        bubbleChartView.setBubbleChartData(generateBubbleChartData());
        bubbleChartView.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);
        bubbleChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        layout.addView(bubbleChartView);
        bubbleChartView.setOnValueTouchListener(new ValueTouchListener());

        return rootView;
    }

    private class ValueTouchListener implements BubbleChartOnValueSelectListener {

        @Override
        public void onValueDeselected() {
        }


        @Override
        public void onValueSelected(int i, BubbleValue bubbleValue) {
            if(label==0) {
                bubbleValue.setLabel(dimensions_data.get(i/3)+" - "+bubbleValue.getZ());
                label=1;
            }
            else{
                bubbleValue.setLabel("");
                label=0;
            }
        }
    }


}