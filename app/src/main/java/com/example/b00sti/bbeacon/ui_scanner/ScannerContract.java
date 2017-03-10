package com.example.b00sti.bbeacon.ui_scanner;

import java.util.List;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

public class ScannerContract {

    public interface View {

        void showProgressBar();

        void hideProgressBar();

        void showNoConnection();

        void refreshData(List<ScannerItem> items);

    }

    public interface Presenter {

        void fetchData();

    }
}