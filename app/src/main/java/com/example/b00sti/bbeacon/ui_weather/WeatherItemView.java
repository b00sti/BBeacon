package com.example.b00sti.bbeacon.ui_weather;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseItemView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.IntArrayRes;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

@EViewGroup(R.layout.weather_item_view)
public class WeatherItemView extends BaseItemView<WeatherItem> {

    @ViewById(R.id.layout_item_demo_title) TextView textView;
    @ViewById(R.id.sidebar) View sidebar;
    @ViewById(R.id.moreIV) ImageView moreIV;
    @ViewById(R.id.topLayoutLL) ViewGroup topLL;
    @ViewById(R.id.tempValueTV) TextView timeTV;
    @ViewById(R.id.daysTV) TextView daysTV;
    @ViewById(R.id.humidityTV) TextView textView2;
    @ViewById(R.id.pressureTV) TextView textView1;
    @ViewById(R.id.card_view) CardView card_view;

    @IntArrayRes(R.array.beaconColors)
    int colors[];

    @DrawableRes(R.drawable.ic_description_grey_900_24dp)
    Drawable desc;

    @DrawableRes(R.drawable.ic_priority_high_red_900_24dp)
    Drawable alarm;

    Context context;

    public WeatherItemView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void bind(WeatherItem weatherItem) {
        textView.setText(weatherItem.getText());
        int color = weatherItem.getColor();
        topLL.setBackgroundColor(color);
        sidebar.setBackgroundColor(color);
        timeTV.setText(weatherItem.getTime());
        daysTV.setText(weatherItem.getDays());
        textView1.setText(weatherItem.getPressure());
        textView2.setText(weatherItem.getHumidity());
        if (weatherItem.isAlarm) {
            daysTV.setCompoundDrawablesWithIntrinsicBounds(desc, null, alarm, null);
        } else {
            daysTV.setCompoundDrawablesWithIntrinsicBounds(desc, null, null, null);
        }

        (new LineCardOne(card_view, context, color)).init();

    }
}
