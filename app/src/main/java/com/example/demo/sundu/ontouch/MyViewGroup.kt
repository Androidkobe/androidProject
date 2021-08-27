package com.example.demo.sundu.ontouch

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.LinearLayout

open class MyViewGroup : LinearLayout {
    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    )


    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val result = super.dispatchTouchEvent(event)
        Log.e(
            "sundu",
            "MyViewGroup view dispatchTouchEvent action " + event.action + " result = " + result
        )
        return result
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        val result = super.onInterceptTouchEvent(event)
        Log.e(
            "sundu",
            "MyViewGroup view onInterceptTouchEvent action " + event.action + " result = " + result
        )
        return result
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val result = super.onTouchEvent(event)
        Log.e(
            "sundu",
            "MyViewGroup view onTouchEvent action " + event.action + " result = " + result
        )
        return result
    }
}