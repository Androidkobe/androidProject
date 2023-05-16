package com.example.demo.sundu.ontouch

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout

class FirstViewGroup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) ,ChildEventInterface{
    override fun dispatchEvent(motionEvent: MotionEvent?): Boolean {
        var result = actionDownChildView(motionEvent)
        Log.e("sundu","first group ${motionEvent?.action} 返回 $result")
        return result
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val result = super.onTouchEvent(event)
        Log.e(
            "sundu",
            "first view onTouchEvent action " + event?.action + " result = " + result
        )
        return result
    }
}