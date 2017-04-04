package com.example.b00sti.bbeacon.ui_weather.top;

import android.support.v4.app.Fragment;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.ui_weather.WeatherTopFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by b00sti on 20.03.2017.
 */
@EFragment(R.layout.weather_top_fragment)
public class WeatherTopFragment extends Fragment {
    public static final String TAG = "WeatherTopFragment";

    @ViewById(R.id.circleToolbarIconIV) CircleImageView circleImageView;

    public static WeatherTopFragment newInstance() {
        return new WeatherTopFragment_();
    }

    @AfterViews
    void test() {

    }

}
