package com.braida.moura.mobilebi;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Roberto on 20/10/2015.
 */

public class TableFragment extends Fragment {

    public ArrayList<String> uniqueDim = new ArrayList<String>();
    public TableLayout layout;
    ArrayList<String> dimensions_items;
    ArrayList<String> values_items;
    ArrayList<String> dimensions_data;
    ArrayList<String> values_data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_table, container, false);
        layout = (TableLayout) rootView.findViewById(R.id.table_main);
        rootView.setBackgroundColor(Color.GRAY);
        Bundle bundle = getActivity().getIntent().getExtras();
        dimensions_items = bundle.getStringArrayList("dimensions_items");
        values_items = bundle.getStringArrayList("values_items");
        dimensions_data = bundle.getStringArrayList("dimensions_data");
        values_data = bundle.getStringArrayList("values_data");

        header();
        data();

        return rootView;


    }

    public void header()
    {
        TableRow first_row = new TableRow(getActivity());
        TextView head = new TextView(getActivity());
        String s="";
        for(int k=0;k<dimensions_items.size();k++)
        {
            s+=dimensions_items.get(k)+"/";
        }
        s=s.substring(0,s.length()-1);
        head.setText(s);
        head.setTextColor(Color.WHITE);
        head.setBackgroundColor(Color.BLACK);
        head.setGravity(Gravity.CENTER);
        first_row.addView(head);
        for(int i=0;i<values_items.size();i++)
        {
            TextView item = new TextView(getActivity());
            item.setText(values_items.get(i));
            item.setTextColor(Color.WHITE);
            item.setBackgroundColor(Color.BLACK);
            item.setGravity(Gravity.CENTER);
            first_row.addView(item);
        }
        layout.addView(first_row);


    }

    public void data()
    {
        for (int i = 0; i < dimensions_data.size(); i++) {
            if (!uniqueDim.contains(dimensions_data.get(i))) {
                uniqueDim.add(dimensions_data.get(i));
            }
        }
        for(int i=0;i<uniqueDim.size();i++)
        {
            float[] values = new float[values_items.size()];
            TableRow tbrow = new TableRow(getActivity());
            TextView dim=new TextView(getActivity());
            dim.setText(uniqueDim.get(i));
            dim.setTextColor(Color.WHITE);
            dim.setGravity(Gravity.CENTER);
            if(i%2==0)
            dim.setBackgroundColor(Color.GRAY);
            else
            dim.setBackgroundColor(Color.DKGRAY);
            tbrow.addView(dim);
            for(int k=0;k<dimensions_data.size();k++)
            {

                if(dimensions_data.get(k).equals(uniqueDim.get(i)))
                {
                    int z=(k/dimensions_items.size())*values_items.size();
                    for(int j=z;j<z+values_items.size();j++)
                    {
                        values[j-z]+=Float.parseFloat(values_data.get(j));
                    }
                }
            }
            for(int p=0;p<values_items.size();p++)
            {
                TextView value=new TextView(getActivity());
                value.setText(String.valueOf(values[p]));
                value.setTextColor(Color.WHITE);
                value.setGravity(Gravity.CENTER);
                if(i%2==0)
                    value.setBackgroundColor(Color.GRAY);
                else
                    value.setBackgroundColor(Color.DKGRAY);
                tbrow.addView(value);
            }
            layout.addView(tbrow);
        }
        }


}