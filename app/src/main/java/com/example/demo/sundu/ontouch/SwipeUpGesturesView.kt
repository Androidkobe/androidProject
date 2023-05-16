package com.example.demo.sundu.ontouch

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.FrameLayout

class SwipeUpGesturesView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    var mDownX = 0f

    var mDownY = 0f

    var isMove = false

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN->{
                isMove = false
                mDownX = event.x
                mDownY = event.y
                Log.e("sundu","上滑 接收到 down")
            }
            MotionEvent.ACTION_MOVE->{
                if (mDownX == 0f){
                    mDownX = event.x
                }
                if (mDownY == 0f){
                    mDownY = event.y
                }
                if (Math.abs(mDownY - event.y) > ViewConfiguration.get(context).scaledTouchSlop) {
                    Log.e("sundu", "上滑 接收到 move")
                    isMove = true
                    return true
                }else {
                    return false
                }
            }
            MotionEvent.ACTION_UP->{
                if (isMove) {
                    isMove = false
                    if (Math.abs(mDownY - event.y) > 120) {
                        Log.e("sundu", "上滑 接收到 up  触发")
                    } else {
                        Log.e("sundu", "上滑 接收到 up  没有触发")
                    }
                    return true
                }
                return false
            }
        }
        return super.onTouchEvent(event)
    }
}