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
import android.widget.Toast;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BasePresenter;
import com.example.b00sti.bbeacon.ui_weather.top.model.WeatherFromOWM;
import com.example.b00sti.bbeacon.utils.CLog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-13
 */

@EBean
public class WeatherTopPresenter extends BasePresenter<WeatherTopContract.View> implements WeatherTopContract.Presenter, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "WeatherTopPresenter";

    private static final String ENDPOINT = "http://api.openweathermap.org/data/2.5/";
    private static final String APPID = "871ef8a06bbdc5fdc0a0a1ce6b3b5e23";
    private static final String UNITS = "metric";

    //defualt coordinates
    public Double lat = 50.057667;
    public Double lon = 19.937222;
    @RootContext
    Activity ctx;
    private GoogleApiClient mGoogleApiClient = null;
    private WeatherFromOWMRealm weatherFromOWMRealm;

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
    public void stopLocationListener() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void getWeatherDataFromWeb() {
        getWeatherData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
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
                        if (weatherFromOWM.main != null) {
                            view.refreshViews(weatherFromOWM);
                            saveWeatherToRealm(weatherFromOWM);
                        } else {
                            Log.d(TAG, "accept: " + "weather from OWM is null");
                        }
                    }
                });
    }

    @Override
    public void initViews() {
        weatherFromOWMRealm = new GetWeatherFromOWMInteractor().execute();

        if (weatherFromOWMRealm == null) {
            Toast.makeText(ctx, R.string.enable_internet_to_refresh_weather, Toast.LENGTH_LONG).show();
            return;
        }

        lat = weatherFromOWMRealm.lat;
        lon = weatherFromOWMRealm.lon;
        view.refreshViews(weatherFromOWMRealm);
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
            lat = mLastLocation.getLatitude();
            lon = mLastLocation.getLongitude();
            saveWeatherAfterLocationUpdate(lat, lon);
            CLog.d(TAG, "onConnected: new location lat", lat, "lon", lon);
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

    private void saveWeatherAfterLocationUpdate(double lat, double lon) {
        if (this.weatherFromOWMRealm != null) {
            this.weatherFromOWMRealm.setLat(lat);
            this.weatherFromOWMRealm.setLon(lon);
            new SetWeatherFromOWMInteractor().execute(weatherFromOWMRealm);
        }
    }

    private Observable<WeatherFromOWM> getWeatherData() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ENDPOINT)
                .build();

        WeatherService weatherService = retrofit.create(WeatherService.class);
        return weatherService.getWeatherFromOWM(lat, lon, APPID, UNITS);
    }

    private void saveWeatherToRealm(WeatherFromOWM weatherFromOWM) {
        WeatherFromOWMRealm weatherFromOWMRealm = new WeatherFromOWMRealm(0
                , weatherFromOWM.main.temp
                , weatherFromOWM.main.pressure
                , weatherFromOWM.main.humidity
                , weatherFromOWM.wind.speed
                , weatherFromOWM.weather.get(0).icon
                , weatherFromOWM.name
                , lat
                , lon);
        this.weatherFromOWMRealm = weatherFromOWMRealm;
        new SetWeatherFromOWMInteractor().execute(weatherFromOWMRealm);
    }
}
