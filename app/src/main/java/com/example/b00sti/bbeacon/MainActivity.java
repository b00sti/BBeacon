package com.example.b00sti.bbeacon;

import android.graphics.Color;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.example.b00sti.bbeacon.navigation.NavigationManager;
import com.example.b00sti.bbeacon.navigation.NotificationManager;
import com.example.b00sti.bbeacon.ui_alarm.AlarmItem;
import com.example.b00sti.bbeacon.ui_alarm.GetAlarmInteractor;
import com.example.b00sti.bbeacon.ui_alarm.NotificationEvent;
import com.example.b00sti.bbeacon.ui_scanner.GetScannerInteractor;
import com.example.b00sti.bbeacon.ui_scanner.ScannerItem;
import com.example.b00sti.bbeacon.ui_weather.OnAnimationToolbar;
import com.example.b00sti.bbeacon.ui_weather.WeatherTopFragment;
import com.example.b00sti.bbeacon.utils.FragmentBuilder;
import com.example.bskeleton.basics.AnimUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;

    @ViewById(R.id.mainViewPager) public AHBottomNavigationViewPager viewPager;
    @ViewById(R.id.bottomNavigation) public AHBottomNavigation bottomNavigation;
    @ViewById(R.id.mainFAB) public FloatingActionButton floatingActionButton;
    @ViewById(R.id.activityTopPlaceholderFL) public FrameLayout frameLayout;
    @ViewById(R.id.collapsedTitleL) public CollapsingToolbarLayout collapsedTitleL;
    @ViewById(R.id.appBarL) public AppBarLayout appBarLayout;
    @ViewById(R.id.toolbar) public Toolbar toolbar;
    @ViewById(R.id.toolbarWithCircle) public Toolbar toolbarWithCircle;
    @ViewById(R.id.titleToolbarWithCircleTV) TextView titleToolbarWithCircle;
    @ViewById(R.id.illusoryLL) ViewGroup illusoryLL;
    @ViewById(R.id.circleToolbarIconIV) CircleImageView circleImageView;

    @Bean
    NavigationManager navigationManager;
    @Bean
    FragmentBuilder fragmentBuilder;
    Fragment topFragment;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    @AfterViews
    void init() {
        appBarLayout.addOnOffsetChangedListener(this);

        refreshTopFragment(0);
        configureToolbar();
        setNotifications();

        navigationManager.initUI(new NavigationManager.AHonTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                refreshTopFragment(position);
                setNotifications();
                configureToolbar();
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
        refreshAllViews();
    }

    public void refreshAllViews() {
        refreshTopFragment(bottomNavigation.getCurrentItem());
        configureToolbar();
        setNotifications();
        navigationManager.refreshCurrentFragment();
    }

    private void configureToolbar() {
        int currentTab = bottomNavigation.getCurrentItem();
        showTitleToolbar(View.INVISIBLE);
        if (currentTab == 1) {
            chooseToolbar(true);
        } else {
            chooseToolbar(false);
        }
    }

    private void showTitleToolbar(int visible) {
        AnimUtils.startAlphaAnimation(titleToolbarWithCircle, WeatherTopFragment.ALPHA_ANIMATIONS_DURATION, visible);
    }

    private void chooseToolbar(boolean isWeather) {
        if (isWeather) {
            //circleImageView.setVisibility(View.VISIBLE);
            //illusoryLL.setVisibility(View.VISIBLE);
            //toolbarWithCircle.setVisibility(View.VISIBLE);
            //toolbar.setVisibility(View.GONE);
        } else {
            //circleImageView.setVisibility(View.GONE);
            //illusoryLL.setVisibility(View.GONE);
            //toolbar.setVisibility(View.VISIBLE);
            //toolbarWithCircle.setVisibility(View.GONE);
        }

    }

    private void refreshTopFragment(int currentTab) {
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

    public void configureToolbar(boolean isWeather, String titleL) {
        if (isWeather) {
            chooseToolbar(true);
        } else {
            chooseToolbar(false);
        }
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
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

        if (verticalOffset == 0) {
            //toolbar.setVisibility(View.VISIBLE);
            collapsedTitleL.setVisibility(View.VISIBLE);
            handleAlphaOnTitle(percentage);
            handleToolbarTitleVisibility(percentage);
            Log.d("hmm", "onOffsetChanged: a " + collapsedTitleL.getVisibility());
            Log.d("hmm", "onOffsetChanged: a " + toolbarWithCircle.getVisibility());
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            //toolbar.setVisibility(View.GONE);
            //collapsedTitleL.setVisibility(View.INVISIBLE);
            //collapsedTitleL.setBackgroundColor(getResources().getColor(R.color.cardview_dark_background));
            //collapsedTitleL.setTitleEnabled(false);
            //toolbar.setBackgroundColor(getResources().getColor(R.color.cardview_dark_background));
            handleAlphaOnTitle(percentage);
            handleToolbarTitleVisibility(percentage);
            collapsedTitleL.setTitleEnabled(false);
            collapsedTitleL.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            appBarLayout.setBackgroundColor(Color.TRANSPARENT);

            Log.d("hmm", "onOffsetChanged: b " + collapsedTitleL.getVisibility());
            Log.d("hmm", "onOffsetChanged: b " + toolbarWithCircle.getVisibility());
        } else {
            //toolbar.setVisibility(View.VISIBLE);
            collapsedTitleL.setVisibility(View.VISIBLE);
            handleAlphaOnTitle(percentage);
            handleToolbarTitleVisibility(percentage);
            Log.d("hmm", "onOffsetChanged: c " + collapsedTitleL.getVisibility());
            Log.d("hmm", "onOffsetChanged: c " + toolbarWithCircle.getVisibility());
        }

//        collapsedTitleL.setTitleEnabled(false);
/*        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);*/
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!mIsTheTitleVisible) { //&& topFragment != null && topFragment instanceof OnAnimationToolbar) {
                showTitleToolbar(View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {
            if (mIsTheTitleVisible) {// && topFragment != null && topFragment instanceof OnAnimationToolbar) {
                showTitleToolbar(View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) { // && topFragment != null && topFragment instanceof OnAnimationToolbar) {
                ((OnAnimationToolbar) topFragment).animeTitleLayout(View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {
            if (!mIsTheTitleContainerVisible) { // && topFragment != null && topFragment instanceof OnAnimationToolbar) {
                ((OnAnimationToolbar) topFragment).animeTitleLayout(View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }


}
