package com.example.b00sti.bbeacon.ui_weather.main;

import java.util.List;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

public class WeatherContract {

    public interface View {

        void refreshData(List<WeatherItem> items);

    }

    public interface Presenter {

        void fetchData();

    }
}
