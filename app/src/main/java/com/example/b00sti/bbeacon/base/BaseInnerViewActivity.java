package com.example.b00sti.bbeacon.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.utils.FragmentBuilder;
import com.example.b00sti.bbeacon.utils.FragmentSwitcher;
import com.example.b00sti.bbeacon.utils.FragmentSwitcherParams;
import com.example.b00sti.bbeacon.utils.ViewUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_inner_view)
public class BaseInnerViewActivity extends AppCompatActivity {
    private static final String TAG = "BaseInnerViewActivity";

    @ViewById(R.id.toolbar) public Toolbar toolbar;

    @Bean
    public FragmentBuilder fragmentBuilder;

    @AfterViews
    public void init() {
        setInitialFragment(getIntent());
        setActivityTitle();
        setHomeButton(true);
    }

    @Override
    public void onBackPressed() {
        ViewUtils.hideKeyboard(this);
        super.onBackPressed();
    }

    private void setActivityTitle() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("name");
        setTitle(title);
    }

    public void setHomeButton(boolean isActive) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(isActive);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return (true);
        }

        return super.onOptionsItemSelected(item);
    }

    public void setInitialFragment(Intent intent) {
        int fragmentId;
        if (intent.hasExtra(getString(R.string.bundle_fragment))) {
            fragmentId = intent.getIntExtra(getString(R.string.bundle_fragment), -1);
            switchToFragment(fragmentId, intent.getExtras());
        }
    }

    public Fragment setFragment(@FragmentBuilder.FragBuild int fragmentId) {
        return fragmentBuilder.newFragment(fragmentId);
    }

    private void switchToFragment(int fragmentId, @Nullable Bundle bundle) {
        Fragment fragment = setFragment(fragmentId);
        Log.d(TAG, "switchToFragment: " + fragment.getTag());

        if (bundle != null) {
            FragmentSwitcher.switchFragment(new FragmentSwitcherParams(getSupportFragmentManager(), fragment, R.id.activity_inner_view_placeholder, bundle));
        } else {
            FragmentSwitcher.switchFragment(new FragmentSwitcherParams(getSupportFragmentManager(), fragment, R.id.activity_inner_view_placeholder));
        }
    }
}
