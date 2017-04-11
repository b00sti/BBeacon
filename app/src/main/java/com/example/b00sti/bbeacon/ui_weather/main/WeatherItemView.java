package com.example.b00sti.bbeacon.ui_weather.main;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseItemView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.StackedValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.IntArrayRes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

@EViewGroup(R.layout.weather_item_view)
public class WeatherItemView extends BaseItemView<WeatherItem> {

    @ViewById(R.id.layout_item_demo_title) TextView textView;
    @ViewById(R.id.sidebar) View sidebar;
    @ViewById(R.id.moreIV) ImageView moreIV;
    @ViewById(R.id.topLayoutLL) ViewGroup topLL;
    @ViewById(R.id.tempValueTV) TextView tempTV;
    @ViewById(R.id.messageTV) TextView messageTV;
    @ViewById(R.id.humidityTV) TextView textView2;
    @ViewById(R.id.pressureTV) TextView textView1;
    @ViewById(R.id.card_view) CardView card_view;
    @ViewById(R.id.chart1) LineChart mChart;

    @ColorRes(R.color.colorAccent)
    int accentColor;

    @IntArrayRes(R.array.beaconColors)
    int colors[];

    @DrawableRes(R.drawable.ic_description_grey_900_24dp)
    Drawable desc;

    @DrawableRes(R.drawable.ic_priority_high_red_900_24dp)
    Drawable alarm;

    Context context;


    public WeatherItemView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void bind(WeatherItem weatherItem) {
        textView.setText(weatherItem.getTitle());
        int color = weatherItem.getColor();
        topLL.setBackgroundColor(color);
        sidebar.setBackgroundColor(color);
        tempTV.setText(weatherItem.getTemp() + " ℃");
        messageTV.setText(weatherItem.getMessage());
        textView1.setText(weatherItem.getPressure());
        textView2.setText(weatherItem.getHumidity());
        if (weatherItem.isAlarm()) {
            messageTV.setCompoundDrawablesWithIntrinsicBounds(desc, null, alarm, null);
        } else {
            messageTV.setCompoundDrawablesWithIntrinsicBounds(desc, null, null, null);
        }

        handleExampleChart(color);
    }

    void handleExampleChart(int color) {
        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setViewPortOffsets(0f, 0f, 0f, 0f);

        // add data
        setData(12, 25, color);
        mChart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();
        l.setEnabled(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.rgb(255, 192, 56));
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f); // one hour
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm");

            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                long millis = TimeUnit.HOURS.toMillis((long) value);
                return mFormat.format(new Date(millis));
            }
        });

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setTypeface(mTfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularityEnabled(false);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(40f);
        CustomMarkerView markerView = new CustomMarkerView(context, R.layout.marker);
        //markerView.setOffset(-markerView.getMeasuredWidth() / 2, - markerView.getMeasuredHeight());
        leftAxis.setYOffset(-10f);
        leftAxis.setTextColor(Color.rgb(255, 192, 56));
        leftAxis.setEnabled(false);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
        mChart.setMarker(markerView);
        mChart.setDrawMarkers(true);
        mChart.animateY(500);


    }

    private void setData(int count, float range, int color) {

        // now in hours
        long now = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis());

        ArrayList<Entry> values = new ArrayList<Entry>();

        float from = now;

        // count = hours
        float to = now + count;

        // increment by 1 hour
        int i = 0;
        float maxY = 0;
        int indexMaxY = 0;

        for (float x = from; x < to; x++) {

            float y = getRandom(range, 10);
            if (y > maxY) {
                indexMaxY = i;
                maxY = y;
            }
            values.add(new Entry(x, y)); // add one entry per hour

            i++;
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(values, "DataSet 1");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setColor(accentColor);
        set1.setValueTextColor(accentColor);
        set1.setLineWidth(0f);
        set1.setDrawCircles(true);
        set1.setCircleColor(accentColor);
        set1.setDrawValues(false);
        set1.setDrawFilled(true);
        set1.setFillAlpha(255);
        set1.setFillColor(color);
        set1.setCircleRadius(5f);
        set1.setValueFormatter(new StackedValueFormatter(false, "", 1));
        set1.setDrawCircleHole(false);
        set1.enableDashedHighlightLine(5f, 5f, 5f);
        set1.setDrawHorizontalHighlightIndicator(false);
        set1.setDrawVerticalHighlightIndicator(false);
        set1.setHighLightColor(accentColor);

        // create a data object with the datasets
        LineData data = new LineData(set1);
        data.setValueTextColor(accentColor);
        data.setValueTextSize(10f);


        // set data
        mChart.setData(data);
        mChart.highlightValue(values.get(indexMaxY).getX(), 0);
    }

    protected float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }
}
