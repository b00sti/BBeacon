package com.example.b00sti.bbeacon.ui_alarm;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseItemView;
import com.example.b00sti.bbeacon.utils.SwitchButton;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.IntArrayRes;

import java.util.Random;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

@EViewGroup(R.layout.alarm_item_view)
public class AlarmItemView extends BaseItemView<AlarmItem> {

    @ViewById(R.id.layout_item_demo_title) TextView textView;
    @ViewById(R.id.sidebar) View sidebar;
    @ViewById(R.id.moreIV) ImageView moreIV;
    @ViewById(R.id.timeTV) TextView timeTV;
    @ViewById(R.id.switchSB) SwitchButton switchSB;
    @ViewById(R.id.daysTV) TextView daysTV;

    @IntArrayRes(R.array.beaconColors)
    int colors[];

    public AlarmItemView(Context context) {
        super(context);
    }

    @Override
    public void bind(AlarmItem alarmItem) {
        textView.setText(alarmItem.getText());
        int a = new Random().nextInt(colors.length);
        sidebar.setBackgroundColor(colors[a]);
        switchSB.setChecked(new Random().nextBoolean());
        switchSB.setColor(colors[a]);
        String time = "" + new Random().nextInt(24) + ":" + (new Random().nextInt(51) + 10);
        timeTV.setText(time);
    }
}
