package com.example.b00sti.bbeacon.ui_alarm.main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseItemView;
import com.example.b00sti.bbeacon.utils.SwitchButton;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

@EViewGroup(R.layout.alarm_item_view)
public class AlarmItemView extends BaseItemView<AlarmItem> {

    @ViewById(R.id.layout_item_demo_title) TextView textView;
    @ViewById(R.id.sidebar) View sidebar;
    @ViewById(R.id.moreIV) ImageView moreIV;
    @ViewById(R.id.tempValueTV) TextView timeTV;
    @ViewById(R.id.switchSB) SwitchButton switchSB;
    @ViewById(R.id.topLayoutLL) ViewGroup topLL;

    public AlarmItemView(Context context) {
        super(context);
    }

    public void setOnSwitchClickedListener(final OnSwitchClickedListener onSwitchClickedListener) {

        switchSB.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (onSwitchClickedListener != null) {
                    onSwitchClickedListener.refreshAdapter(isChecked);
                }
            }
        });

    }

    public void afterDeleteClick(OnClickListener onClickListener) {
        moreIV.setOnClickListener(onClickListener);
    }

    @Override
    public void bind(final AlarmItem alarmItem) {
        textView.setText(alarmItem.getText());
        sidebar.setBackgroundColor(alarmItem.getColor());
        switchSB.setChecked(alarmItem.isEnabled);
        switchSB.setColor(alarmItem.getColor());
        topLL.setBackgroundColor(alarmItem.getColor());
        timeTV.setText(alarmItem.getTime());
    }
}