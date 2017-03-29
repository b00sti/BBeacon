package com.example.b00sti.bbeacon.ui_alarm.add;

import android.app.TimePickerDialog;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.ui_alarm.interactors.SetAlarmInteractor;
import com.example.b00sti.bbeacon.ui_alarm.main.AlarmItem;
import com.example.b00sti.bbeacon.utils.RealmUtils;
import com.example.b00sti.bbeacon.utils.SwitchButton;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.IntArrayRes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.grantland.widget.AutofitTextView;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-14
 */

@EFragment(R.layout.add_new_alarm_fragment)
public class AddNewAlarmFragment extends Fragment {

    @ViewById(R.id.titleET) EditText titleET;
    @ViewById(R.id.switchSB) SwitchButton switchSB;
    @ViewById(R.id.selectedTimeTV) AutofitTextView selectedTimeTV;

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
                selectedTimeTV.setText(time);
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
