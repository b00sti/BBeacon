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
import android.widget.FrameLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.example.b00sti.bbeacon.base.BaseRefreshableFragment;
import com.example.b00sti.bbeacon.navigation.NavigationManager;
import com.example.b00sti.bbeacon.navigation.NotificationManager;
import com.example.b00sti.bbeacon.ui_alarm.NotificationEvent;
import com.example.b00sti.bbeacon.ui_alarm.interactors.GetAlarmInteractor;
import com.example.b00sti.bbeacon.ui_alarm.main.AlarmItem;
import com.example.b00sti.bbeacon.ui_scanner.GetScannerInteractor;
import com.example.b00sti.bbeacon.ui_scanner.ScannerItem;
import com.example.b00sti.bbeacon.ui_weather.main.OnAnimationToolbar;
import com.example.b00sti.bbeacon.utils.FragmentBuilder;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
    private static final String TAG = "MainActivity";

    @ViewById(R.id.mainViewPager) public AHBottomNavigationViewPager viewPager;
    @ViewById(R.id.bottomNavigation) public AHBottomNavigation bottomNavigation;
    @ViewById(R.id.mainFAB) public FloatingActionButton floatingActionButton;
    @ViewById(R.id.activityTopPlaceholderFL) public FrameLayout frameLayout;
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
                setNotifications();
                if (position == 2) {
                    dragInAppBar(false);
                } else {
                    dragInAppBar(true);
                }
            }
        });

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
    public void onMessageEvent(NotificationEvent event) {
        refreshAllViews();
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
        refreshTopFragment(bottomNavigation.getCurrentItem());
        setNotifications();

        if (navigationManager.getCurrentFragment() != null) {
            navigationManager.getCurrentFragment().refreshToolbar();
        }

        if (withMainFragment) {
            navigationManager.refreshCurrentFragment();
        }
    }

    public void refreshAllViews() {
        refreshAllViews(false);
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
        transaction.replace(R.id.activityTopPlaceholderFL, topFragment);
        transaction.commit();
    }

    private void refreshTopFragment(int currentTab) {
        if(topFragment instanceof BaseRefreshableFragment) {
         ((BaseRefreshableFragment) topFragment).refresh();
        }
    }

    public void configureToolbar(String titleL) {
        collapsedTitleL.setTitle(titleL);
    }

    public void setNotifications() {
        Observable.zip(
                new GetAlarmInteractor().execute(),
                new GetScannerInteractor().execute(),
                new BiFunction<List<AlarmItem>, List<ScannerItem>, ArrayList<Pair<AHNotification, Integer>>>() {
                    @Override
                    public ArrayList<Pair<AHNotification, Integer>> apply(List<AlarmItem> alarmItems, List<ScannerItem> scannerItems) throws Exception {

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
                            notifications.add(new Pair<>(NotificationManager.newDefault(getApplicationContext(), String.valueOf(Integer.valueOf(sizeAlarms))), 0));
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
                            notifications.add(new Pair<>(NotificationManager.newDefault(getApplicationContext(), String.valueOf(Integer.valueOf(sizeScanner))), 2));
                        }

                        notifications.add(new Pair<>(NotificationManager.newDefault(getApplicationContext(), String.valueOf(Integer.valueOf(2))), 1));

                        return notifications;
                    }
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

            }

            @Override
            public void onComplete() {

            }
        };
    }

    /**
     * Add or remove items of the bottom navigation
     */
    public void updateBottomNavigationItems(@Nullable ArrayList<Pair<AHNotification, Integer>> notifications) {
        navigationManager.updateBottomNavigationItems(notifications);
    }

    /**
     * Show or hide the bottom navigation with animation
     */
    public void showOrHideBottomNavigation(boolean show) {
        navigationManager.showOrHideBottomNavigation(show);
    }

    /**
     * Show or hide selected item background
     */
    public void updateSelectedBackgroundVisibility(boolean isVisible) {
        navigationManager.updateSelectedBackgroundVisibility(isVisible);
    }

    /**
     * Show or hide selected item background
     */
    public void setForceTitleHide(boolean forceTitleHide) {
        navigationManager.setForceTitleHide(forceTitleHide);
    }

    /**
     * Return the number of items in the bottom navigation
     */
    public int getBottomNavigationNbItems() {
        return navigationManager.getBottomNavigationNbItems();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

        Fragment fragment = navigationManager.getCurrentFragment();
        if (fragment instanceof OnAnimationToolbar) {
            if (verticalOffset == 0) {
                ((OnAnimationToolbar) fragment).setCollapsedTitleLayout("Krowodrza");
                collapsedTitleL.setTitle("Krowodrza");
            } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                ((OnAnimationToolbar) fragment).setCollapsedTitleLayout("Krowodrza    17 \u2103");
            } else {
                ((OnAnimationToolbar) fragment).setCollapsedTitleLayout("Krowodrza");
            }
        }

    }

}
