package com.example.demo.sundu.custview.bitmapshader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.view.MotionEvent;
import android.view.View;

import com.example.demo.R;

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */

public class TelescopeView extends View {

    private Paint mPaint;
    private Bitmap mBitmap,mBitmapBG;
    private int mDx = -1, mDy = -1;

    public TelescopeView(Context context) {
        super(context);
        init();
    }


    private void init() {
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.scenery);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDx = (int) event.getX();
                mDy = (int) event.getY();
                postInvalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                mDx = (int) event.getX();
                mDy = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mDx = -1;
                mDy = -1;
                break;
        }


        postInvalidate();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmapBG == null){
            mBitmapBG = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvasbg = new Canvas(mBitmapBG);
            canvasbg.drawBitmap(mBitmap,null,new Rect(0,0,getWidth(),getHeight()),mPaint);
        }

        if (mDx != -1 && mDy != -1) {
            mPaint.setShader(new BitmapShader(mBitmapBG, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
            canvas.drawCircle(mDx, mDy, 150, mPaint);
        }
    }
}