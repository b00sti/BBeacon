package com.example.b00sti.bbeacon.ui_weather;

import com.example.b00sti.bbeacon.utils.RealmUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

public class GetWeatherInteractor {

    public Observable<List<WeatherItem>> execute() {
        boolean fakeData = true;

        if (fakeData) {
            //fake data
            List<WeatherItem> itemsData = new ArrayList<>();

            WeatherItem weatherItem1 = new WeatherItem("Outdoor",
                    "17 " + " \u2103",
                    "Open window !",
                    "50 %",
                    "1020 hPa",
                    false
            );
            itemsData.add(weatherItem1);

            WeatherItem weatherItem2 = new WeatherItem("Kids room",
                    "22 " + " \u2103",
                    "Check air conditioner !",
                    "65 %",
                    "1017 hPa",
                    true
            );
            itemsData.add(weatherItem2);

            WeatherItem weatherItem3 = new WeatherItem("Flowers",
                    "15 " + " \u2103",
                    "My flowers are thirsty ?",
                    "24 %",
                    "1000 hPa",
                    false
            );
            itemsData.add(weatherItem3);

            WeatherItem weatherItem4 = new WeatherItem("Bathroom",
                    "17 " + " \u2103",
                    "Is too cold ?",
                    "85 %",
                    "1023 hPa",
                    true
            );
            itemsData.add(weatherItem4);

            WeatherItem weatherItem5 = new WeatherItem("Basement",
                    "10 " + " \u2103",
                    "Check it!",
                    "77 %",
                    "997 hPa",
                    false
            );
            itemsData.add(weatherItem5);

            /*for (int i = 0; i < 50; i++) {
                itemsData.add(new WeatherItem("Fragment Weather as mvp - " + " Item : " + i));
            }*/

            return Observable.just(itemsData)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {

            //right time
            return RealmUtils.FindAllAsync(WeatherItem.class);
        }

    }
}
