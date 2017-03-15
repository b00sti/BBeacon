package com.example.b00sti.bbeacon.ui_others;

import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.ui_scanner.ScannerItem;
import com.example.b00sti.bbeacon.ui_scanner.SetScannerInteractor;
import com.example.b00sti.bbeacon.utils.RealmUtils;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.IntArrayRes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-14
 */

@EFragment(R.layout.add_new_beacon_fragment)
public class AddNewBeaconFragment extends Fragment {

    @ViewById(R.id.titleET) EditText titleET;
    @ViewById(R.id.rangeET) EditText rangeET;
    @ViewById(R.id.selectColorB) AppCompatButton selectColorB;
    @ViewById(R.id.enabledCB) CheckBox enabledCB;
    @ViewById(R.id.saveB) AppCompatButton saveB;

    @IntArrayRes(R.array.beaconColors)
    int colors[];

    @ColorRes(R.color.colorPrimaryDark)
    int color;

    public static AddNewBeaconFragment newInstance() {
        return new AddNewBeaconFragment_();
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
        List<ScannerItem> items = new ArrayList<ScannerItem>();
        int val = Integer.parseInt(rangeET.getText().toString());
        if (val < 0) {
            val = 0;
        } else if (val > 100) {
            val = 100;
        }
        items.add(new ScannerItem(titleET.getText().toString(), color, enabledCB.isChecked(), "Last visible: 21:41 19/02/17", val));

        new SetScannerInteractor().execute(items, new RealmUtils.OnSuccessListener() {
            @Override
            public void onSuccess() {
                getActivity().finish();
            }
        });
    }

}
