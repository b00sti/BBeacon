package com.example.b00sti.bbeacon.utils.chart.animation;

import com.example.b00sti.bbeacon.utils.chart.model.ChartSet;

import java.util.ArrayList;

public interface ChartAnimationListener {

    boolean onAnimationUpdate(ArrayList<ChartSet> data);
}
