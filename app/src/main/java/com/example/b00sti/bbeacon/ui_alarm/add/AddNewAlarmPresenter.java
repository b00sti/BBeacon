package com.example.b00sti.bbeacon.ui_alarm.add;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BasePresenter;
import com.example.b00sti.bbeacon.ui_alarm.interactors.GetAlarmInteractor;
import com.example.b00sti.bbeacon.ui_alarm.interactors.SetAlarmInteractor;
import com.example.b00sti.bbeacon.ui_alarm.main.AlarmItem;
import com.example.b00sti.bbeacon.utils.RealmUtils;
import com.example.b00sti.bbeacon.utils.TimeUtils;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.IntArrayRes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-12
 */

@EBean
public class AddNewAlarmPresenter extends BasePresenter<AddNewAlarmContract.View> implements AddNewAlarmContract.Presenter {

    @RootContext
    Activity ctx;

    @IntArrayRes(R.array.beaconColors)
    int defaultColors[];

    @ColorRes(R.color.colorPrimaryDark)
    int mainColor;

    @ColorRes(R.color.colorAccent)
    int colorActivatedDay;

    @ColorRes(android.R.color.primary_text_light)
    int colorDeactivatedDay;

    private String time;
    private AlarmItem alarmItem = null;
    private boolean[] days = new boolean[7];

    @Override
    public void initUIAsEditedAlarm(long id) {
        alarmItem = new GetAlarmInteractor().execute(id);

        if (alarmItem != null) {
            initEditedAlarm();
        } else {
            initUIAsNewAlarm();
        }
    }

    @Override
    public void initUIAsNewAlarm() {
        initNewAlarm();
    }

    @Override
    public void selectTime() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time = hourOfDay + ":" + minute;
                time = TimeUtils.getTimeWith0(time);
                updateTime();
            }
        };

        TimePickerDialog timePicker = new TimePickerDialog(ctx,
                t,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);
        timePicker.setTitle("");
        timePicker.show();
    }

    private void updateTime() {
        view.setTime(time);
        view.setTimeToNextAlarm(getPreparedTimeToNextAlarm());
    }

    @Override
    public void selectBeacon() {
        ArrayList<String> strings = new ArrayList<>();
        for (int i : defaultColors) {
            strings.add("Color: " + i);
        }

        new MaterialDialog.Builder(ctx)
                .title(R.string.select_color)
                .items(strings)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        int colorsSize = defaultColors.length;
                        if (which >= 0 & which <= colorsSize) {
                            mainColor = defaultColors[which];
                        }
                        return true;
                    }
                })
                .positiveText(android.R.string.ok)
                .show();
    }

    @Override
    public void enableDisable(boolean newStatus) {
        if (alarmItem != null) {
            alarmItem.setEnabled(newStatus);
        }
    }

    @Override
    public void onCancel() {
        ctx.finish();
    }

    @Override
    public void onAccept() {
        prepareAlarmToSave();

        new SetAlarmInteractor().executeWithId(alarmItem, new RealmUtils.OnSuccessListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(ctx, R.string.after_alarm_updated, Toast.LENGTH_LONG).show();
                ctx.finish();
            }
        });
    }

    @Override
    public void onRepeatDayClicked(int i, TextView textView) {
        if (days[i]) {
            days[i] = false;
            textView.setTextColor(colorDeactivatedDay);
        } else {
            days[i] = true;
            textView.setTextColor(colorActivatedDay);
        }
    }

    @Override
    public void onSubscribe() {

    }

    @Override
    public void onUnsubscribe() {

    }

    private void initNewAlarm() {
        for (int i = 0; i < 7; i++) {
            days[i] = false;
        }

        time = getCurrentTime();
        alarmItem = new AlarmItem();
        alarmItem.setEnabled(true);
        alarmItem.setTime(time);
        alarmItem.setColor(mainColor);
        alarmItem.setText(ctx.getString(R.string.new_alarm));

        view.setEnableDisable(alarmItem.isEnabled());
        view.setTime(alarmItem.getTime());
        view.setTimeToNextAlarm(getPreparedTimeToNextAlarm());
    }

    private void initEditedAlarm() {
        for (int i = 0; i < 7; i++) {
            days[i] = alarmItem.getIsEnabledRepeat().get(i).isEnabled();
        }

        time = alarmItem.getTime();
        mainColor = alarmItem.getColor();
        view.setTime(time);
        view.setEnableDisable(alarmItem.isEnabled());
        view.setTimeToNextAlarm(getPreparedTimeToNextAlarm());
        view.setTitle(alarmItem.getText());

        if (alarmItem.getIsEnabledRepeat().get(0).isEnabled()) {
            view.setActivatedDay1(colorActivatedDay);
        } else {
            view.setActivatedDay1(colorDeactivatedDay);
        }
        if (alarmItem.getIsEnabledRepeat().get(1).isEnabled()) {
            view.setActivatedDay2(colorActivatedDay);
        } else {
            view.setActivatedDay2(colorDeactivatedDay);
        }
        if (alarmItem.getIsEnabledRepeat().get(2).isEnabled()) {
            view.setActivatedDay3(colorActivatedDay);
        } else {
            view.setActivatedDay3(colorDeactivatedDay);
        }
        if (alarmItem.getIsEnabledRepeat().get(3).isEnabled()) {
            view.setActivatedDay4(colorActivatedDay);
        } else {
            view.setActivatedDay4(colorDeactivatedDay);
        }
        if (alarmItem.getIsEnabledRepeat().get(4).isEnabled()) {
            view.setActivatedDay5(colorActivatedDay);
        } else {
            view.setActivatedDay5(colorDeactivatedDay);
        }
        if (alarmItem.getIsEnabledRepeat().get(5).isEnabled()) {
            view.setActivatedDay6(colorActivatedDay);
        } else {
            view.setActivatedDay6(colorDeactivatedDay);
        }
        if (alarmItem.getIsEnabledRepeat().get(6).isEnabled()) {
            view.setActivatedDay7(colorActivatedDay);
        } else {
            view.setActivatedDay7(colorDeactivatedDay);
        }
    }

    private String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat(ctx.getString(R.string.date_format_hhmm), Locale.getDefault());

        return dateFormat.format(c.getTime());//TimeUtils.getTimeWith0(dateFormat.format(c.getTemp()));
    }

    private String getPreparedTimeToNextAlarm() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());

        int currentHour = cal.get(Calendar.HOUR_OF_DAY);
        int currentMin = cal.get(Calendar.MINUTE);

        String segments[] = time.split(":");
        if (Integer.valueOf(segments[0]) < currentHour || Integer.valueOf(segments[0]) == currentHour && Integer.valueOf(segments[1]) < currentMin) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
            cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(segments[0]));
            cal.set(Calendar.MINUTE, Integer.valueOf(segments[1]));
        } else {
            cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(segments[0]));
            cal.set(Calendar.MINUTE, Integer.valueOf(segments[1]));
        }

        String result = TimeUtils.twoDatesBetweenTime(cal.getTimeInMillis());

        return String.format(ctx.getString(R.string.to_next_alarm), result);
    }

    private void prepareAlarmToSave() {
        if (alarmItem != null) {
            alarmItem.setText(view.getTitle());
            alarmItem.setColor(mainColor);
            alarmItem.setTime(time);
            setDaysToRepeat();
        }
    }

    private void setDaysToRepeat() {
        for (int i = 0; i < 7; i++) {
            alarmItem.getIsEnabledRepeat().get(i).setEnabled(days[i]);
        }
    }
}
