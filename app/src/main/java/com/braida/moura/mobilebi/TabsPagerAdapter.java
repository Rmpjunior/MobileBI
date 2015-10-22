package com.braida.moura.mobilebi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by Roberto on 20/10/2015.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new BarFragment();
            case 1:
                // Games fragment activity
                return new ColorBarFragment();
            case 2:
                // Movies fragment activity
                return new PieFragment();
            case 3:
                // Games fragment activity
                return new BubbleFragment();
            case 4:
                // Movies fragment activity
                return new TableFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 5;
    }
}
