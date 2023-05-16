package com.example.demo.sundu.ontouch

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.Button

open class MyButtonView : androidx.appcompat.widget.AppCompatButton {
    @JvmOverloads
    constructor (context: Context, attrs: AttributeSet?) : super(
        context,
        attrs,
    )


    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val result = super.dispatchTouchEvent(event)
        Log.e("sundu", "my button view dispatchTouchEvent " + event.action + " result = " + result)
        return result
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val result = super.onTouchEvent(event)
        Log.e("sundu", "my button view onTouchEvent " + event.action + " result = " + result)
        return result
    }
}