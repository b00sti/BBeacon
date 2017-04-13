package com.example.b00sti.bbeacon.ui_alarm.add;

import android.widget.TextView;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-12
 */

class AddNewAlarmContract {

    public interface View {

        void setTime(String time);

        void setTimeToNextAlarm(String timeToNextAlarm);

        void setEnableDisable(boolean isEnable);

        void setActivatedDay1(int color);

        void setActivatedDay2(int color);

        void setActivatedDay3(int color);

        void setActivatedDay4(int color);

        void setActivatedDay5(int color);

        void setActivatedDay6(int color);

        void setActivatedDay7(int color);

        String getTitle();

        void setTitle(String title);

    }

    public interface Presenter {

        void selectTime();

        void selectBeacon();

        void initUIAsEditedAlarm(long id);

        void initUIAsNewAlarm();

        void enableDisable(boolean newStatus);

        void onCancel();

        void onAccept();

        void onRepeatDayClicked(int i, TextView textView);
    }


}
