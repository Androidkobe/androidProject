package com.example.demo.sundu.custview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */

public class ShakeImageAnimationImageView extends ImageView {

    private SpringAnimation signUpBtnAnimY;
    private ObjectAnimator objectAnimator;

    public ShakeImageAnimationImageView(Context context) {
        super(context);
    }

    public ShakeImageAnimationImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ShakeImageAnimationImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void startAnimation() {
        Log.e("sundu","start");
        startShakeAnimation(new DynamicAnimation.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
              startShakeAnimation(new DynamicAnimation.OnAnimationEndListener() {
                  @Override
                  public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                      Log.e("sundu","end");
                      startAlpahAnimation();
                  }
              });
            }
        });
    }

    private void startAlpahAnimation() {
        objectAnimator = ObjectAnimator.ofFloat(this, "alpha", 1f, 0.4f, 1f, 0.4f, 1f);
        objectAnimator.setDuration(2000);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }

    private void startShakeAnimation(DynamicAnimation.OnAnimationEndListener onAnimationEndListener) {
        signUpBtnAnimY = new SpringAnimation(this, DynamicAnimation.ROTATION, 0);
        signUpBtnAnimY.getSpring().setStiffness(900f);
        signUpBtnAnimY.getSpring().setDampingRatio(0.2f);
        signUpBtnAnimY.setStartVelocity(3000);
        signUpBtnAnimY.addEndListener(onAnimationEndListener);
        signUpBtnAnimY.start();
    }

    public void stopAnimation() {
        clearAnimation();
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        if (signUpBtnAnimY != null) {
            signUpBtnAnimY.cancel();
        }
    }

}
