package com.braida.moura.mobilebi;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Roberto on 07/03/2016.
 */
public class Coloradapter {
    ArrayList<String> list;
    Activity context;
    int[] color;

    public Coloradapter(Activity context, ArrayList <String> list) {
        super();
        this.context = context;
        this.list = list;
        color = new int[list.size()];
    }

    private class ViewHolder {
        TextView text;
        Button colorbox;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.color_item, null);
            holder = new ViewHolder();

            holder.text = (TextView) convertView
                    .findViewById(R.id.text);
            holder.colorbox = (Button) convertView
                    .findViewById(R.id.colorbox);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }



        holder.text.setText(list.get(position));
        holder.colorbox.setBackgroundColor(color[position]);



        return convertView;

    }

}
