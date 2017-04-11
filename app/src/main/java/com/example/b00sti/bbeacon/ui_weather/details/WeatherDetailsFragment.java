package com.example.b00sti.bbeacon.ui_weather.details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.ui_weather.interactors.GetWeatherInteractor;
import com.example.b00sti.bbeacon.ui_weather.main.LineCardOne;
import com.example.b00sti.bbeacon.ui_weather.main.WeatherItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-05
 */

@EFragment(R.layout.weather_details)
public class WeatherDetailsFragment extends Fragment {

    @ViewById(R.id.card_view1) CardView card_view1;
    @ViewById(R.id.card_view2) CardView card_view2;
    @ViewById(R.id.card_view3) CardView card_view3;
    @ViewById(R.id.tempValueTV) TextView tempValueTV;
    @ViewById(R.id.pressureTV) TextView pressureTV;
    @ViewById(R.id.humidityTV) TextView humidityTV;
    @ViewById(R.id.messageTV) TextView messageTV;
    @ViewById(R.id.whenTV) TextView whenTV;
    @ViewById(R.id.titleTV) TextView titleTV;


    @ColorRes(R.color.colorPrimaryDark)
    int colorPrimary;

    @ColorRes(R.color.colorAccent)
    int colorAccent;

    @ColorRes(R.color.colorPrimaryDark)
    int color;

    private CompositeDisposable compositeDisposable;

    public static WeatherDetailsFragment newInstance() {
        return new WeatherDetailsFragment_();
    }

    @AfterViews
    void initUI() {
        compositeDisposable = new CompositeDisposable();

        (new LineCardOne(card_view1, getActivity(), color, R.id.chart1)).init();
        (new LineCardOne(card_view2, getActivity(), color, R.id.chart2)).init();
        (new LineCardOne(card_view3, getActivity(), color, R.id.chart3)).init();

        Bundle bundle = getArguments();

        if (bundle.containsKey("id")) {
            String id = bundle.getString("id");
            compositeDisposable.add(new GetWeatherInteractor().execute(id).subscribe(new Consumer<WeatherItem>() {
                @Override
                public void accept(WeatherItem weatherItem) throws Exception {
                    updateUI(weatherItem);
                }
            }));
        }
    }

    private void updateUI(WeatherItem weatherItem) {
        tempValueTV.setText(weatherItem.getTemp());
        pressureTV.setText(weatherItem.getPressure());
        humidityTV.setText(weatherItem.getHumidity());
        messageTV.setText(weatherItem.getMessage());
        whenTV.setText("Notify if pressure is less than 1000 hPa");
        titleTV.setText(weatherItem.getTitle());
    }

    @Click(R.id.cancelIV)
    void cancel() {
        getActivity().finish();
    }

    @Click(R.id.doneIV)
    void save() {
        getActivity().finish();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        compositeDisposable.dispose();
    }
}