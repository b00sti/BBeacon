package com.example.b00sti.bbeacon.ui_alarm.main;

import java.util.List;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

public class AlarmContract {

    public interface View {

        void showProgressBar();

        void hideProgressBar();

        void showNoConnection();

        void refreshData(List<AlarmItem> items);

        AlarmAdapter getAdapter();

        void onEmptyDataSet(boolean isEmpty);

    }

    public interface Presenter {

        String getTitleToRefreshToolbar();

        void fetchData();

    }
}
