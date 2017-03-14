package com.example.b00sti.bbeacon.ui_alarm;

import android.support.v4.app.Fragment;

import com.example.b00sti.bbeacon.R;

import org.androidannotations.annotations.EFragment;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-14
 */

@EFragment(R.layout.alarm_top_fragment)
public class AlarmTopFragment extends Fragment {

    public static AlarmTopFragment newInstance() {
        return new AlarmTopFragment_();
    }

}
