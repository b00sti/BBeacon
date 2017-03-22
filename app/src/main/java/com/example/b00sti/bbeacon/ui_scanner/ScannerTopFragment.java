package com.example.b00sti.bbeacon.ui_scanner;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.b00sti.bbeacon.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.IntArrayRes;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-14
 */


@EFragment(R.layout.scanner_top_fragment)
public class ScannerTopFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = "ScannerTopFragment";

    @IntArrayRes(R.array.beaconColors)
    int colors[];

    @DrawableRes(R.drawable.ic_place_white_18dp)
    Drawable markerIcon;

    public static ScannerTopFragment newInstance() {
        return new ScannerTopFragment_();
    }

    @AfterViews
    void init() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        Log.d(TAG, "init: " + supportMapFragment);
        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(this);
        }
    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        double lat = 50.051667;
        double lng = 19.93;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            markerIcon.setTint(colors[0]);
            googleMap.addMarker(
                    new MarkerOptions()
                            .position(new LatLng(lat, lng))
                            .icon(getMarkerIconFromDrawable(markerIcon)));

            lat = lat + 0.005;
            lng = lng + 0.005;
            markerIcon.setTint(colors[2]);

            googleMap.addMarker(
                    new MarkerOptions()
                            .position(new LatLng(lat, lng))
                            .icon(getMarkerIconFromDrawable(markerIcon)));

            lat = lat + 0.005;
            lng = lng + 0.005;
            markerIcon.setTint(colors[1]);

            googleMap.addMarker(
                    new MarkerOptions()
                            .position(new LatLng(lat, lng))
                            .icon(getMarkerIconFromDrawable(markerIcon)));

            lat = lat - 0.013;
            lng = lng + 0.002;
            markerIcon.setTint(colors[2]);

            googleMap.addMarker(
                    new MarkerOptions()
                            .position(new LatLng(lat, lng))
                            .icon(getMarkerIconFromDrawable(markerIcon)));

            lat = lat + 0.003;
            lng = lng - 0.006;
            markerIcon.setTint(colors[0]);

            googleMap.addMarker(
                    new MarkerOptions()
                            .position(new LatLng(lat, lng))
                            .icon(getMarkerIconFromDrawable(markerIcon)));

            lat = lat + 0.02;
            lng = lng + 0.015;
            markerIcon.setTint(colors[1]);

            googleMap.addMarker(
                    new MarkerOptions()
                            .position(new LatLng(lat, lng))
                            .icon(getMarkerIconFromDrawable(markerIcon)));

            lat = lat - 0.017;
            lng = lng - 0.019;
            markerIcon.setTint(colors[2]);

            googleMap.addMarker(
                    new MarkerOptions()
                            .position(new LatLng(lat, lng))
                            .icon(getMarkerIconFromDrawable(markerIcon)));


            lat = lat + 0.012;
            lng = lng + 0.015;
            markerIcon.setTint(colors[1]);

            googleMap.addMarker(
                    new MarkerOptions()
                            .position(new LatLng(lat, lng))
                            .icon(getMarkerIconFromDrawable(markerIcon)));


            lat = lat - 0.016;
            lng = lng - 0.02;
            markerIcon.setTint(colors[0]);

            googleMap.addMarker(
                    new MarkerOptions()
                            .position(new LatLng(lat, lng))
                            .icon(getMarkerIconFromDrawable(markerIcon)));
        }
    }
}