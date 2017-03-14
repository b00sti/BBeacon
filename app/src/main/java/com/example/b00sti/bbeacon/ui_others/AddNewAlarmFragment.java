package com.example.b00sti.bbeacon.ui_others;

import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.ui_alarm.AlarmItem;
import com.example.b00sti.bbeacon.ui_alarm.SetAlarmInteractor;
import com.example.b00sti.bbeacon.utils.RealmUtils;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.IntArrayRes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-14
 */

@EFragment(R.layout.add_new_alarm_fragment)
public class AddNewAlarmFragment extends Fragment {

    @ViewById(R.id.titleET) EditText titleET;
    @ViewById(R.id.selectColorB) AppCompatButton selectColorB;
    @ViewById(R.id.enabledCB) CheckBox enabledCB;
    @ViewById(R.id.saveB) AppCompatButton saveB;

    @IntArrayRes(R.array.beaconColors)
    int colors[];

    @ColorRes(R.color.colorPrimaryDark)
    int color;

    public static AddNewAlarmFragment newInstance() {
        return new AddNewAlarmFragment_();
    }

    @Click(R.id.selectColorB)
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

    @Click(R.id.saveB)
    void save() {
        List<AlarmItem> items = new ArrayList<AlarmItem>();
        String time = "" + new Random().nextInt(24) + ":" + (new Random().nextInt(51) + 10);
        items.add(new AlarmItem(titleET.getText().toString(), enabledCB.isChecked(), color, time));

        new SetAlarmInteractor().execute(items, new RealmUtils.OnSuccessListener() {
            @Override
            public void onSuccess() {
                getActivity().finish();
            }
        });
    }

}
