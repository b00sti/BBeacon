package com.example.b00sti.bbeacon.ui_alarm.main;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseAdapter;
import com.example.b00sti.bbeacon.base.BaseInnerViewActivity_;
import com.example.b00sti.bbeacon.base.ViewWrapper;
import com.example.b00sti.bbeacon.ui_alarm.NotificationEvent;
import com.example.b00sti.bbeacon.ui_alarm.interactors.RemoveAlarmInteractor;
import com.example.b00sti.bbeacon.ui_alarm.interactors.SetAlarmInteractor;
import com.example.b00sti.bbeacon.utils.FragmentBuilder;
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
    Context ctx;

    @Override
    protected AlarmItemView onCreateItemView(ViewGroup parent, int viewType) {
        return AlarmItemView_.build(ctx);
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
                Intent intent = new Intent(ctx, BaseInnerViewActivity_.class);
                intent.putExtra(ctx.getString(R.string.bundle_fragment), FragmentBuilder.ADD_NEW_ALARM);
                intent.putExtra("id", alarmItem.id);
                ctx.startActivity(intent);
            }
        });

        alarmItemView.afterDeleteClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alarmItem.isEnabled()) {
                    Toast.makeText(ctx, "First disable alarm !", Toast.LENGTH_LONG).show();
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
