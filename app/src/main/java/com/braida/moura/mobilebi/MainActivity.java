package com.braida.moura.mobilebi;

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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{
    AutoCompleteTextView textCube;
    Button btnCharts;
    Button btnGo;
    ListView dimensions;
    ArrayList <String> checkedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textCube = (AutoCompleteTextView) findViewById(R.id.textCube);
        btnCharts = (Button)findViewById(R.id.charts);
        btnGo = (Button)findViewById(R.id.go);
        String[] Cubos = getResources().getStringArray(R.array.cubos);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,Cubos);
        textCube.setAdapter(adapter);
        dimensions = (ListView) findViewById(R.id.dimensions);
        final ArrayList<String> dimensions_items= new ArrayList<String>();
        dimensions_items.add("Primeira dimensão");
        dimensions_items.add("Segunda dimensão");
        dimensions_items.add("Terceira dimensão");
        Listadapter Adapter = new Listadapter(this,dimensions_items);
        dimensions.setAdapter(Adapter);
        dimensions.setOnItemClickListener(this);
        btnGo.setOnClickListener(new View.OnClickListener() {
                                     public void onClick(View vw) {

                                     }
                                 }
        );
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
