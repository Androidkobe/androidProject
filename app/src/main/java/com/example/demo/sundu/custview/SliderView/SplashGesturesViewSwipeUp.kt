package com.example.demo.sundu.custview.SliderView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.FrameLayout
import com.example.demo.R
import java.lang.Math.abs


class SplashGesturesViewSwipeUp @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var mDownX = 0f;

    private var mDownY = 0f;

    private val mTouchSlop = 0.12f

    private var mGesturesAdapter: GesturesDispatchAdapter? = null

    var isMove = false

    val TOUCH_SLOP = ViewConfiguration.get(context).scaledTouchSlop

    private var mSwipeUpListener: SwipeUpListener? = null

    private var status = 0

    private val SWIPUP_MOVEING = 1

    private val SWIPEUP_SUCCESS = 2

    private val SWIPEUP_FAILED = 3

    init {
        LayoutInflater.from(context).inflate(R.layout.activity_swipe_up_view, this, true)
    }


    open fun setGesturesAdapter(gestures: GesturesDispatchAdapter?) {
        mGesturesAdapter = gestures
    }

    open fun setSwipeUpListener(swipeUpListener: SwipeUpListener) {
        mSwipeUpListener = swipeUpListener
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mDownX = event.x
                    mDownY = event.y
                    mGesturesAdapter?.disPatchEvent(event)
                    return true
                }
                MotionEvent.ACTION_MOVE -> {
                    if (!(mGesturesAdapter?.disPatchEvent(event) ?: false)) {
                        if(abs(event.y - mDownY)>TOUCH_SLOP){
                            isMove = true
                            notifyMoveIng()
                            return true
                        }else{
                            return false
                        }
                    }
                }

                MotionEvent.ACTION_CANCEL,
                MotionEvent.ACTION_UP -> {
                    if (!(mGesturesAdapter?.disPatchEvent(event) ?: false)) {
                        if (isMove && (mDownY - event.y > mTouchSlop * 2340)) {
                            notifySuccess()
                            return true
                        } else if (isMove){
                            notifyFailed()
                            return true
                        }
                    }
                    isMove = false
                }
            }
            return false
        }
        return false
    }

    fun notifyMoveIng() {
        if (status != SWIPUP_MOVEING) {
            status = SWIPUP_MOVEING
            mSwipeUpListener?.moveing()
        }

    }

    fun notifySuccess() {
        if (status != SWIPEUP_SUCCESS) {
            status = SWIPEUP_SUCCESS
            mSwipeUpListener?.success()
        }
    }

    fun notifyFailed() {
        if (status != SWIPEUP_FAILED) {
            status = SWIPEUP_FAILED
            mSwipeUpListener?.failed()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mSwipeUpListener = null
    }
}

interface SwipeUpListener {
    fun success()
    fun moveing()
    fun failed()
}