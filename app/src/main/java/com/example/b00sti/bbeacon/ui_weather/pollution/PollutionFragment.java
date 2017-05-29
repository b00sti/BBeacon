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
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.b00sti.bbeacon.ui_weather.top.WeatherUtils.getFormattedTemp;

/**
 * Created by b00sti on 20.03.2017.
 */
@EFragment(R.layout.pollution_fragment)
public class PollutionFragment extends BaseFragment<PollutionPresenter> implements PollutionContract.View, OnAnimationToolbar {
    public static final String TAG = "WeatherTopFragment";

    @ViewById(R.id.circleToolbarIconIV) CircleImageView circleImageView;
    @ViewById(R.id.tempValueTV) TextView tempValueTV;
    @ViewById(R.id.windTV) TextView windTV;
    @ViewById(R.id.humidityTV) TextView humidityTV;
    @ViewById(R.id.pressureTV) TextView pressureTV;

    @Bean
    PollutionPresenter weatherTopPresenter;

    String expandedTitle = "";
    String collapsedTitle = "";

    public static PollutionFragment newInstance() {
        return new PollutionFragment_();
    }

    @Override
    protected PollutionPresenter registerPresenter() {
        weatherTopPresenter.attachView(this);
        return weatherTopPresenter;
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


    @Override
    public void refreshViews(WeatherFromOWMRealm weatherFromOWMRealm) {
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
}
