package com.example.b00sti.bbeacon.ui_scanner.top;

import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-13
 */

class ScannerTopContract {

    public interface View {

        FragmentManager getViewChildFragmentManager();

    }

    public interface Presenter {

        void getMapAsync(OnMapReadyCallback onMapReadyCallback);

        void showDataOnTheMap(GoogleMap googleMap);

    }

}
