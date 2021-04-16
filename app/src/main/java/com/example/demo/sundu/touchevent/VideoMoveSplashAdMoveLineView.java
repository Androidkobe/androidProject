package com.example.demo.sundu.touchevent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */

public class VideoMoveSplashAdMoveLineView extends View {

    private Paint mPaint;
    private float dx, sx, tx, dy, sy, ty, mv;
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private boolean isMove = false;
    private boolean disPathMoveListener = false;
    private OnMoveListener mOnMoveListener;
    private CopyOnWriteArrayList<Rect> mIgnoreRects = new CopyOnWriteArrayList<>();

    public VideoMoveSplashAdMoveLineView(Context context) {
        super(context);
        init();
    }

    public VideoMoveSplashAdMoveLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VideoMoveSplashAdMoveLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#f8b62d"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(30);
//        mPaint.setAlpha(178);//0.7*255
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mv = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(getHeight() >0 && getWidth()>0 && mBitmap == null){
            mBitmap = Bitmap
                    .createBitmap(getWidth(), getHeight(),
                            Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("sundu","dispatchTouchEvent = "+event.getAction());
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("sundu","touchevent = "+event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                sx = dx = event.getX();
                sy = dy = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(dx - event.getX()) > mv || Math.abs(dy - event.getY()) > mv) {
                    isMove = true;
                    tx = event.getX();
                    ty = event.getY();
                    mCanvas.drawLine(sx, sy, tx, ty, mPaint);
                    invalidate();
                    sx = tx;
                    sy = ty;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                dx = dy = sx = sy = tx = ty = 0;
                mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                if (isMove && !disPathMoveListener && mOnMoveListener != null) {
                    disPathMoveListener = true;
                    mOnMoveListener.onMove();
                }
                break;
        }
        for (Rect rect : mIgnoreRects) {
            if (rect != null && rect.contains((int) sx, (int) sy)) {
                return false;
            }
        }
        return isMove ? true : super.onTouchEvent(event);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (sx > 0 && sy > 0) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }

    public void setIgnoreClickRect(List<Rect> rects) {
        mIgnoreRects.clear();
        if (rects != null && rects.size() > 0) {
            for (Rect rect : rects) {
                if (rect != null) {
                    mIgnoreRects.add(rect);
                }
            }
        }
    }

    public void setOnMoveListener(OnMoveListener onMoveListener) {
        mOnMoveListener = onMoveListener;
    }

    public interface OnMoveListener {
        void onMove();
    }
}