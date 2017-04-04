package com.example.b00sti.bbeacon.ui_weather.top;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.b00sti.bbeacon.R;

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
public class WeatherTopFragment extends Fragment {
    public static final String TAG = "WeatherTopFragment";
    public static final String ENDPOINT = "http://api.openweathermap.org/data/2.5/";
    public static final String APPID = "871ef8a06bbdc5fdc0a0a1ce6b3b5e23";
    public Double lat = 50.057667;
    public Double lon = 19.937222;

    @ViewById(R.id.circleToolbarIconIV) CircleImageView circleImageView;

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
                        Log.i(TAG, "accept: " + weatherFromOWM.name);
                    }
                });

    }


    private Observable<WeatherFromOWM> getWeatherData() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ENDPOINT)
                .build();

        WeatherService weatherService = retrofit.create(WeatherService.class);
        return weatherService.getWeatherFromOWM(lat, lon, APPID);
    }
}
