package com.example.b00sti.bbeacon.utils;

import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;

import com.example.b00sti.bbeacon.base.EmptyFragment;
import com.example.b00sti.bbeacon.ui_alarm.AlarmFragment;
import com.example.b00sti.bbeacon.ui_alarm.AlarmTopFragment;
import com.example.b00sti.bbeacon.ui_others.AddNewAlarmFragment;
import com.example.b00sti.bbeacon.ui_others.AddNewBeaconFragment;
import com.example.b00sti.bbeacon.ui_scanner.ScannerFragment;
import com.example.b00sti.bbeacon.ui_scanner.ScannerTopFragment;
import com.example.b00sti.bbeacon.ui_weather.WeatherFragment;

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
                return EmptyFragment.newInstance();
            case WEATHER:
                return WeatherFragment.newInstance();
            case ADD_NEW_ALARM:
                return AddNewAlarmFragment.newInstance();
            case ADD_NEW_BEACON:
                return AddNewBeaconFragment.newInstance();
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
            ADD_NEW_BEACON
    })
    public @interface FragBuild {
    }
}
