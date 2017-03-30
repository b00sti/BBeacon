package com.example.b00sti.bbeacon.ui_alarm.main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.b00sti.bbeacon.base.BaseAdapter;
import com.example.b00sti.bbeacon.base.ViewWrapper;
import com.example.b00sti.bbeacon.ui_alarm.NotificationEvent;
import com.example.b00sti.bbeacon.ui_alarm.interactors.RemoveAlarmInteractor;
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
        final AlarmItem alarmItem = dataSet.get(position);

        alarmItemView.setOnSwitchClickedListener(new OnSwitchClickedListener() {
            @Override
            public void refreshAdapter(boolean isChecked) {
                if (alarmItem.isEnabled != isChecked) {
                    alarmItem.setEnabled(isChecked);

                    new SetAlarmInteractor().execute(alarmItem, new RealmUtils.OnSuccessListener() {
                        @Override
                        public void onSuccess() {
                           EventBus.getDefault().post(new NotificationEvent());
                        }
                    });
                }
            }
        });

        alarmItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_LONG).show();
            }
        });

        alarmItemView.afterDeleteClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alarmItem.isEnabled()) {
                    Toast.makeText(context, "First disable alarm !", Toast.LENGTH_LONG).show();
                } else {
                    new RemoveAlarmInteractor().execute(alarmItem);
                    dataSet.remove(alarmItem);
                    notifyItemRemoved(position);
                }
            }
        });

        alarmItemView.bind(alarmItem);
    }
}
