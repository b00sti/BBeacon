package com.example.b00sti.bbeacon.ui_alarm.top;

import android.util.Log;
import android.widget.TextView;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseFragment;
import com.example.b00sti.bbeacon.base.RefreshableFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import static com.example.b00sti.bbeacon.ui_weather.top.WeatherTopFragment.TAG;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-14
 */

@EFragment(R.layout.alarm_top_fragment)
public class AlarmTopFragment extends BaseFragment<AlarmTopPresenter> implements AlarmTopContract.View, RefreshableFragment {


    @ViewById(R.id.tempValueTV) TextView timeTV;
    @ViewById(R.id.toNextAlarmTV) TextView toNextAlarmTV;

    @Bean
    AlarmTopPresenter alarmTopPresenter;

    public static AlarmTopFragment newInstance() {
        return new AlarmTopFragment_();
    }

    @Override
    protected AlarmTopPresenter registerPresenter() {
        alarmTopPresenter.attachView(this);
        return alarmTopPresenter;
    }

    @AfterViews
    void setupUI() {
        presenter.setCurrentTime();
        presenter.setNextAlarm();
        presenter.setupRefreshingTime();
    }

    @Override
    public void refresh() {
        presenter.setNextAlarm();
    }

    @Override
    public void willBeHidden() {

    }

    @Override
    public void willBeDisplayed() {

    }

    @UiThread
    @Override
    public void setTime(String time) {
        if (timeTV != null) {
            timeTV.setText(time);
        } else {
            Log.e(TAG, "setActualTimeToUI: timeTV null", null);
        }
    }

    @UiThread
    @Override
    public void setTimeToNextAlarm(String nextAlarm) {
        if (toNextAlarmTV != null) {
            toNextAlarmTV.setText(nextAlarm);
        } else {
            Log.e(TAG, "setActualTimeToUI: toNextAlarmTV null", null);
        }
    }
}
