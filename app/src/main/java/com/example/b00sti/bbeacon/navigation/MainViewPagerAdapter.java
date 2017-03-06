package com.example.b00sti.bbeacon.navigation;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by b00sti on 06.03.2017.
 */

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<DemoFragment> fragments = new ArrayList<>();
    private DemoFragment currentFragment;

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);

        fragments.clear();
        fragments.add(DemoFragment.newInstance(0));
        fragments.add(DemoFragment.newInstance(1));
        fragments.add(DemoFragment.newInstance(2));
        fragments.add(DemoFragment.newInstance(3));
        fragments.add(DemoFragment.newInstance(4));
    }

    @Override
    public DemoFragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            currentFragment = ((DemoFragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    public DemoFragment getCurrentFragment() {
        return currentFragment;
    }

}
