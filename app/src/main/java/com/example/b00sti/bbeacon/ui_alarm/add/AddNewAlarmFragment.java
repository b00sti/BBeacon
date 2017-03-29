package com.example.b00sti.bbeacon.ui_alarm.add;

import android.app.TimePickerDialog;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.b00sti.bbeacon.R;
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
import java.util.List;
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

    /*
    @ViewById(R.id.selectBeaconTV) AppCompatButton selectColorB;
    @ViewById(R.id.selectedTimeTV) TextView selectedTimeTV;
    @ViewById(R.id.doneIV) ImageView doneIV;
    */

    @IntArrayRes(R.array.beaconColors)
    int colors[];

    @ColorRes(R.color.colorPrimaryDark)
    int color;

    String time;

    public static AddNewAlarmFragment newInstance() {
        return new AddNewAlarmFragment_();
    }

    @AfterViews
    void initUI() {
        time = getCurrentTime();
        switchSB.setChecked(true);
        selectedTimeTV.setText(time);
        timeToNextTV.setText(getTimeToAlarm());
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

        return TimeUtils.getTimeWith0(dateFormat.format(c.getTime()));
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
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time = hourOfDay + ":" + minute;
                selectedTimeTV.setText(TimeUtils.getTimeWith0(time));
                timeToNextTV.setText(getTimeToAlarm());
            }
        };

        new TimePickerDialog(getActivity(),
                t,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true)
                .show();
    }

    @Click(R.id.cancelIV)
    void cancel() {
        getActivity().finish();
    }

    @Click(R.id.doneIV)
    void save() {
        List<AlarmItem> items = new ArrayList<AlarmItem>();
        items.add(new AlarmItem(titleET.getText().toString(), switchSB.isChecked(), color, time));

        new SetAlarmInteractor().execute(items, new RealmUtils.OnSuccessListener() {
            @Override
            public void onSuccess() {
                getActivity().finish();
            }
        });
    }

}
