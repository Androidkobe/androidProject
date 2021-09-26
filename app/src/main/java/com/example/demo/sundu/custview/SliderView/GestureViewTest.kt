package com.example.demo.sundu.custview.SliderView

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import com.example.demo.R
import com.miui.zeus.msa.app.splashad.view.commonview.GesturesDispatchAdapter

class GestureViewTest @JvmOverloads constructor(
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

        addView(TestView(context),ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT))
    }


    open fun setGesturesAdapter(gestures: GesturesDispatchAdapter?) {
        mGesturesAdapter = gestures
    }

    open fun setSwipeUpListener(swipeUpListener: SwipeUpListener) {
        mSwipeUpListener = swipeUpListener
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        Log.e("sundu","text ----------- = "+event?.action)
        event?.let {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mDownX = event.x
                    mDownY = event.y
                    mGesturesAdapter?.disPatchEvent(event)
                }
                MotionEvent.ACTION_MOVE -> {
                    if (!(mGesturesAdapter?.disPatchEvent(event) ?: false)) {
                        if(Math.abs(event.y - mDownY) >TOUCH_SLOP){
                            isMove = true
                            notifyMoveIng()
                        }else{}
                    }else{}
                }

                MotionEvent.ACTION_CANCEL,
                MotionEvent.ACTION_UP -> {
                    if (!(mGesturesAdapter?.disPatchEvent(event) ?: false)) {
                        if (isMove && (mDownY - event.y > mTouchSlop * 2340)) {
                            notifySuccess()
                        } else if (isMove){
                            notifyFailed()
                        }
                    }
                    isMove = false
                }
                else->{}
            }
        }
        return super.dispatchTouchEvent(event)
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

    class TestView : View{
        constructor(context: Context?) : super(context)
        constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
        constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
        )

        override fun onTouchEvent(event: MotionEvent?): Boolean {
            return true
        }
    }
}