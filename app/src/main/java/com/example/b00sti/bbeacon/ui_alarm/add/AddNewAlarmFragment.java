package com.example.b00sti.bbeacon.ui_alarm.add;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseFragment;
import com.example.b00sti.bbeacon.utils.SwitchButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-14
 */

@EFragment(R.layout.add_new_alarm_fragment)
public class AddNewAlarmFragment extends BaseFragment<AddNewAlarmPresenter> implements AddNewAlarmContract.View {

    @ViewById(R.id.titleET) EditText titleET;
    @ViewById(R.id.switchSB) SwitchButton switchSB;
    @ViewById(R.id.selectedTimeTV) TextView selectedTimeTV;
    @ViewById(R.id.timeToNextTV) TextView timeToNextTV;
    @ViewById(R.id.day1TV) TextView day1TV;
    @ViewById(R.id.day2TV) TextView day2TV;
    @ViewById(R.id.day3TV) TextView day3TV;
    @ViewById(R.id.day4TV) TextView day4TV;
    @ViewById(R.id.day5TV) TextView day5TV;
    @ViewById(R.id.day6TV) TextView day6TV;
    @ViewById(R.id.day7TV) TextView day7TV;

    @Bean
    AddNewAlarmPresenter alarmPresenter;

    public static AddNewAlarmFragment newInstance() {
        return new AddNewAlarmFragment_();
    }

    @Override
    protected AddNewAlarmPresenter registerPresenter() {
        alarmPresenter.attachView(this);
        return alarmPresenter;
    }

    @AfterViews
    void initUI() {
        Bundle bundle = getArguments();

        if (bundle.containsKey("id")) {
            long id = bundle.getLong("id");
            presenter.initUIAsEditedAlarm(id);
        } else {
            presenter.initUIAsNewAlarm();
        }
    }

    @Click(R.id.selectedTimeTV)
    void onTimeClicked() {
        presenter.selectTime();
    }

    @Click(R.id.switchSB)
    void onSwitchClicked() {
        presenter.enableDisable(switchSB.isChecked());
    }

    @Click(R.id.day1TV)
    void onDay1Clicked() {
        presenter.onRepeatDayClicked(0, day1TV);
    }

    @Click(R.id.day2TV)
    void onDay2Clicked() {
        presenter.onRepeatDayClicked(1, day2TV);
    }

    @Click(R.id.day3TV)
    void onDay3Clicked() {
        presenter.onRepeatDayClicked(2, day3TV);
    }

    @Click(R.id.day4TV)
    void onDay4Clicked() {
        presenter.onRepeatDayClicked(3, day4TV);
    }

    @Click(R.id.day5TV)
    void onDay5Clicked() {
        presenter.onRepeatDayClicked(4, day5TV);
    }

    @Click(R.id.day6TV)
    void onDay6Clicked() {
        presenter.onRepeatDayClicked(5, day6TV);
    }

    @Click(R.id.day7TV)
    void onDay7Clicked() {
        presenter.onRepeatDayClicked(6, day7TV);
    }

    @Click(R.id.selectBeaconTV)
    void onSelectBeaconClicked() {
        presenter.selectBeacon();
    }

    @Click(R.id.timeToNextTV)
    void onTimeToNextAlarmClicked() {
        presenter.selectTime();
    }

    @Click(R.id.doneIV)
    void onSaveClicked() {
        presenter.onAccept();
    }

    @Click(R.id.cancelIV)
    void onCancelClicked() {
        presenter.onCancel();
    }

    @Override
    public void setTime(String time) {
        selectedTimeTV.setText(time);
    }

    @Override
    public void setTimeToNextAlarm(String timeToNextAlarm) {
        timeToNextTV.setText(timeToNextAlarm);
    }

    @Override
    public void setEnableDisable(boolean isEnable) {
        switchSB.setChecked(isEnable);
    }

    @Override
    public void setActivatedDay1(int color) {
        day1TV.setTextColor(color);
    }

    @Override
    public void setActivatedDay2(int color) {
        day2TV.setTextColor(color);
    }

    @Override
    public void setActivatedDay3(int color) {
        day3TV.setTextColor(color);
    }

    @Override
    public void setActivatedDay4(int color) {
        day4TV.setTextColor(color);
    }

    @Override
    public void setActivatedDay5(int color) {
        day5TV.setTextColor(color);
    }

    @Override
    public void setActivatedDay6(int color) {
        day6TV.setTextColor(color);
    }

    @Override
    public void setActivatedDay7(int color) {
        day7TV.setTextColor(color);
    }

    @Override
    public String getTitle() {
        return titleET.getText().toString();
    }

    @Override
    public void setTitle(String title) {
        titleET.setText(title);
    }

}
