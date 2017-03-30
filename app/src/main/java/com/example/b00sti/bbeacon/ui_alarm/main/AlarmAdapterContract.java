package com.example.b00sti.bbeacon.ui_alarm.main;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-30
 */

interface AlarmAdapterContract {

    void onItemClick(AlarmItem alarmItem);

    void onSwitchClick(final AlarmItem alarmItem, boolean isChecked);

    void onRemoveClick(final AlarmItem alarmItem, int position);

}
