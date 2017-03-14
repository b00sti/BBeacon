package com.example.b00sti.bbeacon.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Dominik (b00sti) Pawlik on 2016-10-28
 */

public class FragmentSwitcher {
    private static final String TAG = "FragmentSwitcher";

    public static void switchFragment(@NonNull FragmentSwitcherParams params, Context context) {

        FragmentTransaction transaction = params.getFragmentManager().beginTransaction();

        Fragment fragment = Fragment.instantiate(context, params.getTag());

        fragment.setArguments(params.getBundle());
        if (params.getSharedElements() != null) {
            for (SharedElement sharedElement : params.getSharedElements()) {
                transaction.addSharedElement(sharedElement.view, sharedElement.name);
            }
        }

        if (params.getCustomEnterAnim() != 0 && params.getCustomExitAnim() != 0) {
            transaction.setCustomAnimations(params.getCustomEnterAnim(), params.getCustomExitAnim());
        }

        transaction.replace(params.getFrameId(), fragment, params.getTag());
        if (params.isCommitAllowingStateLoss()) {
            transaction.commitAllowingStateLoss();
        }

        transaction.commit();

    }

    public static void switchFragment(@NonNull FragmentSwitcherParams params) {

        FragmentTransaction transaction = params.getFragmentManager().beginTransaction();

        Fragment fragment = params.getFragment();

        fragment.setArguments(params.getBundle());
        if (params.getSharedElements() != null) {
            for (SharedElement sharedElement : params.getSharedElements()) {
                transaction.addSharedElement(sharedElement.view, sharedElement.name);
            }
        }

        if (params.getCustomEnterAnim() != 0 && params.getCustomExitAnim() != 0) {
            transaction.setCustomAnimations(params.getCustomEnterAnim(), params.getCustomExitAnim());
        }

        transaction.replace(params.getFrameId(), fragment, params.getTag());
        if (params.isCommitAllowingStateLoss()) {
            transaction.commitAllowingStateLoss();
        }

        transaction.commit();

    }
}
