package com.example.b00sti.bbeacon.ui_weather.pollution;

import com.example.b00sti.bbeacon.ui_weather.pollution.model.Pollution;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by b00sti on 29.05.2017
 */

public class GetPollutionInteractor {

    private static final String ENDPOINT = "https://api.waqi.info/";
    private static final String TOKEN = "71f6ff9c5bf028aed7b84222c1664b299b5217b0";

    private GetPollutionInteractor() {
    }

/*    public static WeatherFromOWMRealm getFromRealm() {
        return RealmUtils.Find(com.example.b00sti.bbeacon.ui_weather.top.WeatherFromOWMRealm.class, "id", 0); //NON-NLS
    }*/

    public static Observable<Pollution> getFromApi(double lat, double lon) {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ENDPOINT)
                .build();

        PollutionService pollutionService = retrofit.create(PollutionService.class);
        return pollutionService.getPollutionFromAPI(lat, lon, TOKEN);
    }

}