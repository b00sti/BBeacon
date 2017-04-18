package com.example.b00sti.bbeacon.ui_weather.top;

import com.example.b00sti.bbeacon.ui_weather.top.model.WeatherFromOWM;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-04
 */

public interface WeatherService {

    @GET("weather?")
    Observable<WeatherFromOWM> getWeatherFromOWM(@Query("lat") Double lat, @Query("lon") Double lon, @Query("APPID") String id, @Query("units") String units);
}
