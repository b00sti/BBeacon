package com.example.b00sti.bbeacon.ui_weather.top;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;

import com.example.b00sti.bbeacon.MainActivity;
import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.ui_weather.main.OnAnimationToolbar;

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
public class WeatherTopFragment extends Fragment implements OnAnimationToolbar {
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

    public static WeatherTopFragment newInstance() {
        return new WeatherTopFragment_();
    }

    @AfterViews
    void test() {
        getWeatherData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Function<Throwable, WeatherFromOWM>() {
                    @Override
                    public WeatherFromOWM apply(Throwable throwable) throws Exception {

                        if (throwable instanceof HttpException) {
                            HttpException response = (HttpException) throwable;
                            int code = response.code();
                            Log.d("RetrofitTest", "Error code: " + code);
                        }

                        return new WeatherFromOWM();
                    }
                })
                .subscribe(new Consumer<WeatherFromOWM>() {
                    @Override
                    public void accept(WeatherFromOWM weatherFromOWM) throws Exception {
                        if (weatherFromOWM.main != null) {
                            tempValueTV.setText(getFormattedTemp(weatherFromOWM.main.temp));
                            pressureTV.setText(getFormattedPressure(weatherFromOWM.main.pressure));
                            humidityTV.setText(getFormattedHumidity(weatherFromOWM.main.humidity));
                            windTV.setText(getFormattedWind(weatherFromOWM.wind.speed));
                            refreshToolbar(weatherFromOWM.name);
                            expandedTitle = weatherFromOWM.name;
                            collapsedTitle = weatherFromOWM.name + "    " + getFormattedTemp(weatherFromOWM.main.temp) + " \u2103";
                        }
                        Log.i(TAG, "accept: " + weatherFromOWM.name);
                    }
                });

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
            ((MainActivity) getActivity()).configureToolbar(title);
        }
    }
}
