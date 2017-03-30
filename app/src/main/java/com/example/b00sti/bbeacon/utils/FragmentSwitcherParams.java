package com.example.b00sti.bbeacon.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik (b00sti) Pawlik on 2016-11-15
 */

public class FragmentSwitcherParams {
    @NonNull private final FragmentManager fragmentManager;
    @NonNull private final Fragment fragment;
    private final int frameId;
    private String tag;
    private Bundle bundle = null;
    private boolean commitAllowingStateLoss = false;
    private int customEnterAnim;
    private int customExitAnim;
    private List<SharedElement> sharedElements = new ArrayList<>();

    public FragmentSwitcherParams(@NonNull FragmentManager fragmentManager, @NonNull Fragment targetFragment, int frameId) {
        this.fragmentManager = fragmentManager;
        this.fragment = targetFragment;
        this.frameId = frameId;
        tag = targetFragment.getClass().getName();
    }

    public FragmentSwitcherParams(@NonNull FragmentManager fragmentManager, @NonNull Fragment targetFragment, int frameId, Bundle bundle) {
        this.fragmentManager = fragmentManager;
        this.fragment = targetFragment;
        this.frameId = frameId;
        this.bundle = bundle;
        tag = targetFragment.getClass().getName();
    }

    @NonNull
    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    @NonNull
    public Fragment getFragment() {
        return fragment;
    }

    public int getFrameId() {
        return frameId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public boolean isCommitAllowingStateLoss() {
        return commitAllowingStateLoss;
    }

    public void setCommitAllowingStateLoss(boolean commitAllowingStateLoss) {
        this.commitAllowingStateLoss = commitAllowingStateLoss;
    }

    public int getCustomEnterAnim() {
        return customEnterAnim;
    }

    public void setCustomEnterAnim(int customEnterAnim) {
        this.customEnterAnim = customEnterAnim;
    }

    public int getCustomExitAnim() {
        return customExitAnim;
    }

    public void setCustomExitAnim(int customExitAnim) {
        this.customExitAnim = customExitAnim;
    }

    public List<SharedElement> getSharedElements() {
        return sharedElements;
    }

    public void setSharedElements(List<SharedElement> sharedElements) {
        this.sharedElements = sharedElements;
    }

    public void setSharedElement(SharedElement sharedElement) {
        if (sharedElements != null) {
            sharedElements.add(sharedElement);
        }
    }
}
