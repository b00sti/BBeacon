package com.example.b00sti.bbeacon.ui_weather.pollution;

import com.example.b00sti.bbeacon.ui_weather.pollution.model.Pollution;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-04
 */

public interface PollutionService {

    //https://api.waqi.info/feed/geo:10.3;20.7/?token=demo

    @GET("feed/geo:{lat};{lon}/?")
    Observable<Pollution> getPollutionFromAPI(@Path("lat") Double lat, @Path("lon") Double lon, @Query("token") String myToken);
}
