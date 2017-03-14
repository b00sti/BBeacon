package com.example.b00sti.bbeacon;

import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.example.b00sti.bbeacon.navigation.NavigationManager;
import com.example.b00sti.bbeacon.navigation.NotificationManager;
import com.example.b00sti.bbeacon.ui_alarm.AlarmItem;
import com.example.b00sti.bbeacon.ui_alarm.GetAlarmInteractor;
import com.example.b00sti.bbeacon.utils.FragmentBuilder;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.view_pager) public AHBottomNavigationViewPager viewPager;
    @ViewById(R.id.bottom_navigation) public AHBottomNavigation bottomNavigation;
    @ViewById(R.id.floating_action_button) public FloatingActionButton floatingActionButton;
    @ViewById(R.id.activity_top_placeholder) public FrameLayout frameLayout;
    @ViewById(R.id.collapsedTitleL) public CollapsingToolbarLayout collapsedTitleL;

    @Bean
    NavigationManager navigationManager;

    @Bean
    FragmentBuilder fragmentBuilder;

    @AfterViews
    void init() {
        refreshTopFragment(0);
        setNotifications();

        navigationManager.initUI(new NavigationManager.AHonTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                refreshTopFragment(position);
                setNotifications();
            }
        });

    }

    private void refreshTopFragment(int currentTab) {
        Fragment fragment;
        if (currentTab == 0) {
            fragment = fragmentBuilder.newFragment(FragmentBuilder.TOP_ALARM);
        } else if (currentTab == 1) {
            fragment = fragmentBuilder.newFragment(FragmentBuilder.TOP_WEATHER);
        } else if (currentTab == 2) {
            fragment = fragmentBuilder.newFragment(FragmentBuilder.TOP_SCANNER);
        } else {
            fragment = fragmentBuilder.newFragment(FragmentBuilder.EMPTY);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_top_placeholder, fragment);
        transaction.commit();
    }

    public void setCollapsedTitleL(String titleL) {
        collapsedTitleL.setTitle(titleL);
    }

    public void setNotifications() {
        new GetAlarmInteractor().execute().subscribe(new Consumer<List<AlarmItem>>() {
            @Override
            public void accept(List<AlarmItem> alarmItems) throws Exception {
                ArrayList<Pair<AHNotification, Integer>> notifications = new ArrayList<>();
                notifications.add(new Pair<>(NotificationManager.newDefault(getApplicationContext(), String.valueOf(Integer.valueOf(alarmItems.size()))), 0));
                notifications.add(new Pair<>(NotificationManager.newDefault(getApplicationContext(), "2"), 2));
                updateBottomNavigationItems(notifications);
            }
        });
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
}
