package com.example.b00sti.bbeacon.ui_weather.interactors;

import android.graphics.Color;

import com.example.b00sti.bbeacon.ui_weather.main.WeatherItem;
import com.example.b00sti.bbeacon.utils.RealmUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

public class GetWeatherInteractor {

    public Observable<WeatherItem> execute(String id) {
        return Observable.just(RealmUtils.Find(WeatherItem.class, "beaconId", id))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<WeatherItem>> execute() {
        boolean fakeData = false;
        int color1 = Color.parseColor("#3F52B5");
        int color2 = Color.parseColor("#653BB5");
        int color3 = Color.parseColor("#3087A8");

        if (fakeData) {
            //fake data
            List<WeatherItem> itemsData = new ArrayList<>();

            WeatherItem weatherItem1 = new WeatherItem(
                    "Outdoor",
                    "Outdoor",
                    "17 " + " \u2103",
                    "Open window !",
                    "50 %",
                    "1020 hPa",
                    "",
                    "",
                    false,
                    color1
            );
            itemsData.add(weatherItem1);

            WeatherItem weatherItem2 = new WeatherItem(
                    "Outdoor",
                    "Kids room",
                    "22 " + " \u2103",
                    "Check air conditioner !",
                    "65 %",
                    "1017 hPa",
                    "",
                    "",
                    true,
                    color2
            );
            itemsData.add(weatherItem2);

            WeatherItem weatherItem3 = new WeatherItem(
                    "Outdoor",
                    "Flowers",
                    "15 " + " \u2103",
                    "My flowers are thirsty ?",
                    "24 %",
                    "1000 hPa",
                    "",
                    "",
                    false,
                    color3
            );
            itemsData.add(weatherItem3);

            WeatherItem weatherItem4 = new WeatherItem(
                    "Outdoor",
                    "Bathroom",
                    "17 " + " \u2103",
                    "Is too cold ?",
                    "85 %",
                    "1023 hPa",
                    "",
                    "",
                    true,
                    color1
            );
            itemsData.add(weatherItem4);

            WeatherItem weatherItem5 = new WeatherItem(
                    "Outdoor",
                    "Basement",
                    "10 " + " \u2103",
                    "Check it!",
                    "77 %",
                    "997 hPa",
                    "",
                    "",
                    false,
                    color2
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
