package com.example.b00sti.bbeacon.ui_alarm.main;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseInnerViewActivity_;
import com.example.b00sti.bbeacon.base.BasePresenter;
import com.example.b00sti.bbeacon.navigation.NavigationNotificationEvent;
import com.example.b00sti.bbeacon.ui_alarm.interactors.RemoveAlarmInteractor;
import com.example.b00sti.bbeacon.ui_alarm.interactors.SetAlarmInteractor;
import com.example.b00sti.bbeacon.utils.FragmentBuilder;
import com.example.b00sti.bbeacon.utils.RealmUtils;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-30
 */

@EBean
public class AlarmAdapterPresenter extends BasePresenter<AlarmContract.View> implements AlarmAdapterContract {

    @RootContext
    Context ctx;

    @Override
    public void onItemClick(AlarmItem alarmItem) {
        Intent intent = new Intent(ctx, BaseInnerViewActivity_.class);
        intent.putExtra(ctx.getString(R.string.bundle_fragment), FragmentBuilder.ADD_NEW_ALARM);
        intent.putExtra("id", alarmItem.getId());
        ctx.startActivity(intent);
    }

    @Override
    public void onSwitchClick(AlarmItem alarmItem, boolean isChecked) {
        if (alarmItem.isEnabled != isChecked) {
            alarmItem.setEnabled(isChecked);

            new SetAlarmInteractor().execute(alarmItem, new RealmUtils.OnSuccessListener() {
                @Override
                public void onSuccess() {
                    EventBus.getDefault().post(new NavigationNotificationEvent());
                }
            });
        }
    }

    @Override
    public void onRemoveClick(AlarmItem alarmItem, int position) {
        if (alarmItem.isEnabled()) {
            Toast.makeText(ctx, R.string.first_disable_alarm, Toast.LENGTH_LONG).show();
        } else {
            new RemoveAlarmInteractor().execute(alarmItem);
            view.getAdapter().getDataSet().remove(alarmItem);
            view.getAdapter().notifyItemRemoved(position);
            if (view.getAdapter().getDataSet().isEmpty()) {
                view.onEmptyDataSet(true);
            }
        }
    }


    @Override
    public void onSubscribe() {

    }

    @Override
    public void onUnsubscribe() {

    }
}
