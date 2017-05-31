package com.example.b00sti.bbeacon.ui_weather.pollution;

import android.util.Log;
import android.widget.TextView;

import com.example.b00sti.bbeacon.MainActivity;
import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseFragment;
import com.example.b00sti.bbeacon.base.OnAnimationToolbar;
import com.example.b00sti.bbeacon.ui_weather.top.WeatherFromOWMRealm;
import com.example.b00sti.bbeacon.ui_weather.top.WeatherUtils;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.b00sti.bbeacon.ui_weather.top.WeatherUtils.getFormattedTemp;

/**
 * Created by b00sti on 20.03.2017
 */
//todo clean up
@EFragment(R.layout.pollution_fragment)
public class PollutionFragment extends BaseFragment<PollutionPresenter> implements PollutionContract.View, OnAnimationToolbar {
    public static final String TAG = "WeatherTopFragment";

    @ViewById(R.id.circleToolbarIconIV) CircleImageView circleImageView;
    @ViewById(R.id.tempValueTV) TextView tempValueTV;
    @ViewById(R.id.windTV) TextView windTV;
    @ViewById(R.id.humidityTV) TextView humidityTV;
    @ViewById(R.id.pressureTV) TextView pressureTV;
    @ViewById(R.id.titlePlaceTV) TextView titlePlaceTV;
    @ViewById(R.id.nameTV) TextView nameTV;
    @ViewById(R.id.timeTV) TextView timeTV;
    @ViewById(R.id.aqiTV) TextView aqiTV;
    @ViewById(R.id.aqiShortDeascTV) TextView aqiShortDeascTV;
    @ViewById(R.id.aqiLongDeascTV) TextView aqiLongDeascTV;
    @ViewById(R.id.pm25TV) TextView pm25TV;
    @ViewById(R.id.pm10TV) TextView pm10TV;
    @ViewById(R.id.no2TV) TextView no2TV;
    @ViewById(R.id.coTV) TextView coTV;
    @ViewById(R.id.backTV) TextView backTV;


    @Bean
    PollutionPresenter pollutionPresenter;

    String expandedTitle = "";
    String collapsedTitle = "";

    public static PollutionFragment newInstance() {
        return new PollutionFragment_();
    }

    @Override
    protected PollutionPresenter registerPresenter() {
        pollutionPresenter.attachView(this);
        return pollutionPresenter;
    }

    public void onStart() {
        presenter.initLocation();
        super.onStart();
    }

    public void onStop() {
        presenter.stopLocation();
        super.onStop();
    }

    @AfterViews
    void initUI() {
        presenter.initViews();
    }

    @Override
    public String setCollapsedTitleLayout() {
        refreshToolbar(collapsedTitle);
        return collapsedTitle;
    }

    @Override
    public String setExpandedTitleLayout() {
        refreshToolbar(expandedTitle);
        return expandedTitle;
    }

    public void refreshToolbar(String title) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setTitleToToolbar(title);
        }
    }

    @Click(R.id.backTV)
    void onBackClick() {
        getActivity().finish();
    }

    @Override
    public void refreshWeatherViews(WeatherFromOWMRealm weatherFromOWMRealm) {
        if (tempValueTV != null) {
            tempValueTV.setText(getFormattedTemp(weatherFromOWMRealm.getTemp()));
        }

        if (pressureTV != null) {
            pressureTV.setText(WeatherUtils.getFormattedPressure(getContext(), weatherFromOWMRealm.getPressure()));
        }

        if (humidityTV != null) {
            humidityTV.setText(WeatherUtils.getFormattedHumidity(getContext(), weatherFromOWMRealm.getHumidity()));
        }

        if (windTV != null) {
            windTV.setText(WeatherUtils.getFormattedWind(getContext(), weatherFromOWMRealm.getWind()));
        }

        if (titlePlaceTV != null) {
            titlePlaceTV.setText(weatherFromOWMRealm.getName());
        }

        refreshToolbar(weatherFromOWMRealm.getName());

        expandedTitle = weatherFromOWMRealm.getName();
        collapsedTitle = weatherFromOWMRealm.getName() + "    " + getFormattedTemp(weatherFromOWMRealm.getTemp()) + " \u2103";

        if (weatherFromOWMRealm.getIcon() != null) {
            String iconUrl = String.format(getString(R.string.openweathermap_icon_url), weatherFromOWMRealm.getIcon());
            Picasso.with(getContext()).load(iconUrl).into(circleImageView);
        } else {
            Log.i(TAG, "accept: image not available");
        }
    }

    @Override
    public void refreshPollutionViews(PollutionRealm pollutionRealm) {
        Log.d(TAG, "refreshPollutionViews: " + pollutionRealm.toString());

        nameTV.setText(pollutionRealm.getName());
        timeTV.setText(pollutionRealm.getTime());
        aqiTV.setText("" + pollutionRealm.getAqi());
        pm25TV.setText(pollutionRealm.getPm25() + "");
        pm10TV.setText(pollutionRealm.getPm10() + "");
        no2TV.setText(pollutionRealm.getNo2() + "");
        coTV.setText(pollutionRealm.getCo() + "");
    }
}
