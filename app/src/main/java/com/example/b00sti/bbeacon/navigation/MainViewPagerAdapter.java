package com.example.b00sti.bbeacon.navigation;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.b00sti.bbeacon.alarm.AlarmFragment;
import com.example.b00sti.bbeacon.base.BaseRefreshableFragment;
import com.example.b00sti.bbeacon.scanner.ScannerFragment;
import com.example.b00sti.bbeacon.weather.WeatherFragment;

import java.util.ArrayList;

/**
 * Created by b00sti on 06.03.2017.
 */

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<BaseRefreshableFragment> fragments = new ArrayList<>();
    private BaseRefreshableFragment currentFragment;

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);

        fragments.clear();
        fragments.add(AlarmFragment.newInstance());
        fragments.add(WeatherFragment.newInstance());
        fragments.add(ScannerFragment.newInstance());
    }

    @Override
    public BaseRefreshableFragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            currentFragment = ((BaseRefreshableFragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    public BaseRefreshableFragment getCurrentFragment() {
        return currentFragment;
    }

}
