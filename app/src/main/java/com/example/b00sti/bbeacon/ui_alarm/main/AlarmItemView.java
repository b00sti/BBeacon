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
import org.androidannotations.annotations.res.ColorRes;

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
    @ViewById(R.id.day1TV) TextView day1TV;
    @ViewById(R.id.day2TV) TextView day2TV;
    @ViewById(R.id.day3TV) TextView day3TV;
    @ViewById(R.id.day4TV) TextView day4TV;
    @ViewById(R.id.day5TV) TextView day5TV;
    @ViewById(R.id.day6TV) TextView day6TV;
    @ViewById(R.id.day7TV) TextView day7TV;

    @ColorRes(R.color.colorAccent)
    int colorActivatedDay;

    @ColorRes(android.R.color.primary_text_light)
    int colorDeactivatedDay;

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
        configureDay(alarmItem, 0, day1TV);
        configureDay(alarmItem, 1, day2TV);
        configureDay(alarmItem, 2, day3TV);
        configureDay(alarmItem, 3, day4TV);
        configureDay(alarmItem, 4, day5TV);
        configureDay(alarmItem, 5, day6TV);
        configureDay(alarmItem, 6, day7TV);
    }

    private void configureDay(AlarmItem alarmItem, int i, TextView textView) {
        if (alarmItem.getIsEnabledRepeat().get(i).isEnabled()) {
            textView.setTextColor(colorActivatedDay);
        } else {
            textView.setTextColor(colorDeactivatedDay);
        }
    }
}
