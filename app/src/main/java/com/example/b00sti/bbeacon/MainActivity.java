package com.example.b00sti.bbeacon;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.example.b00sti.bbeacon.base.OnAnimationToolbar;
import com.example.b00sti.bbeacon.base.RefreshableFragment;
import com.example.b00sti.bbeacon.base.RefreshableFragmentWithToolbar;
import com.example.b00sti.bbeacon.navigation.NavigationManager;
import com.example.b00sti.bbeacon.navigation.NavigationNotificationEvent;
import com.example.b00sti.bbeacon.navigation.NavigationNotificationManager;
import com.example.b00sti.bbeacon.ui_alarm.interactors.GetAlarmInteractor;
import com.example.b00sti.bbeacon.ui_alarm.main.AlarmItem;
import com.example.b00sti.bbeacon.ui_scanner.interactors.GetScannerInteractor;
import com.example.b00sti.bbeacon.ui_scanner.main.ScannerItem;
import com.example.b00sti.bbeacon.ui_weather.interactors.GetWeatherInteractor;
import com.example.b00sti.bbeacon.ui_weather.main.WeatherItem;
import com.example.b00sti.bbeacon.utils.FragmentBuilder;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
    private static final String TAG = "MainActivity";

    @ViewById(R.id.mainViewPagerVP) public AHBottomNavigationViewPager viewPager;
    @ViewById(R.id.bottomNavigation) public AHBottomNavigation bottomNavigation;
    @ViewById(R.id.mainFAB) public FloatingActionButton mainFAB;
    @ViewById(R.id.activityTopPlaceholderVG) public ViewGroup activityTopPlaceholderVG;
    @ViewById(R.id.collapsedTitleL) public CollapsingToolbarLayout collapsedTitleL;
    @ViewById(R.id.appBarL) public AppBarLayout appBarLayout;
    @ViewById(R.id.toolbar) public Toolbar toolbar;

    @Bean
    NavigationManager navigationManager;

    @Bean
    FragmentBuilder fragmentBuilder;

    Fragment topFragment;

    @AfterViews
    void init() {
        appBarLayout.addOnOffsetChangedListener(this);

        setupTopFragment(0);
        setNotifications();

        navigationManager.initUI(new NavigationManager.AHonTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                setupTopFragment(position);
                handleDraggingInAppBar(position);
                setNotifications();
            }
        });

    }

    private void handleDraggingInAppBar(int currentTab) {
        if (currentTab == 2) {
            dragInAppBar(false);
        } else {
            dragInAppBar(true);
        }
    }

    private void dragInAppBar(final boolean drag) {
        if (appBarLayout != null) {
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
            AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
            behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                @Override
                public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                    return drag;
                }
            });
            params.setBehavior(behavior);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NavigationNotificationEvent event) {
        refreshAllViews(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshAllViews(true);
    }

    public void refreshAllViews(boolean withMainFragment) {
        refreshTopFragment();
        setNotifications();

        Fragment fragment = navigationManager.getCurrentFragment();

        if (fragment != null && fragment instanceof RefreshableFragmentWithToolbar) {
            ((RefreshableFragmentWithToolbar) fragment).refreshToolbar();
        }

        if (withMainFragment) {
            navigationManager.refreshCurrentFragment();
        }
    }

    private void setupTopFragment(int currentTab) {
        if (currentTab == 0) {
            topFragment = fragmentBuilder.newFragment(FragmentBuilder.TOP_ALARM);
        } else if (currentTab == 1) {
            topFragment = fragmentBuilder.newFragment(FragmentBuilder.TOP_WEATHER);
        } else if (currentTab == 2) {
            topFragment = fragmentBuilder.newFragment(FragmentBuilder.TOP_SCANNER);
        } else {
            topFragment = fragmentBuilder.newFragment(FragmentBuilder.EMPTY);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activityTopPlaceholderVG, topFragment);
        transaction.commit();
    }

    private void refreshTopFragment() {
        if (topFragment instanceof RefreshableFragment) {
            ((RefreshableFragment) topFragment).refresh();
        }
    }

    public void setTitleToToolbar(String titleL) {
        collapsedTitleL.setTitle(titleL);
    }

    public void setNotifications() {
        Observable.zip(
                new GetAlarmInteractor().execute(),
                new GetScannerInteractor().execute(),
                new GetWeatherInteractor().execute(),
                (alarmItems, scannerItems, weatherItems) -> {

                    ArrayList<Pair<AHNotification, Integer>> notifications = new ArrayList<>();
                    int sizeAlarms = 0;
                    for (AlarmItem alarmItem : alarmItems) {
                        if (alarmItem.isEnabled()) {
                            sizeAlarms++;
                        }
                    }

                    if (sizeAlarms == 0) {
                        notifications.add(new Pair<>(new AHNotification(), 0));
                    } else {
                        notifications.add(new Pair<>(NavigationNotificationManager.newDefault(getApplicationContext(), String.valueOf(Integer.valueOf(sizeAlarms))), 0));
                    }

                    int sizeScanner = 0;
                    for (ScannerItem scanner : scannerItems) {
                        if (scanner.isEnabled()) {
                            sizeScanner++;
                        }
                    }

                    if (sizeScanner == 0) {
                        notifications.add(new Pair<>(new AHNotification(), 2));
                    } else {
                        notifications.add(new Pair<>(NavigationNotificationManager.newDefault(getApplicationContext(), String.valueOf(Integer.valueOf(sizeScanner))), 2));
                    }

                    int sizeWeather = 0;
                    for (WeatherItem weatherItem : weatherItems) {
                        if (weatherItem.isAlarm()) {
                            sizeWeather++;
                        }
                    }

                    if (sizeWeather == 0) {
                        notifications.add(new Pair<>(new AHNotification(), 1));
                    } else {
                        notifications.add(new Pair<>(NavigationNotificationManager.newDefault(getApplicationContext(), String.valueOf(Integer.valueOf(sizeWeather))), 1));
                    }

                    return notifications;
                }
        ).take(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(notificationObserver());
    }

    private Observer<ArrayList<Pair<AHNotification, Integer>>> notificationObserver() {
        return new DefaultObserver<ArrayList<Pair<AHNotification, Integer>>>() {
            @Override
            public void onNext(ArrayList<Pair<AHNotification, Integer>> value) {
                updateBottomNavigationItems(value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
    }

    public void updateBottomNavigationItems(@Nullable ArrayList<Pair<AHNotification, Integer>> notifications) {
        navigationManager.updateBottomNavigationItems(notifications);
    }

    public void showOrHideBottomNavigation(boolean show) {
        navigationManager.showOrHideBottomNavigation(show);
    }

    public void updateSelectedBackgroundVisibility(boolean isVisible) {
        navigationManager.updateSelectedBackgroundVisibility(isVisible);
    }

    public void setForceTitleHide(boolean forceTitleHide) {
        navigationManager.setForceTitleHide(forceTitleHide);
    }

    public int getBottomNavigationNbItems() {
        return navigationManager.getBottomNavigationNbItems();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //handle animated visibility changes on toolbar during collapsing
        if (topFragment instanceof OnAnimationToolbar) {
            if (verticalOffset == 0) {
                collapsedTitleL.setTitle(((OnAnimationToolbar) topFragment).setExpandedTitleLayout());
            } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                ((OnAnimationToolbar) topFragment).setCollapsedTitleLayout();
            } else {
                ((OnAnimationToolbar) topFragment).setExpandedTitleLayout();
            }
        }

    }

}
