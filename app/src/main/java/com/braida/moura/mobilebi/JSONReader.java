package com.braida.moura.mobilebi;
import android.content.Context;
import android.content.res.AssetManager;
import android.view.View;
import java.io.IOException;
import java.io.InputStream;
/**
 * Created by Lucas Braida on 23/02/2016.
 */
public class JSONReader {
    Context mContetx;
    public JSONReader(Context context) {
        mContetx=context;
    }
    public String loadJSONFromAsset() {
        String json = null;
        try {
            AssetManager as = mContetx.getAssets();
            InputStream is = as.open("metadados.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}