package com.example.b00sti.bbeacon.ui_weather;

import android.graphics.Color;

import com.example.b00sti.bbeacon.utils.RealmUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

public class GetWeatherInteractor {

/*    <item>#3F52B5</item>
    <item>#653BB5</item>
    <item>#3087A8</item>*/

    public Observable<List<WeatherItem>> execute() {
        boolean fakeData = true;
        int color1 = Color.parseColor("#3F52B5");
        int color2 = Color.parseColor("#653BB5");
        int color3 = Color.parseColor("#3087A8");

        if (fakeData) {
            //fake data
            List<WeatherItem> itemsData = new ArrayList<>();

            WeatherItem weatherItem1 = new WeatherItem("Outdoor",
                    "17 " + " \u2103",
                    "Open window !",
                    "50 %",
                    "1020 hPa",
                    false,
                    color1
            );
            itemsData.add(weatherItem1);

            WeatherItem weatherItem2 = new WeatherItem("Kids room",
                    "22 " + " \u2103",
                    "Check air conditioner !",
                    "65 %",
                    "1017 hPa",
                    true,
                    color2
            );
            itemsData.add(weatherItem2);

            WeatherItem weatherItem3 = new WeatherItem("Flowers",
                    "15 " + " \u2103",
                    "My flowers are thirsty ?",
                    "24 %",
                    "1000 hPa",
                    false,
                    color3
            );
            itemsData.add(weatherItem3);

            WeatherItem weatherItem4 = new WeatherItem("Bathroom",
                    "17 " + " \u2103",
                    "Is too cold ?",
                    "85 %",
                    "1023 hPa",
                    true,
                    color1
            );
            itemsData.add(weatherItem4);

            WeatherItem weatherItem5 = new WeatherItem("Basement",
                    "10 " + " \u2103",
                    "Check it!",
                    "77 %",
                    "997 hPa",
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
