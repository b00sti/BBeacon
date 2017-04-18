package com.example.b00sti.bbeacon.ui_weather.top;

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
import com.example.b00sti.bbeacon.ui_weather.top.interactors.GetWeatherFromOWMInteractor;
import com.example.b00sti.bbeacon.ui_weather.top.interactors.SetWeatherFromOWMInteractor;
import com.example.b00sti.bbeacon.ui_weather.top.model.Main;
import com.example.b00sti.bbeacon.ui_weather.top.model.WeatherFromOWM;
import com.example.b00sti.bbeacon.utils.CLog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import retrofit2.HttpException;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-13
 */

@EBean
public class WeatherTopPresenter extends BasePresenter<WeatherTopContract.View> implements WeatherTopContract.Presenter, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "WeatherTopPresenter";

    //defualt coordinates
    private static final Double lat = 50.057667;
    private static final Double lon = 19.937222;

    @RootContext
    Activity ctx;
    private GoogleApiClient mGoogleApiClient = null;
    private com.example.b00sti.bbeacon.ui_weather.top.WeatherFromOWMRealm weatherFromOWMRealm;

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

    private void getWeatherDataFromWeb() {
        double latToRequest = lat;
        double lonToRequest = lon;

        if (weatherFromOWMRealm != null) {
            latToRequest = weatherFromOWMRealm.getLat();
            lonToRequest = weatherFromOWMRealm.getLon();
        }

        addDisposable(GetWeatherFromOWMInteractor.getFromApi(latToRequest, lonToRequest)
                .onErrorReturn(new Function<Throwable, WeatherFromOWM>() {
                    @Override
                    public WeatherFromOWM apply(Throwable throwable) throws Exception {

                        if (throwable instanceof HttpException) {
                            HttpException response = (HttpException) throwable;
                            int code = response.code();
                            Log.d(TAG, "Retrofit Error  - code: " + code);
                        } else {
                            Log.d(TAG, "Other Error - code: " + throwable.getMessage());
                        }

                        return new WeatherFromOWM();
                    }
                })
                .subscribe(new Consumer<WeatherFromOWM>() {
                    @Override
                    public void accept(WeatherFromOWM weatherFromOWM) throws Exception {
                        onRetrievedWeatherFromApi(weatherFromOWM);
                    }
                }));
    }

    @UiThread
    void onRetrievedWeatherFromApi(WeatherFromOWM weatherFromOWM) {
        //if error occurs weatherFromOWM.getMain() should be null
        if (weatherFromOWM.getMain() != null) {
            weatherFromOWMRealm = prepareWeatherToRealm(weatherFromOWM);
            SetWeatherFromOWMInteractor.saveToRealm(weatherFromOWMRealm);
            if (view != null) {
                view.refreshViews(weatherFromOWMRealm);
            }
        } else {
            Log.d(TAG, "onRetrievedWeatherFromApi: " + "weather from OWM is null");
        }
    }

    @Override
    public void initViews() {
        weatherFromOWMRealm = GetWeatherFromOWMInteractor.getFromRealm();

        if (weatherFromOWMRealm != null) {
            view.refreshViews(weatherFromOWMRealm);
        }

        getWeatherDataFromWeb();
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
            CLog.d(TAG, "onConnected: new location lat", weatherFromOWMRealm.getLat(), "lon", weatherFromOWMRealm.getLon());
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
        result.setLat(this.weatherFromOWMRealm.getLat());
        result.setLon(this.weatherFromOWMRealm.getLon());

        return result;
    }
}
