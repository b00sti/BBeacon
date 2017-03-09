package com.example.b00sti.bbeacon.ui_alarm;

import android.content.Context;
import android.widget.TextView;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseItemView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

@EViewGroup(R.layout.alarm_item_view)
public class AlarmItemView extends BaseItemView<AlarmItem> {

    @ViewById(R.id.layout_item_demo_title) TextView textView;

    public AlarmItemView(Context context) {
        super(context);
    }

    @Override
    public void bind(AlarmItem alarmItem) {
        textView.setText(alarmItem.getText());
    }
}
