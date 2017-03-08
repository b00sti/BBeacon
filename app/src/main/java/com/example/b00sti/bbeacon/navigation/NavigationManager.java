package com.example.b00sti.bbeacon.navigation;

import android.animation.Animator;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.b00sti.bbeacon.MainActivity;
import com.example.b00sti.bbeacon.R;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.res.IntArrayRes;

import java.util.ArrayList;

/**
 * Created by b00sti on 06.03.2017.
 */

@EBean
public class NavigationManager {

    @IntArrayRes(R.array.tab_colors)
    int[] tabColors;
    @RootContext
    MainActivity ctx;
    private boolean useMenuResource = false;
    private AHBottomNavigationAdapter navigationAdapter;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private MainViewPagerAdapter adapter;
    private BaseRefreshableFragment currentFragment;

    NavigationManager() {
    }

    void prepareNavigationItems() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_apps_black_24dp, R.color.color_tab_1);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_maps_local_bar, R.color.color_tab_2);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.ic_maps_local_restaurant, R.color.color_tab_3);

        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
    }

    public void initUI(final AHonTabSelectedListener aHonTabSelectedListener) {

        if (useMenuResource) {
            tabColors = ctx.getResources().getIntArray(R.array.tab_colors);
            navigationAdapter = new AHBottomNavigationAdapter(ctx, R.menu.bottom_navigation_menu_3);
            navigationAdapter.setupWithBottomNavigation(ctx.bottomNavigation, tabColors);
        } else {
            prepareNavigationItems();
            ctx.bottomNavigation.addItems(bottomNavigationItems);
        }

        ctx.bottomNavigation.manageFloatingActionButtonBehavior(ctx.floatingActionButton);
        ctx.bottomNavigation.setTranslucentNavigationEnabled(false);
        ctx.bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

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

                if (position == 1) {
                    ctx.bottomNavigation.setNotification("", 1);

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

                return true;

            }
        });

		/*
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
			@Override public void onPositionChange(int y) {
				Log.d("DemoActivity", "BottomNavigation Position: " + y);
			}
		});
		*/

        ctx.viewPager.setOffscreenPageLimit(4);
        adapter = new MainViewPagerAdapter(ctx.getSupportFragmentManager());
        ctx.viewPager.setAdapter(adapter);

        currentFragment = adapter.getCurrentFragment();

        //TWORZENIE NOTYFIKACJI
/*        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Setting custom colors for notification
                AHNotification notification = new AHNotification.Builder()
                        .setText(":)")
                        .setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_notification_back))
                        .setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color_notification_text))
                        .build();
                bottomNavigation.setNotification(notification, 1);
                Snackbar.make(bottomNavigation, "Snackbar with bottom navigation",
                        Snackbar.LENGTH_SHORT).show();

            }
        }, 3000);*/

        //-----------------
        //bottomNavigation.setDefaultBackgroundResource(R.drawable.bottom_navigation_background);
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
    public void updateBottomNavigationItems(boolean addItems) {

        if (useMenuResource) {
            if (addItems) {
                navigationAdapter = new AHBottomNavigationAdapter(ctx, R.menu.bottom_navigation_menu_5);
                navigationAdapter.setupWithBottomNavigation(ctx.bottomNavigation, tabColors);
                ctx.bottomNavigation.setNotification("1", 3);
            } else {
                navigationAdapter = new AHBottomNavigationAdapter(ctx, R.menu.bottom_navigation_menu_3);
                navigationAdapter.setupWithBottomNavigation(ctx.bottomNavigation, tabColors);
            }

        } else {
            if (addItems) {
                AHBottomNavigationItem item4 = new AHBottomNavigationItem(ctx.getString(R.string.tab_4),
                        ContextCompat.getDrawable(ctx, R.drawable.ic_maps_local_bar),
                        ContextCompat.getColor(ctx, R.color.color_tab_3));
                AHBottomNavigationItem item5 = new AHBottomNavigationItem(ctx.getString(R.string.tab_5),
                        ContextCompat.getDrawable(ctx, R.drawable.ic_maps_place),
                        ContextCompat.getColor(ctx, R.color.color_tab_3));

                ctx.bottomNavigation.addItem(item4);
                ctx.bottomNavigation.addItem(item5);
                ctx.bottomNavigation.setNotification("0", 0);
                ctx.bottomNavigation.setNotification("1", 1);
                ctx.bottomNavigation.setNotification("2", 2);
                ctx.bottomNavigation.setNotification("3", 3);
            } else {
                ctx.bottomNavigation.removeAllItems();
                ctx.bottomNavigation.addItems(bottomNavigationItems);
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
