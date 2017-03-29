package com.example.b00sti.bbeacon.ui_alarm.main;

import android.app.Activity;
import android.util.Log;

import com.example.b00sti.bbeacon.base.BasePresenter;
import com.example.b00sti.bbeacon.ui_alarm.interactors.GetAlarmInteractor;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

@EBean
public class AlarmPresenter extends BasePresenter<AlarmContract.View> implements AlarmContract.Presenter {
    private static final String TAG = "AlarmPresenter";

    @RootContext
    Activity ctx;

    @Override
    public void fetchData() {
        Log.d(TAG, "fetchData: ");

        addDisposable(new GetAlarmInteractor().execute().subscribe(new Consumer<List<AlarmItem>>() {
            @Override
            public void accept(List<AlarmItem> items) throws Exception {
                Log.d(TAG, "accept: " + items.size());
                view.refreshData(items);
            }
        }));
    }

    @Override
    public void onSubscribe() {

    }

    @Override
    public void onUnsubscribe() {

    }
}
