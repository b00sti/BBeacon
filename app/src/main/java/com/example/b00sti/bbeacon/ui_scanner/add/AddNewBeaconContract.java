package com.example.b00sti.bbeacon.ui_scanner.add;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-13
 */

public class AddNewBeaconContract {

    public interface View {

        boolean getEnableDisable();

        String getRange();

        String getTitle();

    }

    public interface Presenter {

        void storageNewItem();

        void selectColor();

    }

}
