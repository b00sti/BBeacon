package com.example.b00sti.bbeacon.ui_alarm;

import android.graphics.Color;

import com.example.b00sti.bbeacon.utils.RealmUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

public class GetAlarmInteractor {

    public Observable<List<AlarmItem>> execute() {
        boolean fakeData = false;

        if (fakeData) {

            //fake time
            List<AlarmItem> itemsData = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                itemsData.add(new AlarmItem("Fragment Alarm as mvp - " + " Item : " + i, false, Color.BLACK, "18:18"));
            }

        return Observable.just(itemsData)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());

        } else {
            //right time
            return RealmUtils.FindAllAsync(AlarmItem.class);
        }

    }

}