package com.example.b00sti.bbeacon.ui_weather.pollution;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.app.ActivityCompat;
import android.util.Log;

import com.example.b00sti.bbeacon.base.BasePresenter;
import com.example.b00sti.bbeacon.ui_weather.pollution.model.Data;
import com.example.b00sti.bbeacon.ui_weather.pollution.model.Pollution;
import com.example.b00sti.bbeacon.ui_weather.top.WeatherFromOWMRealm;
import com.example.b00sti.bbeacon.ui_weather.top.interactors.GetWeatherFromOWMInteractor;
import com.example.b00sti.bbeacon.ui_weather.top.interactors.SetWeatherFromOWMInteractor;
import com.example.b00sti.bbeacon.ui_weather.top.model.Main;
import com.example.b00sti.bbeacon.ui_weather.top.model.WeatherFromOWM;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;

import retrofit2.HttpException;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-13
 */
//todo clean up
@EBean
public class PollutionPresenter extends BasePresenter<PollutionContract.View> implements PollutionContract.Presenter, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "WeatherTopPresenter";

    private static final Double defaultLat = 50.057667;
    private static final Double defaultLon = 19.937222;

    @RootContext
    Activity ctx;
    private GoogleApiClient mGoogleApiClient = null;
    private WeatherFromOWMRealm weatherFromOWMRealm;
    private PollutionRealm pollutionRealm;

    @Override
    public void onSubscribe() {

    }

    @Override
    public void onUnsubscribe() {

    }

    @Override
    public void initLocation() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(ctx)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void stopLocation() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    private void getPollutionFromWeb() {
        addDisposable(GetPollutionInteractor.getFromApi(getLat(), getLon())
                .onErrorReturn(throwable -> {

                    if (throwable instanceof HttpException) {
                        HttpException response = (HttpException) throwable;
                        int code = response.code();
                        Log.d(TAG, "Retrofit Error  - code: " + code);
                    } else {
                        Log.d(TAG, "Other Error - code: " + throwable.getMessage());
                    }

                    return new Pollution();
                })
                .subscribe(this::onRetrievedPollutionFromApi));
    }

    private void getWeatherDataFromWeb() {
        addDisposable(GetWeatherFromOWMInteractor.getFromApi(getLat(), getLon())
                .onErrorReturn(throwable -> {

                    if (throwable instanceof HttpException) {
                        HttpException response = (HttpException) throwable;
                        int code = response.code();
                        Log.d(TAG, "Retrofit Error  - code: " + code);
                    } else {
                        Log.d(TAG, "Other Error - code: " + throwable.getMessage());
                    }

                    return new WeatherFromOWM();
                })
                .subscribe(this::onRetrievedWeatherFromApi));
    }

    private double getLat() {
        if (weatherFromOWMRealm != null) {
            return weatherFromOWMRealm.getLat();
        } else {
            return defaultLat;
        }
    }

    private double getLon() {
        if (weatherFromOWMRealm != null) {
            return weatherFromOWMRealm.getLon();
        } else {
            return defaultLon;
        }
    }

    @UiThread
    void onRetrievedPollutionFromApi(Pollution pollution) {
        //if error occurs weatherFromOWM.getMain() should be null
        Log.d(TAG, "onRetrievedPollutionFromApi: " + pollution.toString());
        if (pollution.getStatus() != null) {
            pollutionRealm = preparePollutionToRealm(pollution);
            SetPollutionInteractor.saveToRealm(pollutionRealm);
            if (view != null) {
                view.refreshPollutionViews(pollutionRealm);
            }
        } else {
            Log.d(TAG, "onRetrievedWeatherFromApi: " + "pollution from API is null");
        }
    }

    private PollutionRealm preparePollutionToRealm(Pollution pollution) {

        PollutionRealm pollutionRealm = new PollutionRealm();
        Data data = pollution.getData();
        pollutionRealm.setAqi(data.getAqi());
        pollutionRealm.setIdx(data.getIdx());
        pollutionRealm.setName(data.getCity().getName());
        pollutionRealm.setTime(data.getTime().getS());

        if (data.getIaqi().getPm25() != null) {
            pollutionRealm.setPm25(data.getIaqi().getPm25().getV());
        }
        if (data.getIaqi().getPm10() != null) {
            pollutionRealm.setPm10(data.getIaqi().getPm10().getV());
        }
        if (data.getIaqi().getCo() != null) {
            pollutionRealm.setCo(data.getIaqi().getCo().getV());
        }
        if (data.getIaqi().getP() != null) {
            pollutionRealm.setP(data.getIaqi().getP().getV());
        }
        if (data.getIaqi().getH() != null) {
            pollutionRealm.setH(data.getIaqi().getH().getV());
        }
        if (data.getIaqi().getNo2() != null) {
            pollutionRealm.setNo2(data.getIaqi().getNo2().getV());
        }

        return pollutionRealm;
    }

    @UiThread
    void onRetrievedWeatherFromApi(WeatherFromOWM weatherFromOWM) {
        //if error occurs weatherFromOWM.getMain() should be null
        if (weatherFromOWM.getMain() != null) {
            weatherFromOWMRealm = prepareWeatherToRealm(weatherFromOWM);
            SetWeatherFromOWMInteractor.saveToRealm(weatherFromOWMRealm);
            if (view != null) {
                view.refreshWeatherViews(weatherFromOWMRealm);
            }
        } else {
            Log.d(TAG, "onRetrievedWeatherFromApi: " + "weather from OWM is null");
        }
    }

    @Override
    public void initViews() {
        //get last from database
        weatherFromOWMRealm = GetWeatherFromOWMInteractor.getFromRealm();
        pollutionRealm = GetPollutionInteractor.getFromRealm();

        //refresh views
        if (weatherFromOWMRealm != null) {
            view.refreshWeatherViews(weatherFromOWMRealm);
        }

        if (pollutionRealm != null) {
            view.refreshPollutionViews(pollutionRealm);
        }

        //get actual data
        getWeatherDataFromWeb();
        getPollutionFromWeb();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            updateWeatherAfterLocationChanges(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        } else {
            Log.d(TAG, "onConnected: Location is null");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void updateWeatherAfterLocationChanges(double lat, double lon) {
        if (this.weatherFromOWMRealm != null) {
            this.weatherFromOWMRealm.setLat(lat);
            this.weatherFromOWMRealm.setLon(lon);
            SetWeatherFromOWMInteractor.saveToRealm(weatherFromOWMRealm);
        }
        getWeatherDataFromWeb();
        getPollutionFromWeb();
    }

    private WeatherFromOWMRealm prepareWeatherToRealm(WeatherFromOWM weatherFromOWM) {
        WeatherFromOWMRealm result = new WeatherFromOWMRealm();

        //always id = 0 as primary key to ensure one weather object exist in database
        result.setId(0);

        Main main = weatherFromOWM.getMain();
        if (main != null) {
            result.setTemp(main.getTemp());
            result.setPressure(main.getPressure());
            result.setHumidity(main.getHumidity());
        }

        if (weatherFromOWM.getWind() != null) {
            result.setWind(weatherFromOWM.getWind().getSpeed());
        }

        //get first available icon
        if (weatherFromOWM.getWeather() != null && !weatherFromOWM.getWeather().isEmpty()) {
            result.setIcon(weatherFromOWM.getWeather().get(0).getIcon());
        }

        result.setName(weatherFromOWM.getName());
        result.setLat(getLat());
        result.setLon(getLon());

        return result;
    }
}
