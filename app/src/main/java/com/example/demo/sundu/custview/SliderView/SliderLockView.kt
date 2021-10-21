package com.example.demo.sundu.custview.SliderView

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.demo.R
import kotlin.math.abs

class SliderLockView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {
    val TAG = "sundu"

    private var status = 0

    private val UNLOCK_MOVEING = 1

    private val UNLOCK_SUCCESS = 2

    private val UNLOCK_FAILED = 3

    private val UNLOCK_CANCEL = 4

    val defaultWidth = 600
    val defaultHeight = 80

    var mSliderSpace = 12

    var mBgPaintStrokeWidth = 1f
    var mBgStrokeColor = Color.parseColor("#80FFFFFF")
    var mBgPaintColor = Color.parseColor("#4D000000")
    var mBgPaint = Paint()
    var mRectBg = RectF()

    var mTextColor = Color.parseColor("#FFFFFF")
    var mTextPaint = Paint()
    var mTextDes = "点击或滑动前往好礼"
    var mTextHeight = 58f

    var animatioDuration = 300L

    var mSliderViewWidth = 0
    var mSliderViewHeight = 0
    var mSliderLocation = IntArray(2)
    var mSliderView = ImageView(context)
    var mSliderViewLayoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    var mActionDownX = 0f
    var mActionDownY = 0f
    var mDistance = 0
    var mUnLockToClick = 0.7f
    var parLocation = IntArray(2)
    var mViewWidth = defaultWidth
    var mViewHeight = defaultHeight

    var mlockListener: SliderLockListener? = null

    val TOUCH_SLOP = ViewConfiguration.get(context).scaledTouchSlop

    //滑块是否选中移动
    var isSliderTouchMove = false
    //滑块是否在归位动画
    var isSliderAnimationing = false
    //滑块是否倍选择中
    var isSliderSelectTouch = false
    //view区域中是否滑动
    var isTouchMoveViewRectX = false
    //view区域中是否滑动
    var isTouchMoveViewRectY = false

    init {
        val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SliderLockView)
        mSliderSpace =
            typedArray.getDimensionPixelSize(R.styleable.SliderLockView_sliderSpace, mSliderSpace)
        mTextDes = typedArray.getString(R.styleable.SliderLockView_text) ?: mTextDes
        mTextHeight =
            typedArray.getDimensionPixelSize(
                R.styleable.SliderLockView_textsize,
                mTextHeight.toInt()
            ).toFloat()
        mTextColor = typedArray.getColor(R.styleable.SliderLockView_textcolor, mTextColor)
        setWillNotDraw(false)
        initBgPaintInit()
        initBaseView()
    }


    fun initBgPaintInit() {
        mBgPaint.strokeWidth = mBgPaintStrokeWidth
        mBgPaint.isAntiAlias = true
        mTextPaint.isAntiAlias = true
    }

    fun initBaseView() {
        addView(mSliderView, mSliderViewLayoutParams)
        Glide.with(context).load(R.mipmap.splash_btn_slider).into(mSliderView)
    }

    open fun setBgGroundColor(colorStr: String?) {
        colorStr?.let {
            try {
                mBgPaintColor = Color.parseColor(colorStr)
                invalidate()
            } catch (e: Exception) {

            }
        }
    }

    open fun setText(des: String?) {
        des?.let {
            mTextDes = des
            invalidate()
        }
    }

    open fun setTextColor(colorStr: String?) {
        colorStr?.let {
            try {
                mTextColor = Color.parseColor(colorStr)
                invalidate()
            } catch (e: Exception) {

            }
        }
    }

    open fun setUnLokSpace(unlockSpace: Float?) {
        unlockSpace?.let {
            mUnLockToClick = unlockSpace
        }
    }

    open fun setLockListener(arg: SliderLockListener?) {
        mlockListener = arg
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMeasureSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMeasureSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthMeasure = MeasureSpec.getSize(widthMeasureSpec)
        val heightMeasure = MeasureSpec.getSize(heightMeasureSpec)
        when (widthMeasureSpecMode) {
            MeasureSpec.AT_MOST -> {
                mViewWidth = defaultWidth;
            }
            MeasureSpec.UNSPECIFIED,
            MeasureSpec.EXACTLY -> {
                mViewWidth = widthMeasure
            }
        }
        when (heightMeasureSpecMode) {
            MeasureSpec.AT_MOST -> {
                mViewHeight = defaultHeight
            }
            MeasureSpec.UNSPECIFIED,
            MeasureSpec.EXACTLY -> {
                mViewHeight = heightMeasure
            }
        }

        for (i in 0..childCount) {
            var child: View? = getChildAt(i)
            child?.let { child ->
                val sliderSize = mViewHeight - 2 * mSliderSpace
                mSliderViewWidth = sliderSize
                mSliderViewHeight = sliderSize
                child.layoutParams.width = mSliderViewWidth
                child.layoutParams.height = mSliderViewHeight
                measureChild(child, widthMeasureSpec, heightMeasureSpec)
            }
        }
        setMeasuredDimension(mViewWidth, mViewHeight)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (!isSliderAnimationing && !isSliderTouchMove) {
            for (i in 0..childCount) {
                var child: View? = getChildAt(i)
                child?.let { child ->
                    layoutSlider(child, 0, 0)
                }
            }
        }
    }

    private fun layoutSlider(view: View, left: Int, top: Int) {
        val childWidth: Int = view.measuredWidth
        val childHeight: Int = view.measuredHeight
        var currentLeft = left + mSliderSpace
        val currentTop = top + mSliderSpace
        var currentRight = currentLeft + childWidth
        val currentBottom = currentTop + childHeight
        if (left < 0) {
            currentLeft = 0 + mSliderSpace
            currentRight = 0 + mSliderSpace + view.measuredWidth
        }
        if (currentRight > mViewWidth) {
            currentLeft = mViewWidth - mSliderSpace - view.measuredWidth
            currentRight = mViewWidth - mSliderSpace
        }
        view.layout(currentLeft, currentTop, currentRight, currentBottom)
    }



    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        ev?.let { ev ->
            if (!isSliderAnimationing) {
                when (ev.action) {
                    MotionEvent.ACTION_DOWN -> {
                        mActionDownX = ev.rawX
                        mActionDownY = ev.rawY
                        isSliderSelectTouch = isTouchSliderView(ev.rawX.toInt())
                        return true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        if(abs(ev.rawX - mActionDownX) > TOUCH_SLOP){
                            isTouchMoveViewRectX = true
                        }
                        if(abs(ev.rawY - mActionDownY) > TOUCH_SLOP){
                            isTouchMoveViewRectY = true
                        }
                        if (isSliderSelectTouch && isTouchMoveViewRectX) {
                            isSliderTouchMove = true
                            notifyMoveIng()
                            mDistance += (ev.rawX - mActionDownX).toInt()
                            mActionDownX = ev.rawX
                            layoutSlider(
                                mSliderView,
                                mSliderLocation[0] - parLocation[0] + mDistance - (mSliderView.measuredWidth * 0.6f).toInt(),
                                0
                            )
                            return true
                        } else {
                            return false
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        var handler = false
                        if (isSliderSelectTouch && isSliderTouchMove) {
                            dispatchSliderView(false)
                            handler = true
                        }
                        if (!isTouchMoveViewRectX && !isTouchMoveViewRectY) {
                            handler = true
                            var par = parent
                            while (par != null) {
                                if (par is ViewGroup) {
                                    par as ViewGroup
                                    if (par.hasOnClickListeners()) par.performClick()
                                    par = par.parent
                                } else {
                                    par = null
                                }
                            }
                        }
                        reset()
                        return handler
                    }

                    MotionEvent.ACTION_CANCEL -> {
                        if (isSliderSelectTouch && isSliderTouchMove) {
                            dispatchSliderView(true)
                        }
                        notifyCancel()
                        reset()
                        return false
                    }
                }
            }
        }
        return false
    }

    private fun reset() {
        mActionDownX = 0f
        mDistance = 0
        mSliderLocation[0] = 0
        mSliderLocation[1] = 0
        parLocation[0] = 0
        parLocation[1] = 0
        isSliderTouchMove = false
        isSliderSelectTouch = false
        isSliderAnimationing = false
        isTouchMoveViewRectX = false
        isTouchMoveViewRectY = false
    }


    private fun dispatchSliderView(cancle: Boolean) {
        val sliderRight =
            mSliderLocation[0] - parLocation[0] + mDistance + (mSliderView.measuredWidth * 0.4f).toInt()
        val sliderLeft =
            mSliderLocation[0] - parLocation[0] + mDistance - (mSliderView.measuredWidth * 0.6f).toInt()
        if (abs(mViewWidth - mSliderSpace - sliderRight) < 10) {
            notifySuccess()
        } else if (sliderRight > mViewWidth * mUnLockToClick) {
            animationToRectLocation(sliderLeft, mViewWidth - mSliderSpace, cancle, true)
        } else {
            animationToRectLocation(sliderLeft, 0, cancle, false)
        }
    }

    private fun animationToRectLocation(form: Int, to: Int, canel: Boolean, unlockStatus: Boolean) {
        val translationxs = intArrayOf(form, to)
        var animation = ValueAnimator.ofInt(*translationxs)
        animation.addUpdateListener {
            layoutSlider(mSliderView, it.animatedValue.toString().toInt() - mSliderSpace, 0)
        }
        animation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                isSliderAnimationing = false
                if (!canel) {
                    if (unlockStatus) {
                        notifySuccess()
                    } else {
                        notifyFailed()
                    }
                }
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        })
        animation.interpolator = LinearInterpolator()
        animation.duration = animatioDuration
        animation.start()
        isSliderAnimationing = true
    }


    private fun isTouchSliderView(downX: Int): Boolean {
        getLocationOnScreen(parLocation)
        mSliderView?.let {
            it.getLocationOnScreen(mSliderLocation)
            return mSliderLocation[0] - mSliderSpace <= downX && downX <= mSliderLocation[0] + mSliderView.measuredWidth
        }
        return false
    }

    private fun isTouchView(downX: Int, downY: Int): Boolean {
        if (parLocation[0] <= downX && downX <= parLocation[0] + measuredWidth
            && parLocation[1] <= downY && downY <= parLocation[1] + measuredHeight
        ) {
            return true
        }
        return false
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawBg(canvas)
        drawText(canvas)
    }

    private fun drawBg(canvas: Canvas?) {
        canvas?.let { canvas ->
            mRectBg.set(
                mBgPaintStrokeWidth, mBgPaintStrokeWidth,
                mViewWidth - mBgPaintStrokeWidth, mViewHeight - mBgPaintStrokeWidth
            )
            mBgPaint.color = mBgStrokeColor
            mBgPaint.style = Paint.Style.STROKE
            canvas.drawRoundRect(mRectBg, mViewHeight / 2f, mViewHeight / 2f, mBgPaint)
            canvas.save()
            mBgPaint.color = mBgPaintColor
            mBgPaint.style = Paint.Style.FILL
            canvas.drawRoundRect(mRectBg, mViewHeight / 2f, mViewHeight / 2f, mBgPaint)
            canvas.restore()
        }
    }

    private fun drawText(canvas: Canvas?) {
        canvas?.let { canvas ->
            mTextPaint.color = mTextColor
            mTextPaint.textSize = mTextHeight
            mTextPaint.typeface = Typeface.DEFAULT_BOLD
            val textHeight = mTextHeight
            val textWidth = mTextPaint.measureText(mTextDes)
            val top = (mViewHeight - textHeight) / 2
            var minRect = Rect()
            mTextPaint.getTextBounds(mTextDes, 0, mTextDes.length, minRect)
            val baseLineY = top + abs(minRect.top)
            val baseLineX = (mViewWidth - textWidth) / 2
            canvas.drawText(mTextDes, baseLineX, baseLineY, mTextPaint)
        }
    }


    fun notifyMoveIng() {
        if (status != UNLOCK_MOVEING) {
            status = UNLOCK_MOVEING
            mlockListener?.unlocling()
        }

    }

    fun notifySuccess() {
        if (status != UNLOCK_SUCCESS) {
            status = UNLOCK_SUCCESS
            mlockListener?.success()
        }
    }

    fun notifyFailed() {
        if (status != UNLOCK_FAILED) {
            status = UNLOCK_FAILED
            mlockListener?.failed()
        }
    }

    fun notifyCancel() {
        if (status != UNLOCK_CANCEL) {
            status = UNLOCK_CANCEL
            mlockListener?.cancle()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mlockListener = null
    }
}

interface SliderLockListener {
    fun success()
    fun unlocling()
    fun failed()
    fun cancle()
}