package com.example.b00sti.bbeacon.navigation;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.b00sti.bbeacon.base.BaseRefreshableFragmentWithToolbar;
import com.example.b00sti.bbeacon.ui_alarm.main.AlarmFragment;
import com.example.b00sti.bbeacon.ui_scanner.ScannerFragment;
import com.example.b00sti.bbeacon.ui_weather.main.WeatherFragment;

import java.util.ArrayList;

/**
 * Created by b00sti on 06.03.2017.
 */

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<BaseRefreshableFragmentWithToolbar> fragments = new ArrayList<>();
    private BaseRefreshableFragmentWithToolbar currentFragment;

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);

        fragments.clear();
        fragments.add(AlarmFragment.newInstance());
        fragments.add(WeatherFragment.newInstance());
        fragments.add(ScannerFragment.newInstance());

        currentFragment = fragments.get(0);
    }

    @Override
    public BaseRefreshableFragmentWithToolbar getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            currentFragment = ((BaseRefreshableFragmentWithToolbar) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    public BaseRefreshableFragmentWithToolbar getCurrentFragment() {
        return currentFragment;
    }

}
