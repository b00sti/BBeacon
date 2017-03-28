package com.example.b00sti.bbeacon.ui_alarm;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;

import com.example.b00sti.bbeacon.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

import static com.example.b00sti.bbeacon.ui_weather.WeatherTopFragment.TAG;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-14
 */

@EFragment(R.layout.alarm_top_fragment)
public class AlarmTopFragment extends Fragment {

    @ViewById(R.id.tempValueTV) TextView timeTV;
    @ViewById(R.id.toNextAlarmTV) TextView toNextAlarmTV;
    @StringRes(R.string.time_to_next_alarm) String toNextAlarm;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static AlarmTopFragment newInstance() {
        return new AlarmTopFragment_();
    }

    @AfterViews
    void setupUI() {
        setActualTimeToUI();
        setToNextAlarmToUI();
        compositeDisposable.add(
                Observable.interval(10, TimeUnit.SECONDS)
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                setActualTimeToUI();
                                setToNextAlarmToUI();
                            }
                        }));
    }

    @UiThread
    void setActualTimeToUI() {
        if (timeTV != null) {
            timeTV.setText(prepareActualTime());
        } else {
            Log.e(TAG, "setActualTimeToUI: timeTV null", null);
        }
    }

    @UiThread
    void setToNextAlarmToUI() {
        if (toNextAlarmTV != null) {
            toNextAlarmTV.setText(prepareToNextAlarm());
        } else {
            Log.e(TAG, "setActualTimeToUI: toNextAlarmTV null", null);
        }
    }

    private String prepareToNextAlarm() {
        return String.format(toNextAlarm, "7h 33min");
    }

    private String prepareActualTime() {

        Calendar c = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        return dateFormat.format(c.getTime());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
