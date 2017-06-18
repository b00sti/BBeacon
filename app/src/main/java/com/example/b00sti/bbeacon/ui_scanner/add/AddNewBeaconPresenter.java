package com.example.b00sti.bbeacon.ui_scanner.add;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BasePresenter;
import com.example.b00sti.bbeacon.ui_scanner.interactors.SetScannerInteractor;
import com.example.b00sti.bbeacon.ui_scanner.main.ScannerItem;
import com.example.b00sti.bbeacon.utils.RealmUtils;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.IntArrayRes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-13
 */

@EBean
public class AddNewBeaconPresenter extends BasePresenter<AddNewBeaconContract.View> implements AddNewBeaconContract.Presenter {

    @RootContext
    Activity ctx;

    @IntArrayRes(R.array.beaconColors)
    int colors[];

    @ColorRes(R.color.colorPrimaryDark)
    int color;

    @Override
    public void onSubscribe() {

    }

    @Override
    public void onUnsubscribe() {

    }

    @Override
    public void storageNewItem() {
        //generate sample fake data
        List<ScannerItem> items = new ArrayList<ScannerItem>();
        String s = view.getRange();
        int val;
        if (s.length() > 0) {
            val = Integer.parseInt(s);
        } else {
            val = 66;
        }
        if (val < 0) {
            val = 0;
        } else if (val > 100) {
            val = 100;
        }

        double rand = new Random().nextDouble();
        Log.d("tak", "storageNewItem: " + val + " / " + s + " / " + s.length());

        ScannerItem scannerItem = new ScannerItem();
        scannerItem.setId(view.getTitle().hashCode() + rand + "");
        scannerItem.setTitle(view.getTitle());
        scannerItem.setColor(color);
        scannerItem.setEnabled(view.getEnableDisable());
        scannerItem.setLastVisible(String.format(ctx.getString(R.string.last_visible_at)));
        scannerItem.setRange(val);

        items.add(scannerItem);

        new SetScannerInteractor().execute(items, new RealmUtils.OnSuccessListener() {
            @Override
            public void onSuccess() {
                ctx.finish();
            }
        });
    }

    @Override
    public void selectColor() {
        ArrayList<String> strings = new ArrayList<>();
        for (int i : colors) {
            strings.add("Color: " + i);
        }

        new MaterialDialog.Builder(ctx)
                .title(ctx.getString(R.string.select_color))
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
                .positiveText(android.R.string.ok)
                .show();
    }
}
