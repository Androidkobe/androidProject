package com.example.demo.sundu.custview.beziercurve.dragpop;

import android.animation.TypeEvaluator;
import android.graphics.PointF;
import android.util.Log;

/**
 * PointF动画估值器
 */
public class PointFEvaluator implements TypeEvaluator<PointF> {

    @Override
    public PointF evaluate(float fraction, PointF startPointF, PointF endPointF) {
        Log.e("sundu","fraction = "+fraction);
        float x = startPointF.x + fraction * (endPointF.x - startPointF.x);
        float y = startPointF.y + fraction * (endPointF.y - startPointF.y);
        return new PointF(x, y);
    }
}
