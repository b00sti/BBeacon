package com.example.b00sti.bbeacon.ui_weather.details;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BasePresenter;
import com.example.b00sti.bbeacon.navigation.NavigationNotificationEvent;
import com.example.b00sti.bbeacon.ui_weather.interactors.GetWeatherInteractor;
import com.example.b00sti.bbeacon.ui_weather.interactors.SetWeatherInteractor;
import com.example.b00sti.bbeacon.ui_weather.main.LineCardOne;
import com.example.b00sti.bbeacon.ui_weather.main.WeatherItem;
import com.example.b00sti.bbeacon.ui_weather.utils.NotificationHelper;
import com.example.b00sti.bbeacon.ui_weather.utils.WeatherConditionKind;
import com.example.b00sti.bbeacon.ui_weather.utils.WeatherConditionUtils;
import com.example.b00sti.bbeacon.ui_weather.utils.WeatherParameterKind;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.StringArrayRes;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-04-13
 */

@EBean
public class WeatherDetailsPresenter extends BasePresenter<WeatherDetailsContract.View> implements WeatherDetailsContract.Presenter {
    private static final String TAG = "WeatherDetailsPresenter";

    @RootContext
    Activity ctx;

    @ColorRes(R.color.colorPrimaryDark)
    int color;

    @StringArrayRes(R.array.notify_parameters)
    String[] notifyParameters;

    @StringArrayRes(R.array.notify_conditions)
    String[] notifyConditions;
    @Bean
    NotificationHelper notificationHelper;
    @Getter
    private WeatherItem weather;
    @WeatherParameterKind private String selectedParameter = "";
    @WeatherConditionKind private String selectedCondition = "";
    private double selectedValue;
    private EditText selectedValueET;


    @Override
    public void onSubscribe() {

    }

    @Override
    public void onUnsubscribe() {

    }

    @Override
    public void initUI(String id) {

        (new LineCardOne(view.getChartCardView1(), ctx, color, R.id.chart1)).init();
        (new LineCardOne(view.getChartCardView2(), ctx, color, R.id.chart2)).init();
        (new LineCardOne(view.getChartCardView3(), ctx, color, R.id.chart3)).init();

        addDisposable(new GetWeatherInteractor().execute(id).subscribe(weatherItem -> {
            weather = weatherItem;
            view.updateUI(weatherItem);
            notificationHelper.sendNotification(weatherItem);
        }));
    }

    @Override
    public String getNotificationConditionsText(WeatherItem weatherItem) {
        return WeatherConditionUtils.prepareNotificationConditionsText(weatherItem, ctx);
    }

    @Override
    public String getNotificationConditionsText() {
        return getNotificationConditionsText(weather);
    }

    // todo get from WeatherConditionUtils
    private List<String> prepareParametersList() {
        List<String> result = new ArrayList<String>();
        result.addAll(Arrays.asList(notifyParameters));
        return result;
    }

    private List<String> prepareConditionsList() {
        List<String> result = new ArrayList<String>();
        result.addAll(Arrays.asList(notifyConditions));
        return result;
    }

    public void getNotificationConditionsFromUser() {
        if (weather == null) {
            return;
        }

        MaterialDialog dialog = new MaterialDialog.Builder(ctx)
                .title(R.string.get_notify_conditions_from_user_title)
                .customView(R.layout.select_notify_conditions_dialog, true)
                .positiveText(android.R.string.ok)
                .onPositive((dialog1, which) -> onPositiveButtonOnNotificationConfig())
                .negativeText(android.R.string.cancel)
                .build();

        View dialogCustomView = dialog.getCustomView();
        if (dialogCustomView == null) {
            return;
        }
        dialog.show();

        List<String> parameters = prepareParametersList();
        List<String> conditions = prepareConditionsList();

        Spinner notifyParametersSpinner = (Spinner) dialogCustomView.findViewById(R.id.notifyParametersSpinner);
        notifyParametersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectedParameter = WeatherParameterKind.WEATHER_TEMPERATURE;
                } else if (position == 1) {
                    selectedParameter = WeatherParameterKind.WEATHER_PRESSURE;
                } else if (position == 2) {
                    selectedParameter = WeatherParameterKind.WEATHER_HUMIDITY;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> parametersAdapter = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_item, parameters);
        parametersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        notifyParametersSpinner.setAdapter(parametersAdapter);
        selectSpinnerInitialValue(notifyParametersSpinner, WeatherConditionUtils.getParameterName(weather.getConditionParameterKind(), ctx));

        Spinner notifyConditionsSpinner = (Spinner) dialogCustomView.findViewById(R.id.notifyConditionsSpinner);
        notifyConditionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectedCondition = WeatherConditionKind.LESS;
                } else if (position == 1) {
                    selectedCondition = WeatherConditionKind.MORE;
                } else if (position == 2) {
                    selectedCondition = WeatherConditionKind.EQUAL;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> conditionsAdapter = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_item, conditions);
        conditionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        notifyConditionsSpinner.setAdapter(conditionsAdapter);
        selectSpinnerInitialValue(notifyConditionsSpinner, WeatherConditionUtils.getConditionName(weather.getConditionKind(), ctx));

        selectedValueET = (EditText) dialogCustomView.findViewById(R.id.notifyValueET);

    }

    @Override
    public void onNotificationIconClick() {
        weather.setAlarm(false);
        view.showNotificationIcon(false);
    }

    private void onPositiveButtonOnNotificationConfig() {
        if (!isValidatedValues()) {
            return;
        }

        weather.setConditionParameterValue(selectedValue);

        if (!"".equals(selectedCondition)) {
            weather.setConditionKind(selectedCondition);
        }

        if (!"".equals(selectedParameter)) {
            weather.setConditionParameterKind(selectedParameter);
        }

        view.updateNotificationView(getNotificationConditionsText());

    }

    private boolean isValidatedValues() {
        if (selectedValueET != null) {
            try {
                selectedValue = Double.parseDouble(selectedValueET.getText().toString());
                return true;
            } catch (NumberFormatException e) {
                Toast.makeText(ctx, "Type a value and try again !", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            return false;
        }
    }

    private void selectSpinnerInitialValue(Spinner spinner, String text) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(text)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    @Override
    public void onAccept() {
        prepareWeatherToSaveInDatabase();
        SetWeatherInteractor.execute(weather);
        EventBus.getDefault().post(new NavigationNotificationEvent());
        ctx.finish();
    }

    @Override
    public void onCancel() {
        ctx.finish();
    }

    private void prepareWeatherToSaveInDatabase() {
        weather.setMessage(view.getNotificationMessage());
    }
}
