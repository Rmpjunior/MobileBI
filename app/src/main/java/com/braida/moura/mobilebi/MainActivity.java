package com.braida.moura.mobilebi;

import android.content.Intent;
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
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


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
    ArrayList<String> dimensions_data = new ArrayList<String>();
    ArrayList<String> values_data = new ArrayList<String>();
    Listadapter Adapter = new Listadapter(this, dimensions_items);
    Listadapter Adapter2 = new Listadapter(this,values_items);

    public String loadJSON(int i, String name, String file) {
        JSONArray jsonarray = null;
        try {
            JSONReader jsonreader = new JSONReader(getApplicationContext());
            jsonarray = new JSONArray(jsonreader.loadJSONFromAsset(file));
            return jsonarray.optJSONObject(i).getString(name);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    public int nrObj(String file){
            JSONReader jsonreader = new JSONReader(getApplicationContext());
        JSONArray jsonarray = null;
        try {
            jsonarray = new JSONArray(jsonreader.loadJSONFromAsset(file));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonarray.length();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textCube = (AutoCompleteTextView) findViewById(R.id.textCube); //define a caixa de texto que seleciona o cubo
        btnCharts = (Button)findViewById(R.id.charts); //define o botão que abre a janela de gráficos
        btnGo = (Button) findViewById(R.id.go);
        String[] Cubos = getResources().getStringArray(R.array.cubos); //define a array de strings com os cubos existentes na resource de strings
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,Cubos); //cria o adaptador para o autocomplete
        textCube.setAdapter(adapter); //define o adaptador
        dimensions = (ListView) findViewById(R.id.dimensions); //define a listview de dimensões
        values = (ListView) findViewById(R.id.values); //define a listview de valores
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] names = {"name", "qualification"};
                for (int i = 0; i < nrObj(textCube.getText()+"_meta.json"); i++) {
                    String qualification = loadJSON(i, "qualification", textCube.getText() + "_meta.json");
                    String name = loadJSON(i, "name", textCube.getText() + "_meta.json");
                    if (qualification.equals("dimensão")) {
                        dimensions_items.add(name);
                    } else {
                        values_items.add(name);
                    }
                    Adapter = new Listadapter(MainActivity.this, dimensions_items); //cria adaptador para a listview de dimensões
                    Adapter2 = new Listadapter(MainActivity.this, values_items); //cria adaptador para a listview de valores
                    values.setAdapter(Adapter2); //define o adaptador
                    dimensions.setAdapter(Adapter); //define o adaptador
                }
            }
        });
        btnCharts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View vw) {
                dimensions_items_checked.clear();
                values_items_checked.clear();
                dimensions_data.clear();
                values_data.clear();
                for (int i = 0; i < dimensions_items.size(); i++) {
                    if (Adapter.itemChecked[i]) {
                        dimensions_items_checked.add(dimensions_items.get(i));
                    }
                }
                for (int i = 0; i < values_items.size(); i++) {
                    if (Adapter2.itemChecked[i]) {
                        values_items_checked.add(values_items.get(i));
                    }
                }
                for (int i = 0; i < nrObj(textCube.getText() + "_data.json"); i++) {
                    for (int j = 0; j < dimensions_items_checked.size(); j++) {
                        String s = dimensions_items_checked.get(j);
                        dimensions_data.add(loadJSON(i, s, textCube.getText() + "_data.json"));
                    }
                }
                for (int i = 0; i < nrObj("Alunos_data.json"); i++) {
                    for (int j = 0; j < values_items_checked.size(); j++) {
                        values_data.add(loadJSON(i, values_items_checked.get(j), textCube.getText() + "_data.json"));
                    }
                }
                ArrayList<String> tabs = new ArrayList<String>();
                Intent myIntent = new Intent(MainActivity.this, Charts.class);
                if(dimensions_items_checked.size()==1 && values_items_checked.size()==1){
                tabs.add("Pie");
                }
                if(dimensions_items_checked.size()==1  && values_items_checked.size()==1){
                tabs.add("Bar");
                }
                if(dimensions_items_checked.size()==2  && values_items_checked.size()==1){
                    tabs.add("Color Bar");
                }
                if(dimensions_items_checked.size()==1 && values_items_checked.size()==3){
                    tabs.add("Bubble");
                }
                tabs.add("Table");
                myIntent.putExtra("tabs", tabs);
                myIntent.putExtra("dimensions_items", dimensions_items_checked);
                myIntent.putExtra("dimensions_data", dimensions_data);
                myIntent.putExtra("values_items", values_items_checked);
                myIntent.putExtra("values_data", values_data);
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
