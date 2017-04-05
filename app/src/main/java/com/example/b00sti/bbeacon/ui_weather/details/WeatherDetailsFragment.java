package com.example.b00sti.bbeacon.ui_weather.details;

import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.ui_weather.main.LineCardOne;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-05
 */

@EFragment(R.layout.weather_details)
public class WeatherDetailsFragment extends Fragment {

    @ViewById(R.id.card_view1) CardView card_view1;
    @ViewById(R.id.card_view2) CardView card_view2;
    @ViewById(R.id.card_view3) CardView card_view3;

    @ColorRes(R.color.colorPrimaryDark)
    int colorPrimary;

    @ColorRes(R.color.colorAccent)
    int colorAccent;

    @ColorRes(R.color.colorPrimaryDark)
    int color;

    public static WeatherDetailsFragment newInstance() {
        return new WeatherDetailsFragment_();
    }

    @AfterViews
    void initUI() {
        (new LineCardOne(card_view1, getActivity(), color, R.id.chart1)).init();
        (new LineCardOne(card_view2, getActivity(), color, R.id.chart2)).init();
        (new LineCardOne(card_view3, getActivity(), color, R.id.chart3)).init();
    }


    @Click(R.id.cancelIV)
    void cancel() {
        getActivity().finish();
    }

    @Click(R.id.doneIV)
    void save() {
        getActivity().finish();
    }

}