package com.example.b00sti.bbeacon.ui_alarm.top;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-12
 */

class AlarmTopContract {

    public interface View {

        void setTime(String time);

        void setTimeToNextAlarm(String nextAlarm);
    }

    public interface Presenter {

        void setupRefreshingTime();

        void setNextAlarm();

        void setCurrentTime();
    }

}
