package com.example.demo.sundu.custview.SliderView



import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.TextView

class MyTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    private var sundu = 1000

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        Log.e("sundu", "dis text view action = " + event?.action)
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.e("sundu", "text view action = " + event?.action)
        if (event?.action == MotionEvent.ACTION_UP) {
            return super.onTouchEvent(event)
        }
        return super.onTouchEvent(event)
    }
}