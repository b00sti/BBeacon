package com.example.b00sti.bbeacon.ui_weather;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ViewGroup;

import com.example.b00sti.bbeacon.R;
import com.example.bskeleton.basics.AnimUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by b00sti on 20.03.2017.
 */
@EFragment(R.layout.weather_top_fragment)
public class WeatherTopFragment extends Fragment implements OnAnimationToolbar {
    public static final int ALPHA_ANIMATIONS_DURATION = 200;
    public static final String TAG = "WeatherTopFragment";

    @ViewById(R.id.bottomTopHeaderLL) ViewGroup titleContainer;
    @ViewById(R.id.bottomTopHeaderFL) ViewGroup conatiner;


    public static WeatherTopFragment newInstance() {
        return new WeatherTopFragment_();
    }

    @AfterViews
    void test() {
        Log.d(TAG, "test: " + titleContainer);
        Log.d(TAG, "test: " + conatiner);
    }

    @Override
    public void animeTitleLayout(int visibility) {
        AnimUtils.startAlphaAnimation(titleContainer, ALPHA_ANIMATIONS_DURATION, visibility);
    }

}
