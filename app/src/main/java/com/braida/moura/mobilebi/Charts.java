package com.braida.moura.mobilebi;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.Toast;
import android.widget.Toolbar;

import java.lang.reflect.Field;
import java.util.ArrayList;



public class Charts extends FragmentActivity implements ActionBar.TabListener {
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    private Toolbar toolbar;
    String stringSpeak;
    int atual;
    ArrayList<String> tabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initilization
        setContentView(R.layout.activity_charts);
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(2);
       actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);
        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        tabs=bundle.getStringArrayList("tabs");
        Intent intent = new Intent(this, TabsPagerAdapter.class);
        intent.putExtras(bundle);
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            // Ignore
        }
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                //actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        /*Intent recintent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "AndroidBite Voice Recognition...");
        startActivityForResult(recintent, 100);*/
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_charts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.voice_command:
                Intent recintent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                recintent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                recintent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak");
                startActivityForResult(recintent, 100);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100: {
                if (resultCode == RESULT_OK && null != data) {
                    atual=viewPager.getCurrentItem();
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    stringSpeak=result.get(0);
                    if(stringSpeak.toLowerCase().contains("next")){
                        if(atual==(tabs.size()-1)){
                            viewPager.setCurrentItem(0);
                        }
                        else{
                            viewPager.setCurrentItem(atual+1);
                        }}
                    else
                    if(stringSpeak.toLowerCase().contains("back")){
                        if(atual==0){
                            viewPager.setCurrentItem(tabs.size()-1);
                        }
                        else{
                            viewPager.setCurrentItem(atual-1);
                        }}
                    else{
                        Toast.makeText(getApplicationContext(),"Invalid Command",Toast.LENGTH_LONG).show();
                    }
                }
                break;


            }


        }


    }
}