package com.example.b00sti.bbeacon.navigation;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.b00sti.bbeacon.base.BaseFragment;
import com.example.b00sti.bbeacon.ui_alarm.main.AlarmFragment;
import com.example.b00sti.bbeacon.ui_scanner.main.ScannerFragment;
import com.example.b00sti.bbeacon.ui_weather.main.WeatherFragment;

import java.util.ArrayList;

/**
 * Created by b00sti on 06.03.2017.
 */
class MainViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<BaseFragment> fragments = new ArrayList<>();
    private BaseFragment currentFragment;

    MainViewPagerAdapter(FragmentManager fm) {
        super(fm);

        fragments.clear();
        fragments.add(AlarmFragment.newInstance());
        fragments.add(WeatherFragment.newInstance());
        fragments.add(ScannerFragment.newInstance());

        currentFragment = fragments.get(0);
    }

    @Override
    public BaseFragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            currentFragment = ((BaseFragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    BaseFragment getCurrentFragment() {
        return currentFragment;
    }

}
