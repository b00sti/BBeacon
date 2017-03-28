package com.example.b00sti.bbeacon.ui_alarm;

import com.example.b00sti.bbeacon.utils.RealmUtils;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-28
 */

public class GetNextAlarmInteractor {

    public AlarmItem execute() {
        List<AlarmItem> items = RealmUtils.FindAll(AlarmItem.class);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        AlarmItem result = new AlarmItem();
        int nearestHour = 24;
        int nearestMin = 60;
        for (AlarmItem alarmItem : items) {
            if (alarmItem.isEnabled) {
                String segments[] = alarmItem.getTime().split(":");
                    if (Integer.valueOf(segments[0]) < nearestHour || Integer.valueOf(segments[0]) == nearestHour && Integer.valueOf(segments[1]) < nearestMin) {
                        nearestHour = Integer.valueOf(segments[0]);
                        nearestMin = Integer.valueOf(segments[1]);
                        result = alarmItem;
                    }
            }
        }
        return result;
    }

}
