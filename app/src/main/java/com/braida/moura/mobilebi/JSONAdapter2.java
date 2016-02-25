package com.braida.moura.mobilebi;

import android.content.Context;
import android.view.LayoutInflater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Lucas Braida on 20/02/2016.
 */

public class JSONAdapter2 {
    JSONArray mJsonArray;
    int mb;
    ArrayList<String> vectorbuild = new ArrayList<String>();
    String[] usename = new String[mb];

    public JSONAdapter2(JSONArray jsonArray, int b, String[] names) {
        mJsonArray = jsonArray;
        mb = b;
        usename=names;
    }

    public ArrayList<String> buildvector() throws JSONException {
        for(int i=0;i<mJsonArray.length();i++){
            for (int j=0;j<mb;j++) {
                vectorbuild.add(mJsonArray.optJSONObject(i).getString(usename[j]));
            }
        }

        return vectorbuild;
    }
    public Object getItem(int position){
        return mJsonArray.optJSONObject(position);
    }
}
