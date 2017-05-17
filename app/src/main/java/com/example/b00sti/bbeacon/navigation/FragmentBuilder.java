package com.example.b00sti.bbeacon.navigation;

import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;

import com.example.b00sti.bbeacon.base.EmptyFragment;
import com.example.b00sti.bbeacon.ui_alarm.add.AddNewAlarmFragment;
import com.example.b00sti.bbeacon.ui_alarm.disable.DisableAlarmFragment;
import com.example.b00sti.bbeacon.ui_alarm.main.AlarmFragment;
import com.example.b00sti.bbeacon.ui_alarm.top.AlarmTopFragment;
import com.example.b00sti.bbeacon.ui_scanner.add.AddNewBeaconFragment;
import com.example.b00sti.bbeacon.ui_scanner.details.BeaconDetailsFragment;
import com.example.b00sti.bbeacon.ui_scanner.main.ScannerFragment;
import com.example.b00sti.bbeacon.ui_scanner.top.ScannerTopFragment;
import com.example.b00sti.bbeacon.ui_weather.details.WeatherDetailsFragment;
import com.example.b00sti.bbeacon.ui_weather.main.WeatherFragment;
import com.example.b00sti.bbeacon.ui_weather.top.WeatherTopFragment;

import org.androidannotations.annotations.EBean;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-14
 */

@EBean(scope = EBean.Scope.Singleton)
public class FragmentBuilder {

    public static final int TOP_ALARM = 0;
    public static final int ALARM = 1;
    public static final int TOP_WEATHER = 2;
    public static final int WEATHER = 3;
    public static final int TOP_SCANNER = 4;
    public static final int SCANNER = 5;
    public static final int ADD_NEW_ALARM = 6;
    public static final int ADD_NEW_BEACON = 7;
    public static final int WEATHER_DETAILS = 8;
    public static final int DISABLE_ALARM = 9;
    public static final int BEACON_DETAILS = 10;
    public static final int EMPTY = 100;

    public Fragment newFragment(@FragBuild int fragmentId) {

        switch (fragmentId) {
            case ALARM:
                return AlarmFragment.newInstance();
            case EMPTY:
                return EmptyFragment.newInstance();
            case SCANNER:
                return ScannerFragment.newInstance();
            case TOP_ALARM:
                return AlarmTopFragment.newInstance();
            case TOP_SCANNER:
                return ScannerTopFragment.newInstance();
            case TOP_WEATHER:
                return WeatherTopFragment.newInstance();
            case WEATHER:
                return WeatherFragment.newInstance();
            case ADD_NEW_ALARM:
                return AddNewAlarmFragment.newInstance();
            case ADD_NEW_BEACON:
                return AddNewBeaconFragment.newInstance();
            case WEATHER_DETAILS:
                return WeatherDetailsFragment.newInstance();
            case DISABLE_ALARM:
                return DisableAlarmFragment.newInstance();
            case BEACON_DETAILS:
                return BeaconDetailsFragment.newInstance();
            default:
                return EmptyFragment.newInstance();
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            TOP_ALARM,
            ALARM,
            TOP_WEATHER,
            WEATHER,
            TOP_SCANNER,
            SCANNER,
            EMPTY,
            ADD_NEW_ALARM,
            ADD_NEW_BEACON,
            WEATHER_DETAILS,
            DISABLE_ALARM
            WEATHER_DETAILS,
            BEACON_DETAILS
    })
    public @interface FragBuild {
    }
}
