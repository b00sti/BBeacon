package com.example.b00sti.bbeacon.ui_scanner;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseItemView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.IntArrayRes;

import java.util.Random;

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

    @IntArrayRes(R.array.beaconColors)
    int colors[];

    @DrawableRes(R.drawable.ic_bluetooth_connected_indigo_800_24dp)
    Drawable enabled;

    @DrawableRes(R.drawable.ic_bluetooth_disabled_black_24dp)
    Drawable disabled;

    boolean isEnabled = true;

    public ScannerItemView(Context context) {
        super(context);
    }

    @Override
    public void bind(ScannerItem scannerItem) {
        textView.setText(scannerItem.getText());
        int a = new Random().nextInt(colors.length);
        sidebar.setBackgroundColor(colors[a]);
        progressBar.setProgressColor(colors[a]);
        progressBar.setProgress((float) new Random().nextInt(100));
        topLL.setBackgroundColor(colors[a]);

        enableDisableIV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEnabled) {
                    isEnabled = false;
                    enableDisableIV.setImageDrawable(disabled);
                } else {
                    isEnabled = true;
                    enableDisableIV.setImageDrawable(enabled);
                }
            }
        });
    }
}