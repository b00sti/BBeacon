package com.example.b00sti.bbeacon.navigation;

import android.content.Context;
import android.content.Intent;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseInnerViewActivity_;
import com.example.b00sti.bbeacon.utils.FragmentBuilder;

/**
 * Created by b00sti on 28.04.2017
 */

public class ActivityBuilder {

    public static void startDisableAlarmActivity(Context ctx, String id) {
        Intent intent = new Intent(ctx, BaseInnerViewActivity_.class);
        intent.putExtra(ctx.getString(R.string.bundle_fragment), FragmentBuilder.DISABLE_ALARM);
        intent.putExtra("id", id);
        ctx.startActivity(intent);

    }
}
