package com.braida.moura.mobilebi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Roberto on 20/10/2015.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter{
    private Context mcontext;
    public TabsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mcontext=context;
    }

    @Override
    public Fragment getItem(int index) {
        ArrayList<String> tabs = ((Activity)mcontext).getIntent().getStringArrayListExtra("tabs");
        switch (index) {
            case 0: {
                if (tabs.size() > 0) {
                    switch (tabs.get(0)) {
                        case "Bar":
                            return new BarFragment();
                        case "Color Bar":
                            return new ColorBarFragment();
                        case "Pie":
                            return new PieFragment();
                        case "Bubble":
                            return new BubbleFragment();
                        case "Table":
                            return new TableFragment();
                    }
                }
            }
            case 1: {
                if (tabs.size()>1) {
                    switch (tabs.get(1)) {
                        case "Bar":
                            return new BarFragment();
                        case "Color Bar":
                            return new ColorBarFragment();
                        case "Pie":
                            return new PieFragment();
                        case "Bubble":
                            return new BubbleFragment();
                        case "Table":
                            return new TableFragment();
                    }
                }
            }
            case 2: {
                if (tabs.size()>2) {
                    switch (tabs.get(2)) {
                        case "Bar":
                            return new BarFragment();
                        case "Color Bar":
                            return new ColorBarFragment();
                        case "Pie":
                            return new PieFragment();
                        case "Bubble":
                            return new BubbleFragment();
                        case "Table":
                            return new TableFragment();
                    }
                }
            }
            case 3: {
                if (tabs.size()>3) {
                    switch (tabs.get(3)) {
                        case "Bar":
                            return new BarFragment();
                        case "Color Bar":
                            return new ColorBarFragment();
                        case "Pie":
                            return new PieFragment();
                        case "Bubble":
                            return new BubbleFragment();
                        case "Table":
                            return new TableFragment();
                    }
                }
            }
            case 4: {
                if (tabs.size()>4) {
                    switch (tabs.get(4)) {
                        case "Bar":
                            return new BarFragment();
                        case "Color Bar":
                            return new ColorBarFragment();
                        case "Pie":
                            return new PieFragment();
                        case "Bubble":
                            return new BubbleFragment();
                        case "Table":
                            return new TableFragment();
                    }
                }
            }
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        ArrayList<String> tabs = ((Activity)mcontext).getIntent().getStringArrayListExtra("tabs");
        return tabs.size();
    }
}
