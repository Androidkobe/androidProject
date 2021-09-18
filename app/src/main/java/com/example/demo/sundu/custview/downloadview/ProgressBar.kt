package com.example.demo.sundu.custview.downloadview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class ProgressBar @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    var bgPaint = Paint()
    var prPaint = Paint()

    var bgRect = RectF()
    var prRect = Rect()

    var mHeight = 100;

    var radius = mHeight / 2f;

    var progress = 0

    init {
        bgPaint.color = Color.RED
        bgPaint.style = Paint.Style.STROKE
        bgPaint.isAntiAlias = true
        bgPaint.strokeWidth = 3f

        prPaint.color = Color.YELLOW
        prPaint.style = Paint.Style.FILL
        prPaint.isAntiAlias = true
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMeasureSpaceMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthMeasure = MeasureSpec.getSize(widthMeasureSpec)
        val heightMeasureSpaceMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightMeasure = MeasureSpec.getSize(heightMeasureSpec)
        when (heightMeasureSpaceMode) {
            MeasureSpec.AT_MOST,
            MeasureSpec.UNSPECIFIED -> {
                mHeight = 100
            }
            MeasureSpec.EXACTLY -> {
                mHeight = heightMeasure
            }
        }
        setMeasuredDimension(widthMeasure, mHeight)
        radius = mHeight.toFloat()
    }

    fun setCurrentProgress(progress: Int) {
        this.progress = if (progress > 100) {
            100
        } else {
            progress
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawBg(canvas)
        drawProgress(canvas)
    }

    private fun drawProgress(canvas: Canvas?) {
        canvas?.save()
        //裁剪画布
        val mPath = Path()
        var radiusii = floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius)
        mPath.addRoundRect(bgRect, radiusii, Path.Direction.CCW)
        canvas?.clipPath(mPath)
        //画矩形图形
        val right = (progress / 100f) * (measuredWidth)
        prRect.set(0, 0, right.toInt(), mHeight)
        canvas?.drawRect(prRect, prPaint)
        canvas?.restore()
    }

    private fun drawBg(canvas: Canvas?) {
        bgRect.set(
            bgPaint.strokeWidth,
            bgPaint.strokeWidth,
            measuredWidth.toFloat() - bgPaint.strokeWidth,
            mHeight.toFloat() - bgPaint.strokeWidth
        )
        canvas?.drawRoundRect(bgRect, radius, radius, bgPaint)
    }


}