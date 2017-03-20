package com.example.b00sti.bbeacon.ui_weather;

import android.support.v4.app.Fragment;

import com.example.b00sti.bbeacon.R;

import org.androidannotations.annotations.EFragment;

/**
 * Created by b00sti on 20.03.2017.
 */
@EFragment(R.layout.weather_top_fragment)
public class WeaherTopFragment extends Fragment {

    public static WeaherTopFragment newInstance() {
        return new WeaherTopFragment_();
    }

}
