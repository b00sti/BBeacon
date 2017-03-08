package com.example.b00sti.bbeacon.navigation;

import android.animation.Animator;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.example.b00sti.bbeacon.MainActivity;
import com.example.b00sti.bbeacon.R;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;

/**
 * Created by b00sti on 06.03.2017.
 */

@EBean
public class NavigationManager {

    @RootContext
    MainActivity ctx;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private MainViewPagerAdapter adapter;
    private BaseRefreshableFragment currentFragment;

    NavigationManager() {
    }

    private void prepareNavigationItems() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_alarm_white_24dp, R.color.indigo_900);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_wb_sunny_white_24dp, R.color.indigo_900);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.ic_bluetooth_searching_white_24dp, R.color.indigo_900);

        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
    }

    public void initUI(final AHonTabSelectedListener aHonTabSelectedListener) {

        prepareNavigationItems();
        ctx.bottomNavigation.setColored(true);
        ctx.bottomNavigation.addItems(bottomNavigationItems);

        ctx.bottomNavigation.manageFloatingActionButtonBehavior(ctx.floatingActionButton);

        if (ctx.bottomNavigation.getCurrentItem() != 1) {
            showFAB(true);
        } else {
            showFAB(false);
        }

        ctx.bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                if (position != 1) {
                    showFAB(true);
                } else {
                    showFAB(false);
                }

                aHonTabSelectedListener.onTabSelected(position, wasSelected);
                ctx.viewPager.setCurrentItem(position, false);

                if (currentFragment == null) {
                    currentFragment = adapter.getCurrentFragment();
                }

                if (wasSelected) {
                    currentFragment.refresh();
                    return true;
                }

                if (currentFragment != null) {
                    currentFragment.willBeHidden();
                }

                currentFragment = adapter.getCurrentFragment();
                currentFragment.willBeDisplayed();

                return true;

            }
        });

        ctx.viewPager.setOffscreenPageLimit(4);
        adapter = new MainViewPagerAdapter(ctx.getSupportFragmentManager());
        ctx.viewPager.setAdapter(adapter);

        currentFragment = adapter.getCurrentFragment();

    }

    private void showFAB(boolean show) {
        if (show) {
            ctx.floatingActionButton.setVisibility(View.VISIBLE);
            ctx.floatingActionButton.setAlpha(0f);
            ctx.floatingActionButton.setScaleX(0f);
            ctx.floatingActionButton.setScaleY(0f);
            ctx.floatingActionButton.animate()
                    .alpha(1)
                    .scaleX(1)
                    .scaleY(1)
                    .setDuration(300)
                    .setInterpolator(new OvershootInterpolator())
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            ctx.floatingActionButton.animate()
                                    .setInterpolator(new LinearOutSlowInInterpolator())
                                    .start();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    })
                    .start();
        } else {
            if (ctx.floatingActionButton.getVisibility() == View.VISIBLE) {
                ctx.floatingActionButton.animate()
                        .alpha(0)
                        .scaleX(0)
                        .scaleY(0)
                        .setDuration(300)
                        .setInterpolator(new LinearOutSlowInInterpolator())
                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                ctx.floatingActionButton.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                                ctx.floatingActionButton.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        })
                        .start();
            }
        }
    }

    /**
     * Update the bottom navigation colored param
     */
    public void updateBottomNavigationColor(boolean isColored) {
        ctx.bottomNavigation.setColored(isColored);
    }

    /**
     * Return if the bottom navigation is colored
     */
    public boolean isBottomNavigationColored() {
        return ctx.bottomNavigation.isColored();
    }

    /**
     * Add or remove items of the bottom navigation
     */
    public void updateBottomNavigationItems(@Nullable ArrayList<Pair<AHNotification, Integer>> notifications) {

        if (notifications == null) {
            ctx.bottomNavigation.removeAllItems();
            ctx.bottomNavigation.addItems(bottomNavigationItems);
        } else {
            for (Pair<AHNotification, Integer> notification : notifications) {
                int i = notification.second;
                if (i < 3 && i >= 0) {
                    ctx.bottomNavigation.setNotification(notification.first, i);
                }
            }
        }
    }

    /**
     * Show or hide the bottom navigation with animation
     */
    public void showOrHideBottomNavigation(boolean show) {
        if (show) {
            ctx.bottomNavigation.restoreBottomNavigation(true);
        } else {
            ctx.bottomNavigation.hideBottomNavigation(true);
        }
    }

    /**
     * Show or hide selected item background
     */
    public void updateSelectedBackgroundVisibility(boolean isVisible) {
        ctx.bottomNavigation.setSelectedBackgroundVisible(isVisible);
    }

    /**
     * Show or hide selected item background
     */
    public void setForceTitleHide(boolean forceTitleHide) {
        AHBottomNavigation.TitleState state = forceTitleHide ? AHBottomNavigation.TitleState.ALWAYS_HIDE : AHBottomNavigation.TitleState.ALWAYS_SHOW;
        ctx.bottomNavigation.setTitleState(state);
    }

    /**
     * Return the number of items in the bottom navigation
     */
    public int getBottomNavigationNbItems() {
        return ctx.bottomNavigation.getItemsCount();
    }

    public interface AHonTabSelectedListener {
        public void onTabSelected(int position, boolean wasSelected);
    }
}
