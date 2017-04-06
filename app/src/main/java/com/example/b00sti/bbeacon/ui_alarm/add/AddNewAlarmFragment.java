package com.example.b00sti.bbeacon.ui_alarm.add;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.ui_alarm.interactors.GetAlarmInteractor;
import com.example.b00sti.bbeacon.ui_alarm.interactors.SetAlarmInteractor;
import com.example.b00sti.bbeacon.ui_alarm.main.AlarmItem;
import com.example.b00sti.bbeacon.utils.RealmUtils;
import com.example.b00sti.bbeacon.utils.SwitchButton;
import com.example.b00sti.bbeacon.utils.TimeUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.IntArrayRes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import me.grantland.widget.AutofitTextView;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-14
 */

@EFragment(R.layout.add_new_alarm_fragment)
public class AddNewAlarmFragment extends Fragment {

    @ViewById(R.id.titleET) EditText titleET;
    @ViewById(R.id.switchSB) SwitchButton switchSB;
    @ViewById(R.id.selectedTimeTV) AutofitTextView selectedTimeTV;
    @ViewById(R.id.timeToNextTV) TextView timeToNextTV;
    @ViewById(R.id.day1TV) TextView day1TV;
    @ViewById(R.id.day2TV) TextView day2TV;
    @ViewById(R.id.day3TV) TextView day3TV;
    @ViewById(R.id.day4TV) TextView day4TV;
    @ViewById(R.id.day5TV) TextView day5TV;
    @ViewById(R.id.day6TV) TextView day6TV;
    @ViewById(R.id.day7TV) TextView day7TV;

    @IntArrayRes(R.array.beaconColors)
    int colors[];

    @ColorRes(R.color.colorPrimaryDark)
    int color;

    @ColorRes(R.color.colorAccent)
    int colorActivatedDay;

    @ColorRes(android.R.color.primary_text_light)
    int colorDeactivatedDay;

    String time;
    AlarmItem alarmItem = null;
    boolean[] days = new boolean[7];

    public static AddNewAlarmFragment newInstance() {
        return new AddNewAlarmFragment_();
    }

    @AfterViews
    void initUI() {
        Bundle bundle = getArguments();

        if (bundle.containsKey("id")) {
            long id = bundle.getLong("id");
            alarmItem = new GetAlarmInteractor().execute(id);
        }

        if (alarmItem != null) {

            for (int i = 0; i < 7; i++) {
                days[i] = alarmItem.getIsEnabledRepeat().get(i).isEnabled();
            }

            time = alarmItem.getTime();
            switchSB.setChecked(alarmItem.isEnabled());
            selectedTimeTV.setText(time);
            timeToNextTV.setText(getTimeToAlarm());
            titleET.setText(alarmItem.getText());

            if (alarmItem.getIsEnabledRepeat().get(0).isEnabled()) {
                day1TV.setTextColor(colorActivatedDay);
            } else {
                day1TV.setTextColor(colorDeactivatedDay);
            }
            if (alarmItem.getIsEnabledRepeat().get(1).isEnabled()) {
                day2TV.setTextColor(colorActivatedDay);
            } else {
                day2TV.setTextColor(colorDeactivatedDay);
            }
            if (alarmItem.getIsEnabledRepeat().get(2).isEnabled()) {
                day3TV.setTextColor(colorActivatedDay);
            } else {
                day3TV.setTextColor(colorDeactivatedDay);
            }
            if (alarmItem.getIsEnabledRepeat().get(3).isEnabled()) {
                day4TV.setTextColor(colorActivatedDay);
            } else {
                day4TV.setTextColor(colorDeactivatedDay);
            }
            if (alarmItem.getIsEnabledRepeat().get(4).isEnabled()) {
                day5TV.setTextColor(colorActivatedDay);
            } else {
                day5TV.setTextColor(colorDeactivatedDay);
            }
            if (alarmItem.getIsEnabledRepeat().get(5).isEnabled()) {
                day6TV.setTextColor(colorActivatedDay);
            } else {
                day6TV.setTextColor(colorDeactivatedDay);
            }
            if (alarmItem.getIsEnabledRepeat().get(6).isEnabled()) {
                day7TV.setTextColor(colorActivatedDay);
            } else {
                day7TV.setTextColor(colorDeactivatedDay);
            }
        } else {

            for (int i = 0; i < 7; i++) {
                days[i] = false;
            }

            time = getCurrentTime();
            switchSB.setChecked(true);
            selectedTimeTV.setText(time);
            timeToNextTV.setText(getTimeToAlarm());
        }

    }

    private String getTimeToAlarm() {
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

        String time = TimeUtils.twoDatesBetweenTime(cal.getTimeInMillis());

        return "Time to next alarm: " + time;
    }

    private String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        return dateFormat.format(c.getTime());//TimeUtils.getTimeWith0(dateFormat.format(c.getTemp()));
    }

    @Click(R.id.selectBeaconTV)
    void selectColor() {
        ArrayList<String> strings = new ArrayList<>();
        for (int i : colors) {
            strings.add("Color: " + i);
        }

        new MaterialDialog.Builder(getActivity())
                .title("Select color")
                .items(strings)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        int colorsSize = colors.length;
                        if (which >= 0 & which <= colorsSize) {
                            color = colors[which];
                        }
                        return true;
                    }
                })
                .positiveText("Ok")
                .show();
    }

    @Click(R.id.selectedTimeTV)
    void onTimeClicked() {
        getTimeFromUser();
    }

    @Click(R.id.timeToNextTV)
    void onTimeToNextAlarmClicked() {
        getTimeFromUser();
    }

    private void getTimeFromUser() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time = hourOfDay + ":" + minute;
                time = TimeUtils.getTimeWith0(time);
                selectedTimeTV.setText(time);
                timeToNextTV.setText(getTimeToAlarm());
            }
        };

        TimePickerDialog timePicker = new TimePickerDialog(getActivity(),
                t,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);
        timePicker.setTitle("");
        timePicker.show();
    }

    @Click(R.id.cancelIV)
    void cancel() {
        getActivity().finish();
    }

    @Click(R.id.day1TV)
    void setDay1TV() {
        configureDay(0, day1TV);
    }

    @Click(R.id.day2TV)
    void setDay2TV() {
        configureDay(1, day2TV);
    }

    @Click(R.id.day3TV)
    void setDay3TV() {
        configureDay(2, day3TV);
    }

    @Click(R.id.day4TV)
    void setDay4TV() {
        configureDay(3, day4TV);
    }

    @Click(R.id.day5TV)
    void setDay5TV() {
        configureDay(4, day5TV);
    }

    @Click(R.id.day6TV)
    void setDay6TV() {
        configureDay(5, day6TV);
    }

    @Click(R.id.day7TV)
    void setDay7TV() {
        configureDay(6, day7TV);
    }

    @Click(R.id.doneIV)
    void save() {

        if (alarmItem != null) {
            alarmItem.setEnabled(switchSB.isChecked());
            alarmItem.setColor(color);
            alarmItem.setText(titleET.getText().toString());
            alarmItem.setTime(time);
            setDaysToRepeat();

            new SetAlarmInteractor().execute(alarmItem, new RealmUtils.OnSuccessListener() {
                @Override
                public void onSuccess() {
                    getActivity().finish();
                    Toast.makeText(getActivity(), "Alarm updated...", Toast.LENGTH_LONG).show();
                }
            });

        } else {
            alarmItem = new AlarmItem(titleET.getText().toString(), color, time, switchSB.isChecked());
            setDaysToRepeat();

            new SetAlarmInteractor().executeWithId(alarmItem, new RealmUtils.OnSuccessListener() {
                @Override
                public void onSuccess() {
                    getActivity().finish();
                }
            });
        }
    }

    private void configureDay(int i, TextView textView) {
        if (days[i]) {
            days[i] = false;
            textView.setTextColor(colorDeactivatedDay);
        } else {
            days[i] = true;
            textView.setTextColor(colorActivatedDay);
        }
    }

    private void setDaysToRepeat() {
        for (int i = 0; i < 7; i++) {
            alarmItem.getIsEnabledRepeat().get(i).setEnabled(days[i]);
        }
    }

}
