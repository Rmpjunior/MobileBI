package com.braida.moura.mobilebi;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {
    AutoCompleteTextView textCube;
    Button btnCharts;
    Button btnGo;

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
        btnCharts.setVisibility(View.INVISIBLE);
        btnGo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View vw){
                btnCharts.setVisibility(View.VISIBLE);
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
