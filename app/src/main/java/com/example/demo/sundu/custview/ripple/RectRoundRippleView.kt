package com.example.demo.sundu.custview.ripple

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import com.example.demo.R

class RectRoundRippleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val defaultWidth = 600
    val defaultHeight = 80

    var mStrokeWidth = 3f
    var mSpace = 50f
    var mRippleLineCount = 2

    var mBgStrokeColor = Color.parseColor("#ff0000")

    var mRippleColor = Color.parseColor("#00ff00")

    var mBgPaint = Paint()
    var mRipplePaint = Paint()
    var mRectBg = RectF()

    var startColor = Color.parseColor("#64000000")

    var endColor = Color.parseColor("#FF6900")

    var mBgPaintColor = endColor

    var mViewWidth = defaultWidth
    var mViewHeight = defaultHeight

    var xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)

    var mRoundRippleList = mutableListOf<Ripple>()

    lateinit var loadBitmap: Bitmap

    private var drawBitmap: Bitmap? = null

    var bitMapOffsetx = 0f

    var breatheLineWidth = 0f

    init {
        mRipplePaint.style = Paint.Style.STROKE
        mRipplePaint.color = mRippleColor
        mRipplePaint.strokeWidth = 2f
        loadBitmap = BitmapFactory.decodeResource(resources, R.mipmap.button_shed)
    }

    fun setPaintColor(color: Int) {
        mBgPaintColor = color
        invalidate()
    }

    fun setXfermod(mode: PorterDuff.Mode) {
        xfermode = PorterDuffXfermode(mode)
        invalidate()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMeasureSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMeasureSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthMeasure = MeasureSpec.getSize(widthMeasureSpec)
        val heightMeasure = MeasureSpec.getSize(heightMeasureSpec)
        when (widthMeasureSpecMode) {
            MeasureSpec.AT_MOST -> {
                mViewWidth = defaultWidth
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
        if (mViewHeight > 0 && mViewWidth > 0) {
            initRipple()
        }
    }

    fun initRipple() {
        mRoundRippleList.clear()
        var rect = RectF()
        rect.set(
            getRippleRectFMargin(),
            getRippleRectFMargin(),
            mViewWidth - getRippleRectFMargin(),
            mViewHeight - getRippleRectFMargin()
        )
        mRoundRippleList.add(Ripple(rect, 0f, 255))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBreatheLine(canvas)
        drawBg(canvas)
        drawBitmap(canvas)
        drawRippleView(canvas)
    }

    private fun drawBreatheLine(canvas: Canvas) {
        canvas?.let { canvas ->
            mRectBg.set(
                getBreathRectFMargin(),
                getBreathRectFMargin(),
                mViewWidth - getBreathRectFMargin(),
                mViewHeight - getBreathRectFMargin()
            )
            mBgPaint.color = mBgStrokeColor
            mBgPaint.style = Paint.Style.FILL
            canvas.drawRoundRect(mRectBg, mViewHeight / 2f, mViewHeight / 2f, mBgPaint)
        }
    }

    private fun drawBitmap(canvas: Canvas) {
        drawBitmap?.let {
            canvas.save()
            val mPath = Path()
            mRectBg.set(
                getBackGroundRectFMargin(),
                getBackGroundRectFMargin(),
                mViewWidth - getBackGroundRectFMargin(),
                mViewHeight - getBackGroundRectFMargin()
            )
            mPath.addRoundRect(mRectBg, mViewHeight / 2f, mViewHeight / 2f, Path.Direction.CCW)
            canvas.clipPath(mPath)
            canvas.drawBitmap(it, bitMapOffsetx, getStockRectFMargin(), Paint())
            canvas.restore()
        }
    }

    private fun drawRippleView(canvas: Canvas) {
        var iterator = mRoundRippleList.iterator()
        while (iterator.hasNext()) {
            var ripple = iterator.next()
            var path = Path()
            if (ripple.Offset - mSpace >= 0) {
                iterator.remove()
            } else {
                mRipplePaint.alpha = (255 - (ripple.Offset / mSpace) * 255f).toInt()
                path.addRoundRect(
                    ripple.rect,
                    mViewHeight / 2f,
                    mViewHeight / 2f,
                    Path.Direction.CCW
                )
                canvas.drawPath(path, mRipplePaint)
            }
        }
    }

    fun startRippleAnimation() {
        var valueAnimator = ValueAnimator.ofFloat(0f, mSpace)
        valueAnimator.duration = 2000
        valueAnimator.interpolator = RippleInterpolator()
        valueAnimator.repeatMode = ValueAnimator.RESTART
        valueAnimator.repeatCount = ValueAnimator.INFINITE
        var preValue = 0f
        valueAnimator.addUpdateListener {
            var currentValue = it.animatedValue as Float
            if (currentValue - preValue < 0) {
                preValue = 0f
            }
            var offset = currentValue - preValue
            preValue = currentValue
            mRoundRippleList.forEach { ripple ->
                ripple.Offset += offset
                ripple.rect.set(
                    getRippleRectFMargin() - ripple.Offset,
                    getRippleRectFMargin() - ripple.Offset,
                    mViewWidth - getRippleRectFMargin() + ripple.Offset,
                    mViewHeight - getRippleRectFMargin() + ripple.Offset
                )
            }

            mRoundRippleList.last()?.let { ripple ->
                if (ripple.Offset >= mSpace / mRippleLineCount) {
                    var rect = RectF()
                    rect.set(
                        getRippleRectFMargin(),
                        getRippleRectFMargin(),
                        mViewWidth - getRippleRectFMargin(),
                        mViewHeight - getRippleRectFMargin()
                    )
                    mRoundRippleList.add(Ripple(rect, 0f, 255))
                }
            }
            invalidate()
        }
        valueAnimator.start()
    }

    fun startSweepLightAnimation() {
        if (drawBitmap == null) {
            val originalWidth = loadBitmap.width
            val originalHeight = loadBitmap.height
            val targetHeight = mViewHeight - 2 * getBackGroundRectFMargin()
            val targetWidth = targetHeight * (originalWidth / originalHeight)
            drawBitmap = Bitmap.createScaledBitmap(
                loadBitmap,
                targetWidth.toInt(),
                targetHeight.toInt(),
                true
            )
        }
        val animator = ValueAnimator.ofFloat(
            getStockRectFMargin() - (drawBitmap?.width ?: 0),
            mViewWidth - 2 * getStockRectFMargin()
        )
        animator.duration = 2000 // 设置动画持续时间为2秒
        animator.repeatMode = ValueAnimator.RESTART
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { animation ->
            bitMapOffsetx = animation.animatedValue as Float
            invalidate() // 重新绘制视图
        }
        animator.start()
    }

    fun startBreathAnimation() {
        val animator = ValueAnimator.ofFloat(0f, 30f, 0f)
        animator.duration = 2000
        animator.repeatMode = ValueAnimator.RESTART
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { animation ->
            breatheLineWidth = animation.animatedValue as Float
            invalidate() // 重新绘制视图
        }
        animator.start()
    }

    fun startGradientAnimation() {
        val animator = ObjectAnimator.ofArgb(
            this, "paintColor", startColor, endColor
        )
        animator.duration = 2000
        animator.repeatMode = ValueAnimator.RESTART
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()
    }

    private fun drawBg(canvas: Canvas?) {
        canvas?.let { canvas ->
            mRectBg.set(
                getStockRectFMargin(),
                getStockRectFMargin(),
                mViewWidth - getStockRectFMargin(),
                mViewHeight - getStockRectFMargin()
            )
            mBgPaint.color = mBgStrokeColor
            mBgPaint.style = Paint.Style.STROKE
            mBgPaint.strokeWidth = mStrokeWidth
            canvas.drawRoundRect(mRectBg, mViewHeight / 2f, mViewHeight / 2f, mBgPaint)

            canvas.save()
            //贴着stoke边进行背景绘制
            mRectBg.set(
                getBackGroundRectFMargin(),
                getBackGroundRectFMargin(),
                mViewWidth - getBackGroundRectFMargin(),
                mViewHeight - getBackGroundRectFMargin()
            )
            mBgPaint.color = mBgPaintColor
            mBgPaint.style = Paint.Style.FILL
            canvas.drawRoundRect(mRectBg, mViewHeight / 2f, mViewHeight / 2f, mBgPaint)
            canvas.restore()
        }
    }

    private fun getRippleRectFMargin(): Float {
        return mStrokeWidth / 2 + mSpace
    }

    private fun getStockRectFMargin(): Float {
        return mStrokeWidth + mSpace
    }

    private fun getBackGroundRectFMargin(): Float {
        return getStockRectFMargin() + mStrokeWidth / 2
    }

    private fun getBreathRectFMargin(): Float {
        return mStrokeWidth + mSpace - breatheLineWidth
    }
}

class RippleInterpolator : android.view.animation.Interpolator {
    override fun getInterpolation(input: Float): Float {
        return input
    }

}

class Ripple constructor(var rect: RectF, var Offset: Float, var alpha: Int) {}