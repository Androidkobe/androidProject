package com.example.demo.sundu.touchevent

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.Button

class ChildView2 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : Button(context, attrs) {
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.e("sundu2",event?.action.toString())
        return super.onTouchEvent(event)
    }
}