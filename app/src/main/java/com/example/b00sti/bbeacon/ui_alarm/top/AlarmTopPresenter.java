package com.example.b00sti.bbeacon.ui_alarm.top;

import android.content.Context;
import android.support.v4.util.Pair;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BasePresenter;
import com.example.b00sti.bbeacon.ui_alarm.interactors.GetNextAlarmInteractor;
import com.example.b00sti.bbeacon.ui_alarm.main.AlarmItem;
import com.example.b00sti.bbeacon.utils.TimeUtils;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.res.StringRes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-13
 */

@EBean
public class AlarmTopPresenter extends BasePresenter<AlarmTopContract.View> implements AlarmTopContract.Presenter {

    private static final int INTERVAL_TO_REFRESH_TIME = 1;

    @StringRes(R.string.time_to_next_alarm) String toNextAlarm;

    @RootContext
    Context ctx;

    @Override
    public void onSubscribe() {

    }

    @Override
    public void onUnsubscribe() {

    }

    @Override
    public void setupRefreshingTime() {
        addDisposable(Observable.interval(INTERVAL_TO_REFRESH_TIME, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        setCurrentTime();
                    }
                }));
    }

    @Override
    public void setNextAlarm() {
        view.setTimeToNextAlarm(prepareToNextAlarm());
    }

    @Override
    public void setCurrentTime() {
        view.setTime(prepareActualTime());
    }

    private String prepareToNextAlarm() {
        String result;
        Pair<AlarmItem, Long> pair = new GetNextAlarmInteractor().executeToGetTime();
        AlarmItem alarmItem = pair.first;
        if (alarmItem.getTime() != null) {
            String time = TimeUtils.twoDatesBetweenTime(pair.second);
            result = String.format(toNextAlarm, time);
        } else {
            result = String.format(toNextAlarm, ctx.getString(R.string.none));
        }

        return result;
    }

    private String prepareActualTime() {
        Calendar c = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat(ctx.getString(R.string.date_format_hhmm), Locale.getDefault());

        return dateFormat.format(c.getTime());
    }
}
