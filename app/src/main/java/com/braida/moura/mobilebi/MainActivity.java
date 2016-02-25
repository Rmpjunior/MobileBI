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
import android.widget.Toast;

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

import java.io.IOException;
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
    ArrayList<String> dimensions_items= new ArrayList<String>();
    ArrayList<String> values_items = new ArrayList<String>();
    ArrayList<String> dimensions_items_checked = new ArrayList<String>();
    ArrayList<String> values_items_checked = new ArrayList<String>();

    public String loadJSON(int i, int j ,String[] names) {
        JSONArray jsonarray = null;
        try {
            JSONReader jsonreader = new JSONReader(getApplicationContext());
            jsonarray = new JSONArray(jsonreader.loadJSONFromAsset("metadados.json"));
            return jsonarray.optJSONObject(i).getString(names[j]);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int nrObj() throws JSONException {
            JSONReader jsonreader = new JSONReader(getApplicationContext());
            JSONArray jsonarray = new JSONArray(jsonreader.loadJSONFromAsset("metadados.json"));
            return jsonarray.length();
    }

    public int nrKeys() throws JSONException {
            JSONReader jsonreader = new JSONReader(getApplicationContext());
            JSONArray jsonarray = new JSONArray(jsonreader.loadJSONFromAsset("metadados.json"));
            return jsonarray.optJSONObject(0).length();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textCube = (AutoCompleteTextView) findViewById(R.id.textCube); //define a caixa de texto que seleciona o cubo
        btnCharts = (Button)findViewById(R.id.charts); //define o botão que abre a janela de gráficos
        String[] Cubos = getResources().getStringArray(R.array.cubos); //define a array de strings com os cubos existentes na resource de strings
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,Cubos); //cria o adaptador para o autocomplete
        textCube.setAdapter(adapter); //define o adaptador
        dimensions = (ListView) findViewById(R.id.dimensions); //define a listview de dimensões
        values = (ListView) findViewById(R.id.values); //define a listview de valores
        String []names =    {"name","qualification"};
        for (int i = 0; i < 4; i++) {
                String qualification = loadJSON(i, 1, names);
                String name = loadJSON(i, 0, names);
                if (qualification.equals("dimensão")) {
                    dimensions_items.add(name);
                } else {
                    values_items.add(name);
                }
            }

        final Listadapter Adapter = new Listadapter(this,dimensions_items); //cria adaptador para a listview de dimensões
        final Listadapter Adapter2 = new Listadapter(this, values_items); //cria adaptador para a listview de valores
        values.setAdapter(Adapter2); //define o adaptador
        dimensions.setAdapter(Adapter); //define o adaptador
        btnCharts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View vw) {
                dimensions_items_checked.clear();
                values_items_checked.clear();
                for (int i=0;i<dimensions_items.size();i++)
                {
                    if(Adapter.itemChecked[i]){
                        dimensions_items_checked.add(dimensions_items.get(i));
                    }
                }
                for (int i=0;i<values_items.size();i++)
                {
                    if(Adapter2.itemChecked[i]){
                        values_items_checked.add(values_items.get(i));
                    }
                }
                ArrayList<String> tabs = new ArrayList<String>();
                Intent myIntent = new Intent(MainActivity.this, Charts.class);
                Intent dataIntent = new Intent(MainActivity.this, TabsPagerAdapter.class);
                if(dimensions_items_checked.size()>0 && dimensions_items_checked.size()<6 && values_items_checked.size()==1){
                tabs.add("Pie");
                    SendPieData(textCube.getText().toString());
                }
                if(dimensions_items_checked.size()>0 && dimensions_items_checked.size()<10 && values_items_checked.size()==1){
                tabs.add("Bar");
                }
                if(dimensions_items_checked.size()>1 && dimensions_items_checked.size()<10 && values_items_checked.size()==1){
                    tabs.add("Bar");
                }
                if(dimensions_items_checked.size()>0 && values_items_checked.size()==3){
                    tabs.add("Bubble");
                }
                tabs.add("Table");
                dataIntent.putExtra("tabs", tabs);
                myIntent.putExtra("tabs",tabs);
                MainActivity.this.startActivity(myIntent);
            }
        });
    }

    public void SendPieData(String cube){
        ArrayList<String> dimensions = new ArrayList<String>();
        //Chama classe para receber a arraylist de dimensões e valores do JSON
        Intent intent = new Intent(MainActivity.this, PieFragment.class);
        intent.putExtra("dimensions", dimensions);
       // intent.putExtra("values", values);
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
