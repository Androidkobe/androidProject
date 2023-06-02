package com.example.demo.sundu.custview.bgcolorchange

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

class GradientBackgroundView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val defaultWidth = 600
    val defaultHeight = 80

    var mBgPaintStrokeWidth = 10f
    var mBgStrokeColor = Color.parseColor("#80FFFFFF")

    var mBgPaint = Paint()
    var mRectBg = RectF()

    var startColor = Color.parseColor("#64000000")

    var endColor = Color.parseColor("#FF6900")

    var mBgPaintColor = startColor

    var mViewWidth = defaultWidth
    var mViewHeight = defaultHeight

    fun setPaintColor(color: Int) {
        mBgPaintColor = color
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
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBg(canvas)
    }


    private fun drawBg(canvas: Canvas?) {
        canvas?.let { canvas ->
//            mRectBg.set(
//                mBgPaintStrokeWidth, mBgPaintStrokeWidth,
//                mViewWidth - mBgPaintStrokeWidth, mViewHeight - mBgPaintStrokeWidth
//            )
//            mBgPaint.color = mBgStrokeColor
//            mBgPaint.style = Paint.Style.STROKE
//            canvas.drawRoundRect(mRectBg, mViewHeight / 2f, mViewHeight / 2f, mBgPaint)
//            canvas.save()
//
//            mBgPaint.color = mBgPaintColor
//            mBgPaint.style = Paint.Style.FILL
//            canvas.drawRoundRect(mRectBg, mViewHeight / 2f, mViewHeight / 2f, mBgPaint)
//            canvas.restore()
            var path = Path()
            var rectF = RectF()
            rectF.set(
                mBgPaintStrokeWidth, mBgPaintStrokeWidth,
                mViewWidth - mBgPaintStrokeWidth, mViewHeight - mBgPaintStrokeWidth
            )
            path.addRoundRect(rectF, mViewHeight / 2f, mViewHeight / 2f, Path.Direction.CCW)
            mBgPaint.color = mBgStrokeColor
            mBgPaint.style = Paint.Style.STROKE
            canvas.drawPath(path, mBgPaint)

        }
    }

    fun startGradientAnimation() {
        val animator = ObjectAnimator.ofArgb(
            this, "paintColor", startColor, endColor
        )
        animator.duration = 2000
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()
    }
}
