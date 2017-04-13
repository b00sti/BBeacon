package com.example.b00sti.bbeacon.ui_weather.details;

import android.app.Activity;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BasePresenter;
import com.example.b00sti.bbeacon.ui_weather.interactors.GetWeatherInteractor;
import com.example.b00sti.bbeacon.ui_weather.main.LineCardOne;
import com.example.b00sti.bbeacon.ui_weather.main.WeatherItem;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.res.ColorRes;

import io.reactivex.functions.Consumer;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-13
 */

@EBean
public class WeatherDetailsPresenter extends BasePresenter<WeatherDetailsContract.View> implements WeatherDetailsContract.Presenter {

    @RootContext
    Activity ctx;

    @ColorRes(R.color.colorPrimaryDark)
    int color;

    @Override
    public void onSubscribe() {

    }

    @Override
    public void onUnsubscribe() {

    }

    @Override
    public void initUI(String id) {

        (new LineCardOne(view.getChartCardView1(), ctx, color, R.id.chart1)).init();
        (new LineCardOne(view.getChartCardView2(), ctx, color, R.id.chart2)).init();
        (new LineCardOne(view.getChartCardView3(), ctx, color, R.id.chart3)).init();

        addDisposable(new GetWeatherInteractor().execute(id).subscribe(new Consumer<WeatherItem>() {
            @Override
            public void accept(WeatherItem weatherItem) throws Exception {
                view.updateUI(weatherItem);
            }
        }));
    }

    @Override
    public void onAccept() {
        ctx.finish();
    }

    @Override
    public void onCancel() {
        ctx.finish();
    }
}
