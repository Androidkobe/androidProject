package com.example.demo.sundu.custview.path;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.SweepGradient;
import android.util.AttributeSet;

public class LoadingView extends BaseView {
    private Path mPath;
    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private float mAnimatorValue;
    private Path mDst;
    private float mLength;

    private SweepGradient SweepGradient;

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mPath = new Path();
        mPath.addCircle(0, 0, 100, Path.Direction.CW);

        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(mPath, true);
        mLength = mPathMeasure.getLength();

        mDst = new Path();

        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimatorValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        drawCoordinate(canvas);

        // 设置着色器到画笔
        canvas.translate(mWidth / 2, mHeight / 2);

        // 需要重置，否则受上次影响，因为getSegment方法是添加而非替换
        mDst.reset();
        // 4.4版本以及之前的版本，需要使用这行代码，否则getSegment无效果
        // 导致这个原因是 硬件加速问题导致
        mDst.lineTo(0, 0);

        if (SweepGradient == null) {
            SweepGradient = new SweepGradient(0f, 0f,
                    Color.RED, Color.GREEN);
        }

        mPaint.setShader(SweepGradient);

        float stop = mLength;
        //float start = (float) (stop - ((0.5 - Math.abs(mAnimatorValue - 0.5)) * mLength));
        float start = 0;
        mPathMeasure.getSegment(start, stop, mDst, true);

        canvas.drawPath(mPath, mPaint);
    }
}