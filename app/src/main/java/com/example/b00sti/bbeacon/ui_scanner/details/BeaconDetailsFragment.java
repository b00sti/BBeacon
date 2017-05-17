package com.example.b00sti.bbeacon.ui_scanner.details;

import android.widget.EditText;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseFragment;
import com.example.b00sti.bbeacon.ui_scanner.main.ScannerItem;
import com.example.b00sti.bbeacon.utils.SwitchButton;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-05-10
 */

@EFragment(R.layout.beacon_details_fragment)
public class BeaconDetailsFragment extends BaseFragment<BeaconDetailsPresenter> implements BeaconDetailsContract.View {

    @ViewById EditText titleET;
    @ViewById TextView colorPickerTV;
    @ViewById TextView batteryStatusTV;
    @ViewById TextView movingDetectionAlarmTV;
    @ViewById SwitchButton switchEnableScannignSB;
    @ViewById SwitchButton switchEnableTrackingSB;
    @ViewById TextView lastVisibleTV;
    @ViewById TextView distanceTV;
    @ViewById RoundCornerProgressBar strengthPB;
    @ViewById DiscreteSeekBar GPSLocationPeriodPB;
    @ViewById DiscreteSeekBar GPSLastPointsPB;
    @ViewById DiscreteSeekBar timeToNotificationPB;

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

    @Click(R.id.movingDetectionAlarmTV)
    void onAlarmClick() {
        presenter.onMovingAlarmClick();
    }

    @Click(R.id.doneIV)
    void onDoneClick() {
        ScannerItem scannerItem = presenter.getActualScannerItem();
        presenter.onPositiveClick(scannerItem);
    }

    @Click(R.id.cancelIV)
    void onCancelClick() {
        presenter.onNegativeClick();
    }

    @Override
    public void changeMovingAlarmView(boolean isAlarmed) {
        if (isAlarmed) {
            movingDetectionAlarmTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_priority_high_red_900_24dp, 0);
        } else {
            movingDetectionAlarmTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_done_green_500_24dp, 0);
        }
    }

    @Override
    public void finishActivity() {
        getActivity().finish();
    }
}
