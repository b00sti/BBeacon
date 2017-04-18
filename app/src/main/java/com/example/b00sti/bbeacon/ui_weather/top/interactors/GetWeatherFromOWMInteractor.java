package com.example.b00sti.bbeacon.ui_weather.top.interactors;

import com.example.b00sti.bbeacon.ui_weather.top.WeatherFromOWMRealm;
import com.example.b00sti.bbeacon.ui_weather.top.WeatherService;
import com.example.b00sti.bbeacon.ui_weather.top.model.WeatherFromOWM;
import com.example.b00sti.bbeacon.utils.RealmUtils;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-05
 */

public class GetWeatherFromOWMInteractor {

    private static final String ENDPOINT = "http://api.openweathermap.org/data/2.5/";
    private static final String APPID = "871ef8a06bbdc5fdc0a0a1ce6b3b5e23";
    private static final String UNITS = "metric";

    private GetWeatherFromOWMInteractor() {
    }

    public static WeatherFromOWMRealm getFromRealm() {
        return RealmUtils.Find(com.example.b00sti.bbeacon.ui_weather.top.WeatherFromOWMRealm.class, "id", 0); //NON-NLS
    }

    public static Observable<WeatherFromOWM> getFromApi(double lat, double lon) {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ENDPOINT)
                .build();

        WeatherService weatherService = retrofit.create(WeatherService.class);
        return weatherService.getWeatherFromOWM(lat, lon, APPID, UNITS);
    }
}
