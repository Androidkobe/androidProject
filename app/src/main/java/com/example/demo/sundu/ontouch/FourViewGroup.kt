package com.example.demo.sundu.ontouch

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout

class FourViewGroup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) ,ChildEventInterface{
    override fun dispatchEvent(motionEvent: MotionEvent?): Boolean {
        var result = actionDownChildView(motionEvent)
        Log.e("sundu","four group ${motionEvent?.action} 返回 $result")
        return result
    }


    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        val result = super.dispatchTouchEvent(event)
        Log.e(
            "sundu",
            "four view dispatchTouchEvent action " + event?.action + " result = " + result
        )
        return result
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        val result = super.onInterceptTouchEvent(event)
        Log.e(
            "sundu",
            "four view onInterceptTouchEvent action " + event?.action + " result = " + result
        )
        return result
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val result = super.onTouchEvent(event)
        Log.e(
            "sundu",
            "four view onTouchEvent action " + event?.action + " result = " + result
        )
        return result
    }
}