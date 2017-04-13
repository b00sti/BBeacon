package com.example.b00sti.bbeacon.ui_scanner.main;

import java.util.List;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

class ScannerContract {

    public interface View {

        void refreshData(List<ScannerItem> items);

    }

    public interface Presenter {

        void fetchData();

    }
}
