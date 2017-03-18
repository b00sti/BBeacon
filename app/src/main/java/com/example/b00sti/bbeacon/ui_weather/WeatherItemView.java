package com.example.b00sti.bbeacon.ui_weather;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseItemView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.IntArrayRes;

import java.util.Random;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

@EViewGroup(R.layout.weather_item_view)
public class WeatherItemView extends BaseItemView<WeatherItem> {

    @ViewById(R.id.layout_item_demo_title) TextView textView;
    @ViewById(R.id.sidebar) View sidebar;
    @ViewById(R.id.moreIV) ImageView moreIV;
    @ViewById(R.id.topLayoutLL) ViewGroup topLL;

    @IntArrayRes(R.array.beaconColors)
    int colors[];

    public WeatherItemView(Context context) {
        super(context);
    }

    @Override
    public void bind(WeatherItem weatherItem) {
        textView.setText(weatherItem.getText());
        int i = new Random().nextInt(colors.length);
        topLL.setBackgroundColor(colors[i]);
        sidebar.setBackgroundColor(colors[i]);
    }
}
