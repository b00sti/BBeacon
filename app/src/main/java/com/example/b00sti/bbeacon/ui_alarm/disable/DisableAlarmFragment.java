package com.example.b00sti.bbeacon.ui_alarm.disable;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseFragment;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

/**
 * Created by b00sti on 28.04.2017
 */

@EFragment(R.layout.alarm_disable_fragment)
public class DisableAlarmFragment extends BaseFragment<DisableAlarmPresenter> implements DisableAlarmContract.View {

    @Bean
    DisableAlarmPresenter disableAlarmPresenter;

    public static DisableAlarmFragment newInstance() {
        return new DisableAlarmFragment_();
    }

    @Override
    protected DisableAlarmPresenter registerPresenter() {
        disableAlarmPresenter.attachView(this);
        return disableAlarmPresenter;
    }


}
