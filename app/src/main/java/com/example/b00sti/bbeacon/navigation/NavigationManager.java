package com.example.b00sti.bbeacon.navigation;

import android.animation.Animator;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.example.b00sti.bbeacon.MainActivity;
import com.example.b00sti.bbeacon.R;
import com.example.b00sti.bbeacon.base.BaseFragment;
import com.example.b00sti.bbeacon.base.BaseInnerViewActivity_;
import com.example.b00sti.bbeacon.base.RefreshableFragment;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;

import lombok.Getter;

/**
 * Created by b00sti on 06.03.2017.
 */

@EBean
public class NavigationManager {
    private static final String TAG = "NavigationManager";

    @RootContext
    MainActivity ctx;

    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private MainViewPagerAdapter adapter;

    @Getter
    private BaseFragment currentFragment;

    NavigationManager() {
    }

    public void refreshCurrentFragment() {
        if (currentFragment == null) {
            currentFragment = adapter.getCurrentFragment();
        }

        if (ctx != null) {
            ctx.setNotifications();
        }

        if (currentFragment != null && currentFragment instanceof RefreshableFragment) {
            ((RefreshableFragment) currentFragment).refresh();
            Log.d(TAG, "current fragment refreshed " + currentFragment.getClass().getName());
        }
    }

    public void initUI(final AHonTabSelectedListener aHonTabSelectedListener) {

        prepareNavigationItems();

        ctx.bottomNavigation.setColored(true);
        ctx.bottomNavigation.addItems(bottomNavigationItems);

        ctx.bottomNavigation.manageFloatingActionButtonBehavior(ctx.mainFAB);

        if (ctx.bottomNavigation.getCurrentItem() != 1) {
            showFAB(true);
        } else {
            showFAB(false);
        }

        ctx.mainFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = ctx.bottomNavigation.getCurrentItem();
                if (i == 0) {
                    onFabClickedOnFirstPage();
                } else if (i == 2) {
                    onFabClickedOnThirdPage();
                } else {
                    Log.e(TAG, "onClick: " + "clicked fab on page where is not available");
                }
            }
        });

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
                    refreshCurrentFragment();
                    return true;
                }

                if (currentFragment != null && currentFragment instanceof RefreshableFragment) {
                    ((RefreshableFragment) currentFragment).willBeHidden();
                }

                currentFragment = adapter.getCurrentFragment();
                if (currentFragment != null && currentFragment instanceof RefreshableFragment) {
                    ((RefreshableFragment) currentFragment).willBeDisplayed();
                }
                return true;

            }
        });

        ctx.viewPager.setOffscreenPageLimit(4);
        adapter = new MainViewPagerAdapter(ctx.getSupportFragmentManager());
        ctx.viewPager.setAdapter(adapter);

        currentFragment = adapter.getCurrentFragment();

        if (currentFragment != null && currentFragment instanceof RefreshableFragment) {
            ((RefreshableFragment) currentFragment).willBeDisplayed();
        } else {
            Log.d(TAG, "initUI: currentFragment is null");
        }
        
    }

    private void prepareNavigationItems() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_alarm_white_24dp, R.color.indigo_900);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_wb_sunny_white_24dp, R.color.indigo_900);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.ic_bluetooth_searching_white_24dp, R.color.indigo_900);

        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
    }

    private void onFabClickedOnFirstPage() {
        //ActivityBuilder.startDisableAlarmActivity(ctx, "");
        Intent intent = new Intent(ctx, BaseInnerViewActivity_.class);
        intent.putExtra(ctx.getString(R.string.bundle_fragment), FragmentBuilder.ADD_NEW_ALARM);
        ctx.startActivity(intent);
    }

    private void onFabClickedOnThirdPage() {
        Intent intent = new Intent(ctx, BaseInnerViewActivity_.class);
        intent.putExtra(ctx.getString(R.string.bundle_fragment), FragmentBuilder.ADD_NEW_BEACON);
        ctx.startActivity(intent);
    }

    private void showFAB(boolean show) {
        if (show) {
            ctx.mainFAB.setVisibility(View.VISIBLE);
            ctx.mainFAB.setAlpha(0f);
            ctx.mainFAB.setScaleX(0f);
            ctx.mainFAB.setScaleY(0f);
            ctx.mainFAB.animate()
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
                            ctx.mainFAB.animate()
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
            if (ctx.mainFAB.getVisibility() == View.VISIBLE) {
                ctx.mainFAB.animate()
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
                                ctx.mainFAB.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                                ctx.mainFAB.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        })
                        .start();
            }
        }
    }

    public void updateBottomNavigationColor(boolean isColored) {
        ctx.bottomNavigation.setColored(isColored);
    }

    public boolean isBottomNavigationColored() {
        return ctx.bottomNavigation.isColored();
    }

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

    public void showOrHideBottomNavigation(boolean show) {
        if (show) {
            ctx.bottomNavigation.restoreBottomNavigation(true);
        } else {
            ctx.bottomNavigation.hideBottomNavigation(true);
        }
    }

    public void updateSelectedBackgroundVisibility(boolean isVisible) {
        ctx.bottomNavigation.setSelectedBackgroundVisible(isVisible);
    }

    public void setForceTitleHide(boolean forceTitleHide) {
        AHBottomNavigation.TitleState state = forceTitleHide ? AHBottomNavigation.TitleState.ALWAYS_HIDE : AHBottomNavigation.TitleState.ALWAYS_SHOW;
        ctx.bottomNavigation.setTitleState(state);
    }

    public int getBottomNavigationNbItems() {
        return ctx.bottomNavigation.getItemsCount();
    }

    public interface AHonTabSelectedListener {
        void onTabSelected(int position, boolean wasSelected);
    }
}
