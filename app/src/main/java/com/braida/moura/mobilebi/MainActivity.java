package com.braida.moura.mobilebi;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{
    AutoCompleteTextView textCube;
    Button btnCharts;
    Button btnGo;
    ListView dimensions;
    ArrayList <String> checkedValue;
    ListView values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textCube = (AutoCompleteTextView) findViewById(R.id.textCube);
        btnCharts = (Button)findViewById(R.id.charts);
        String[] Cubos = getResources().getStringArray(R.array.cubos);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,Cubos);
        textCube.setAdapter(adapter);
        dimensions = (ListView) findViewById(R.id.dimensions);
        values = (ListView) findViewById(R.id.values);
        ArrayList<String> dimensions_items= new ArrayList<String>();
        ArrayList<String> values_items = new ArrayList<String>();
        values_items.add("Budget");
        dimensions_items.add("Year");
        dimensions_items.add("Area");
        dimensions_items.add("Company size");
        Listadapter Adapter = new Listadapter(this,dimensions_items);
        Listadapter Adapter2 = new Listadapter(this, values_items);
        values.setAdapter(Adapter2);
        dimensions.setAdapter(Adapter);
        dimensions.setOnItemClickListener(this);
        btnCharts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View vw) {
                Intent myIntent = new Intent(MainActivity.this, Charts.class);

                MainActivity.this.startActivity(myIntent);
            }
        });
    }



    @Override
    public void onItemClick(AdapterView arg0, View v, int position, long arg3) {
        CheckBox cb = (CheckBox) v.findViewById(R.id.checkbox);
        TextView tv = (TextView) v.findViewById(R.id.name);
        cb.performClick();
        if (cb.isChecked()) {
            checkedValue.add(tv.getText().toString());
        } else if (!cb.isChecked()) {
            checkedValue.remove(tv.getText().toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
