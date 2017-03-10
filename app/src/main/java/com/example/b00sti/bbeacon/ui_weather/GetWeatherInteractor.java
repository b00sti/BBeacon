package com.example.b00sti.bbeacon.ui_weather;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

public class GetWeatherInteractor {

    public Observable<List<WeatherItem>> execute() {
        //fake data
        List<WeatherItem> itemsData = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            itemsData.add(new WeatherItem("Fragment Weather as mvp - " + " Item : " + i));
        }

        return Observable.just(itemsData)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());

        //return RealmUtils.FindAllAsync(WeatherItem.class);
    }
}