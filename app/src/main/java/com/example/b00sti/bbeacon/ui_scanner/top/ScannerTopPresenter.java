package com.example.b00sti.bbeacon.ui_scanner.top;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BasePresenter;
import com.example.b00sti.bbeacon.ui_scanner.interactors.GetLocationInteractor;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.IntArrayRes;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.b00sti.bbeacon.R.id.map;

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
        SupportMapFragment supportMapFragment = (SupportMapFragment) view.getViewChildFragmentManager().findFragmentById(map);
        Log.d(TAG, "init: " + supportMapFragment);
        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(onMapReadyCallback);
        }
    }

    public String convertTime(long time) {
        Date date = new Date(time);
        Format format = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        return format.format(date);
    }

    @Override
    public void showDataOnTheMap(GoogleMap googleMap) {

        List<BeaconLocation> beaconLocations = GetLocationInteractor.fromDatabase(25);

        LatLng latLng = null;
        if (beaconLocations != null && !beaconLocations.isEmpty()) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(beaconLocations.get(0).getLat(), beaconLocations.get(0).getLng())));
            latLng = new LatLng(beaconLocations.get(0).getLat(), beaconLocations.get(0).getLng());
            for (BeaconLocation beaconLocation : beaconLocations) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    markerIcon.setTint(colors[0]);
                }

                googleMap.addPolyline(new PolylineOptions()
                        .add(latLng, new LatLng(beaconLocation.getLat(), beaconLocation.getLng()))
                        .width(5)
                        .color(colors[0]));

                latLng = new LatLng(beaconLocation.getLat(), beaconLocation.getLng());
                googleMap.addMarker(
                        new MarkerOptions()
                                .position(new LatLng(beaconLocation.getLat(), beaconLocation.getLng()))
                                .title(convertTime(beaconLocation.getTime()))
                                .icon(getMarkerIconFromDrawable(markerIcon)));
            }
        }
        /*
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
        }*/
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
