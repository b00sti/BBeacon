package com.example.b00sti.bbeacon.ui_scanner;

import android.support.v4.app.Fragment;

import com.example.b00sti.bbeacon.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentById;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-14
 */


@EFragment(R.layout.scanner_top_fragment)
public class ScannerTopFragment extends Fragment implements OnMapReadyCallback {

    @FragmentById(R.id.map) SupportMapFragment supportMapFragment;

    public static ScannerTopFragment newInstance() {
        return new ScannerTopFragment_();
    }

    @AfterViews
    void init() {
        //SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng place = new LatLng(50.00, 19.9);
        googleMap.addMarker(
                new MarkerOptions()
                        .position(place)
                        .title("My BBeacon"));

        LatLng place2 = new LatLng(50.03, 19.93);
        googleMap.addMarker(
                new MarkerOptions()
                        .position(place2)
                        .title("My BBeacon 2 "));

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(place));
    }

}