package com.example.b00sti.bbeacon.ui_scanner.top;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BasePresenter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.IntArrayRes;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-13
 */

@EBean
public class ScannerTopPresenter extends BasePresenter<ScannerTopContract.View> implements ScannerTopContract.Presenter {
    private static final String TAG = "ScannerTopPresenter";

    @IntArrayRes(R.array.beaconColors)
    int colors[];

    @DrawableRes(R.drawable.ic_place_white_18dp)
    Drawable markerIcon;

    @Override
    public void onSubscribe() {

    }

    @Override
    public void onUnsubscribe() {

    }

    @Override
    public void getMapAsync(OnMapReadyCallback onMapReadyCallback) {
        SupportMapFragment supportMapFragment = (SupportMapFragment) view.getViewChildFragmentManager().findFragmentById(R.id.map);
        Log.d(TAG, "init: " + supportMapFragment);
        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(onMapReadyCallback);
        }
    }

    @Override
    public void showDataOnTheMap(GoogleMap googleMap) {
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

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}
