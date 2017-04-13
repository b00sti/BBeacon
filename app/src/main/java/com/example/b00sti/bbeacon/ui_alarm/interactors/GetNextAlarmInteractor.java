package com.example.b00sti.bbeacon.ui_alarm.interactors;

import android.support.v4.util.Pair;

import com.example.b00sti.bbeacon.ui_alarm.main.AlarmItem;
import com.example.b00sti.bbeacon.utils.RealmUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-28
 */

public class GetNextAlarmInteractor {

    public Pair<AlarmItem, Long> executeToGetTime() {
        List<AlarmItem> items = RealmUtils.FindAll(AlarmItem.class);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMin = calendar.get(Calendar.MINUTE);

        ArrayList<Pair<AlarmItem, Long>> pairs = new ArrayList<>();

        for (AlarmItem alarmItem : items) {
            if (alarmItem.isEnabled()) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());

                String segments[] = alarmItem.getTime().split(":");
                if (Integer.valueOf(segments[0]) < currentHour || Integer.valueOf(segments[0]) == currentHour && Integer.valueOf(segments[1]) < currentMin) {
                    cal.add(Calendar.DAY_OF_YEAR, 1);
                    cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(segments[0]));
                    cal.set(Calendar.MINUTE, Integer.valueOf(segments[1]));
                } else {
                    cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(segments[0]));
                    cal.set(Calendar.MINUTE, Integer.valueOf(segments[1]));
                }

                pairs.add(new Pair<>(alarmItem, cal.getTimeInMillis()));
            }
        }

        long min = Long.MAX_VALUE;
        Pair<AlarmItem, Long> pairResult = new Pair<>(new AlarmItem(), min);

        for (Pair<AlarmItem, Long> pair : pairs) {
            if (pair.second < min) {
                min = pair.second;
                pairResult = pair;
            }
        }

        return pairResult;
    }

    public AlarmItem execute() {
        Pair<AlarmItem, Long> result = executeToGetTime();
        return result.first;
    }

}
