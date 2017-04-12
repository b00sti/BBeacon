package com.example.b00sti.bbeacon.ui_weather.top;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b00sti.bbeacon.MainActivity;
import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.OnAnimationToolbar;
import com.example.b00sti.bbeacon.ui_weather.top.model.WeatherFromOWM;
import com.example.b00sti.bbeacon.utils.CLog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import de.hdodenhof.circleimageview.CircleImageView;
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
 * Created by b00sti on 20.03.2017.
 */
@EFragment(R.layout.weather_top_fragment)
public class WeatherTopFragment extends Fragment implements OnAnimationToolbar,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final String TAG = "WeatherTopFragment";
    public static final String ENDPOINT = "http://api.openweathermap.org/data/2.5/";
    public static final String APPID = "871ef8a06bbdc5fdc0a0a1ce6b3b5e23";
    public static final String UNITS = "metric";
    public Double lat = 50.057667;
    public Double lon = 19.937222;

    @ViewById(R.id.circleToolbarIconIV) CircleImageView circleImageView;
    @ViewById(R.id.tempValueTV) TextView tempValueTV;
    @ViewById(R.id.windTV) TextView windTV;
    @ViewById(R.id.humidityTV) TextView humidityTV;
    @ViewById(R.id.pressureTV) TextView pressureTV;

    String expandedTitle = "";
    String collapsedTitle = "";

    GoogleApiClient mGoogleApiClient = null;
    WeatherFromOWMRealm weatherFromOWMRealm;

    public static WeatherTopFragment newInstance() {
        return new WeatherTopFragment_();
    }

    private void getLocation() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    public void onStart() {
        getLocation();
        mGoogleApiClient.connect();
        super.onStart();
    }

    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @AfterViews
    void initUI() {
        weatherFromOWMRealm = new GetWeatherFromOWMInteractor().execute();

        if (weatherFromOWMRealm == null) {
            Toast.makeText(getContext(), R.string.enable_internet_to_refresh_weather, Toast.LENGTH_LONG).show();
            return;
        }

        lat = weatherFromOWMRealm.lat;
        lon = weatherFromOWMRealm.lon;

        tempValueTV.setText(getFormattedTemp(weatherFromOWMRealm.getTemp()));
        pressureTV.setText(getFormattedPressure(weatherFromOWMRealm.getPressure()));
        humidityTV.setText(getFormattedHumidity(weatherFromOWMRealm.getHumidity()));
        windTV.setText(getFormattedWind(weatherFromOWMRealm.getWind()));
        refreshToolbar(weatherFromOWMRealm.getName());
        expandedTitle = weatherFromOWMRealm.getName();
        collapsedTitle = weatherFromOWMRealm.getName() + "    " + getFormattedTemp(weatherFromOWMRealm.getTemp()) + " \u2103";
        if (weatherFromOWMRealm.getIcon() != null) {
            String iconUrl = String.format(getString(R.string.openweathermap_icon_url), weatherFromOWMRealm.getIcon());
            Picasso.with(getContext()).load(iconUrl).into(circleImageView);
        } else {
            Log.i(TAG, "accept: image not available");
        }
    }

    @AfterViews
    void getWeatherFromWeb() {
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
                            Log.d(TAG, "accept: " + weatherFromOWM.name);
                            refreshUI(weatherFromOWM);
                            saveWeatherToRealm(weatherFromOWM);
                        } else {
                            Log.d(TAG, "accept: " + "weather from OWM is null");
                        }
                    }
                });

    }

    private void refreshUI(@NonNull WeatherFromOWM weatherFromOWM) {

        if (tempValueTV != null) {
            tempValueTV.setText(getFormattedTemp(weatherFromOWM.main.temp));
        }

        if (pressureTV != null) {
            pressureTV.setText(getFormattedPressure(weatherFromOWM.main.pressure));
        }

        if (humidityTV != null) {
            humidityTV.setText(getFormattedHumidity(weatherFromOWM.main.humidity));
        }

        if (windTV != null) {
            windTV.setText(getFormattedWind(weatherFromOWM.wind.speed));
        }

        refreshToolbar(weatherFromOWM.name);

        expandedTitle = weatherFromOWM.name;
        collapsedTitle = weatherFromOWM.name + "    " + getFormattedTemp(weatherFromOWM.main.temp) + " \u2103";

        if (weatherFromOWM.weather != null) {
            if (!weatherFromOWM.weather.isEmpty()) {
                String iconName = weatherFromOWM.weather.get(0).icon;
                String iconUrl = String.format(getString(R.string.openweathermap_icon_url), weatherFromOWMRealm.getIcon());
                Log.i(TAG, "accept: icon url:" + iconUrl);
                Picasso.with(getContext()).load(iconUrl).into(circleImageView);
            } else {
                Log.d(TAG, "accept: image not available");
            }
        }
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

    private void saveWeatherAfterLocationUpdate(double lat, double lon) {
        if (this.weatherFromOWMRealm != null) {
            this.weatherFromOWMRealm.setLat(lat);
            this.weatherFromOWMRealm.setLon(lon);
            new SetWeatherFromOWMInteractor().execute(weatherFromOWMRealm);
        }
    }

    private String getFormattedTemp(double temp) {
        return String.valueOf((int) temp);
    }

    private String getFormattedPressure(double pressure) {
        return String.format(getString(R.string.pressure), String.valueOf((int) pressure));
    }

    private String getFormattedHumidity(int humidity) {
        return String.format(getString(R.string.humidity), String.valueOf((int) humidity));
    }

    private String getFormattedWind(double wind) {
        return String.format(getString(R.string.wind_speed), String.valueOf((int) wind));
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

    @Override
    public String setCollapsedTitleLayout() {
        refreshToolbar(collapsedTitle);
        return collapsedTitle;
    }

    @Override
    public String setExpandedTitleLayout() {
        refreshToolbar(expandedTitle);
        return expandedTitle;
    }

    public void refreshToolbar(String title) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setTitleToToolbar(title);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
            getWeatherData();
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
}
