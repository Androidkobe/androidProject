package com.example.demo.sundu.custview.SliderView

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView


class SplashGesturesViewSwipeUp @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var mDownX = 0f;

    private var mDownY = 0f;

    private var mTouchSlop = 0.12f

    private var mGesturesAdapter: GesturesDispatchAdapter? = null

    private var mSwipeUpListener: SwipeUpListener? = null

    private var status = 0

    var isMove = false

    private val SWIPUP_MOVEING = 1

    private val SWIPEUP_SUCCESS = 2

    private val SWIPEUP_FAILED = 3

    private val mScreenHeight = 1080

    private var mTipGesturesControllerViewGroup: LinearLayout? = null
    private var mTipGesturesControllerImg: ImageView? = null
    private var mTipGesturesControllerDex: TextView? = null

    val TOUCH_SLOP = ViewConfiguration.get(context).scaledTouchSlop

    init {
//        LayoutInflater.from(context).inflate(R.layout.splash_ad_gesture_swipe_up, this, true)
//        mTipGesturesControllerViewGroup = findViewById(R.id.splash_gesture_swipe_up_view_group)
//        mTipGesturesControllerImg = findViewById(R.id.splash_gesture_swipe_up_tip_image)
//        mTipGesturesControllerDex = findViewById(R.id.splash_gesture_swipe_up_des)
//        mTipGesturesControllerImg?.let {
//            Glide.with(context).load(R.drawable.splash_gesture_swipe_up_tip_img).into(it)
//        }
    }


    open fun setGesturesAdapter(gestures: GesturesDispatchAdapter?) {
        mGesturesAdapter = gestures
    }

    open fun setSwipeUpListener(swipeUpListener: SwipeUpListener) {
        mSwipeUpListener = swipeUpListener
    }

    open fun setDes(str: String?) {
        str?.let {
            mTipGesturesControllerDex?.setText(str)
        }
    }

    open fun setDesColor(color: String?) {
        color?.let {
            try {
                mTipGesturesControllerDex?.setTextColor(Color.parseColor(color))
            } catch (e: Exception) {

            }
        }
    }

    open fun setGesturesSlop(slop: Float?) {
        slop?.let {
            mTouchSlop = slop
        }
    }

    open fun setGestureMarginBottom(bottom: Int) {
        var layoutParams = mTipGesturesControllerViewGroup?.layoutParams
        layoutParams?.let {
            if (layoutParams is MarginLayoutParams) {
                (layoutParams as MarginLayoutParams).bottomMargin = bottom
            }
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
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
                        isMove = true
                        notifyMoveIng()
                        return true
                    } else {

                    }
                }

                MotionEvent.ACTION_CANCEL,
                MotionEvent.ACTION_UP -> {
                    if (!(mGesturesAdapter?.disPatchEvent(event) ?: false)) {
                        if (isMove && (mDownY - event.y > mTouchSlop * mScreenHeight)) {
                            notifySuccess()
                            return true
                        } else if (isMove) {
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