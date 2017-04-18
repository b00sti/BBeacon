package com.example.b00sti.bbeacon.ui_weather.main;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.Log;
import android.widget.TextView;

import com.example.b00sti.bbeacon.R;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DecimalFormat;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-11
 */

public class CustomMarkerView extends MarkerView {
    private static final String TAG = "CustomMarkerView";

    private TextView textTV;
    private Context context;

    public CustomMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        // this markerview only displays a textview
        this.context = context;
        textTV = (TextView) findViewById(R.id.textTV);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        try {
            float value = Float.parseFloat(new DecimalFormat("##.#").format(e.getY()));
            textTV.setText(String.valueOf(value)); // set the entry-value as the display text
        } catch (NumberFormatException e1) {
            Log.d(TAG, "refreshContent: Invalid parsing !");
        }

        super.refreshContent(e, highlight);
    }

    @Override
    public void setOffset(float offsetX, float offsetY) {
        super.setOffset(offsetX, offsetY);
    }

    @Override
    public MPPointF getOffset() {
        return super.getOffset();
    }

    @Override
    public void setOffset(MPPointF offset) {
        super.setOffset(offset);
    }

    @Override
    public Chart getChartView() {
        return super.getChartView();
    }

    @Override
    public void setChartView(Chart chart) {
        super.setChartView(chart);
    }

    @Override
    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
        return super.getOffsetForDrawingAtPoint(posX, posY);
    }

    @Override
    public void draw(Canvas canvas, float posX, float posY) {
        float posx;
        float posy;

        // take offsets into consideration
        float px = (int) (20 * Resources.getSystem().getDisplayMetrics().density);

        posx = posX - px;
        posy = posY;


        // AVOID RIGHT/LEFT OFFSCREEN
        if (posx < 0)
            posx = 0;

        // translate to the correct position and draw
        canvas.translate(posx, posy);
        draw(canvas);
        canvas.translate(-posx, -posy);
    }

}
