package com.example.b00sti.bbeacon.ui_alarm.main;

import android.content.Context;
import android.view.ViewGroup;

import com.example.b00sti.bbeacon.base.BaseAdapter;
import com.example.b00sti.bbeacon.base.ViewWrapper;
import com.example.b00sti.bbeacon.ui_alarm.NotificationEvent;
import com.example.b00sti.bbeacon.ui_alarm.interactors.SetAlarmInteractor;
import com.example.b00sti.bbeacon.utils.RealmUtils;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

@EBean
public class AlarmAdapter extends BaseAdapter<AlarmItem, AlarmItemView> {

    @RootContext
    Context context;

    @Override
    protected AlarmItemView onCreateItemView(ViewGroup parent, int viewType) {
        return AlarmItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(final ViewWrapper<AlarmItemView> holder, final int position) {
        AlarmItemView alarmItemView = holder.getView();
        AlarmItem alarmItem = dataSet.get(position);
        alarmItemView.setOnSwitchClickedListener(new OnSwitchClickedListener() {
            @Override
            public void refreshAdapter(boolean isChecked) {
                AlarmItem item = dataSet.get(position);
                if (item.isEnabled != isChecked) {
                    item.setEnabled(isChecked);

                    new SetAlarmInteractor().execute(item, new RealmUtils.OnSuccessListener() {
                        @Override
                        public void onSuccess() {
                           EventBus.getDefault().post(new NotificationEvent());
                        }
                    });
                }
            }
        });
        alarmItemView.bind(alarmItem);
    }
}
