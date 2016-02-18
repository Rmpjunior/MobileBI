package com.braida.moura.mobilebi;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Roberto on 17/02/2016.
 */
public class JSONAdapter extends BaseAdapter{
    JSONArray jArray = new JSONArray();



    @Override
    public int getCount() {
        return jArray.length();
    }

    @Override
    public Object getItem(int position) {
        return jArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}

