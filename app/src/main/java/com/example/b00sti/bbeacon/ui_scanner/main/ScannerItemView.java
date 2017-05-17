package com.example.b00sti.bbeacon.ui_scanner.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseItemView;
import com.example.b00sti.bbeacon.navigation.NavigationNotificationEvent;
import com.example.b00sti.bbeacon.ui_scanner.interactors.SetScannerInteractor;
import com.example.b00sti.bbeacon.utils.RealmUtils;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

@EViewGroup(R.layout.scanner_item_view)
public class ScannerItemView extends BaseItemView<ScannerItem> {

    @ViewById(R.id.layout_item_demo_title) TextView textView;
    @ViewById(R.id.sidebar) View sidebar;
    @ViewById(R.id.enableDisableIV) ImageView enableDisableIV;
    @ViewById(R.id.lastVisibleTV) TextView lastVisibleTV;
    @ViewById(R.id.strengthPB) RoundCornerProgressBar progressBar;
    @ViewById(R.id.topLayoutLL) ViewGroup topLL;

    @DrawableRes(R.drawable.ic_bluetooth_connected_white_24dp)
    Drawable enabled;

    @DrawableRes(R.drawable.ic_bluetooth_disabled_black_24dp)
    Drawable disabled;

    boolean isEnabled = true;

    public ScannerItemView(Context context) {
        super(context);
    }

    @Override
    public void bind(final ScannerItem scannerItem) {
        textView.setText(scannerItem.getTitle());
        sidebar.setBackgroundColor(scannerItem.getColor());
        progressBar.setProgressColor(scannerItem.getColor());
        progressBar.setProgress(scannerItem.getRange());
        topLL.setBackgroundColor(scannerItem.getColor());

        isEnabled = scannerItem.isEnabled();
        if (isEnabled) {
            enableDisableIV.setImageDrawable(enabled);
        } else {
            enableDisableIV.setImageDrawable(disabled);
        }

        enableDisableIV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEnabled) {
                    isEnabled = false;
                    scannerItem.setEnabled(false);
                    enableDisableIV.setImageDrawable(disabled);
                } else {
                    isEnabled = true;
                    scannerItem.setEnabled(true);
                    enableDisableIV.setImageDrawable(enabled);
                }
                new SetScannerInteractor().execute(scannerItem, new RealmUtils.OnSuccessListener() {
                    @Override
                    public void onSuccess() {
                        EventBus.getDefault().post(new NavigationNotificationEvent());
                    }
                });
            }
        });
    }
}