package com.example.demo.sundu.ontouch

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent

open class MyButtonView : androidx.appcompat.widget.AppCompatButton {
    @JvmOverloads
    constructor (context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        setOnClickListener({
            Log.e("sundu", "my button view action onclick")
        })
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val result = super.onTouchEvent(event)
        Log.e("sundu", "my button view action " + event.action + " result = " + result)
        return result
    }
}