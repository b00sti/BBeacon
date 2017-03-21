package com.example.bskeleton.basics;

import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-21
 */

public class AnimUtils {

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

}
