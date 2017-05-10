package com.example.b00sti.bbeacon.ui_scanner.details;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseFragment;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-05-10
 */

@EFragment(R.layout.beacon_details_fragment)
public class BeaconDetailsFragment extends BaseFragment<BeaconDetailsPresenter> implements BeaconDetailsContract.View {

    @Bean
    BeaconDetailsPresenter beaconDetailsPresenter;

    public static BeaconDetailsFragment newInstance() {
        return new BeaconDetailsFragment_();
    }

    @Override
    protected BeaconDetailsPresenter registerPresenter() {
        beaconDetailsPresenter.attachView(this);
        return beaconDetailsPresenter;
    }

}
