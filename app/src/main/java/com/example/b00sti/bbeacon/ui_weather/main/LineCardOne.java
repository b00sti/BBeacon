package com.example.b00sti.bbeacon.ui_weather.main;

import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.utils.chart.Tools;
import com.example.b00sti.bbeacon.utils.chart.animation.Animation;
import com.example.b00sti.bbeacon.utils.chart.model.LineSet;
import com.example.b00sti.bbeacon.utils.chart.renderer.AxisRenderer;
import com.example.b00sti.bbeacon.utils.chart.tooltip.Tooltip;
import com.example.b00sti.bbeacon.utils.chart.view.LineChartView;

import java.util.Random;

import lombok.Getter;


//test chart
//example data
public class LineCardOne {
    private static final String TAG = "LineCardOne";


    @Getter
    private final LineChartView mChart;

    private final Context mContext;


    private final String[] mLabels = {"Jan", "Fev", "Mar", "Apr", "Jun", "May", "Jul", "Aug", "Sep"};

    private final float[][] mValues = {{3.5f, 4.7f, 4.3f, 8f, 6.5f, 9.9f, 7f, 8.3f, 7.0f},
            {4.5f, 2.5f, 2.5f, 9f, 4.5f, 9.5f, 5f, 8.3f, 1.8f}};

    private Tooltip mTip;

    private int color;


    public LineCardOne(CardView card, Context context, int color) {

        mContext = context;
        this.color = color;
        for (int x = 0; x < mValues[0].length; x++) {
            float a = Math.round(new Random().nextFloat() * 200 + 100.f);
            mValues[0][x] = a / 10;
        }
        mChart = (LineChartView) card.findViewById(R.id.chart1);
    }

    public LineCardOne(CardView card, Context context, int color, int idChart) {

        mContext = context;
        this.color = color;
        for (int x = 0; x < mValues[0].length; x++) {
            float a = Math.round(new Random().nextFloat() * 200 + 100.f);
            mValues[0][x] = a / 10;
        }
        mChart = (LineChartView) card.findViewById(idChart);
    }

    public void init() {
        show();
    }

    public void show() {

        // Tooltip
        mTip = new Tooltip(mContext, R.layout.linechart_three_tooltip, R.id.value);

        ((TextView) mTip.findViewById(R.id.value)).setTypeface(
                Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Semibold.ttf"));

        mTip.setVerticalAlignment(Tooltip.Alignment.BOTTOM_TOP);
        mTip.setDimensions((int) Tools.fromDpToPx(58), (int) Tools.fromDpToPx(25));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

            mTip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 1f)).setDuration(200);

            mTip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 0f)).setDuration(200);

            mTip.setPivotX(Tools.fromDpToPx(65) / 2);
            mTip.setPivotY(Tools.fromDpToPx(25));
        }

        mChart.setTooltips(mTip);

        // Data
        LineSet dataset = new LineSet(mLabels, mValues[0]);
        dataset.setColor(Color.parseColor("#3F51B5"))
                .setFill(color)
                .setDotsColor(Color.parseColor("#4CAF50"))
                .setThickness(4)
                //.setDashed(new float[] {10f, 10f})
                .beginAt(5);
        mChart.addData(dataset);

        dataset = new LineSet(mLabels, mValues[0]);
        dataset.setColor(Color.parseColor("#3F51B5"))
                .setFill(color)
                .setDotsColor(Color.parseColor("#4CAF50"))
                .setThickness(4)
                .endAt(6);
        mChart.addData(dataset);

        // Chart
        mChart.setBorderSpacing(Tools.fromDpToPx(15))
                .setAxisBorderValues(0, 55)
                .setYLabels(AxisRenderer.LabelPosition.NONE)
                .setLabelsColor(Color.parseColor("#1A237E"))
                .setXAxis(false)
                .setYAxis(false);

        Log.d(TAG, "show: " + dataset.size());

/*        Runnable chartAction = new Runnable() {
            @Override
            public void run() {

                int i = 0;
                float max = 0.f;
                for (int j = 0; j < mValues[0].length; j++) {
                    if (mValues[0][j] > max) {
                        max = mValues[0][j];
                        i = j;
                    }
                }

                mTip.prepare(mChart.getEntriesArea(0).get(i), mValues[0][i]);

                mChart.showTooltip(mTip, true);
            }
        };*/

        int i = 0;
        float max = 0.f;
        for (int j = 0; j < mValues[0].length; j++) {
            if (mValues[0][j] > max) {
                max = mValues[0][j];
                i = j;
            }
        }


        //Animation anim = new Animation().setEasing(new BounceInterpolator()).setEndAction(chartAction);

        mChart.show();

        //mTip.prepare(mChart.getEntriesArea(0).get(i), mValues[0][i]);

        //mChart.showTooltip(mTip, true);

    }


    public void update(boolean firstStage) {

        mChart.dismissAllTooltips();
        if (firstStage) {
            mChart.updateValues(0, mValues[1]);
            mChart.updateValues(1, mValues[1]);
        } else {
            mChart.updateValues(0, mValues[0]);
            mChart.updateValues(1, mValues[0]);
        }
        //mChart.getChartAnimation().setEndAction(mBaseAction);
        mChart.notifyDataUpdate();
    }


    public void dismiss(Animation animation) {

        mChart.dismissAllTooltips();
        mChart.dismiss(animation);

        //mChart.dismiss(new Animation().setEasing(new BounceInterpolator()).setEndAction(action));
    }

}
