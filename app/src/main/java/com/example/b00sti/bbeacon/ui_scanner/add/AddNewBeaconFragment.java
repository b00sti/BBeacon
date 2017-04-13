package com.example.b00sti.bbeacon.ui_scanner.add;

import android.support.v7.widget.AppCompatButton;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseFragment;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-14
 */

@EFragment(R.layout.add_new_beacon_fragment)
public class AddNewBeaconFragment extends BaseFragment<AddNewBeaconPresenter> implements AddNewBeaconContract.View {

    @ViewById(R.id.titleET) EditText titleET;
    @ViewById(R.id.rangeET) EditText rangeET;
    @ViewById(R.id.selectBeaconTV) AppCompatButton selectColorB;
    @ViewById(R.id.enabledCB) CheckBox enabledCB;
    @ViewById(R.id.saveB) AppCompatButton saveB;

    @Bean
    AddNewBeaconPresenter addNewBeaconPresenter;

    public static AddNewBeaconFragment newInstance() {
        return new AddNewBeaconFragment_();
    }

    @Click(R.id.selectBeaconTV)
    void selectColor() {
        presenter.selectColor();
    }

    @Click(R.id.saveB)
    void save() {
        presenter.storageNewItem();
    }

    @Override
    protected AddNewBeaconPresenter registerPresenter() {
        addNewBeaconPresenter.attachView(this);
        return addNewBeaconPresenter;
    }

    @Override
    public boolean getEnableDisable() {
        return enabledCB.isChecked();
    }

    @Override
    public String getRange() {
        return rangeET.getText().toString();
    }

    @Override
    public String getTitle() {
        return titleET.getText().toString();
    }
}
