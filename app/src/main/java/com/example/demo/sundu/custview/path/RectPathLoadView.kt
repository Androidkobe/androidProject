package com.example.demo.sundu.custview.path

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator


class RectPathLoadView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    val defaultWidth = 600
    val defaultHeight = 80

    var mStockWidth = 10f

    var mStockColor = Color.parseColor("#80000000")
    var mRunStockColor = Color.parseColor("#ffffff")

    var mPathMeasure = PathMeasure()

    var mStockPaint = Paint()
    var mStockRect = RectF()
    var mStockPath = Path()

    var mRunStockPaint = Paint()
    var mRunStockPath = Path()

    var lineOffset = 0f
    var lineLength = 80f
    var pathLength = 0f

    var mViewWidth = defaultWidth
    var mViewHeight = defaultHeight

    var gradient: SweepGradient? = null

    var linearGradient: LinearGradient? = null

    init {
        mStockPaint.color = mStockColor
        mStockPaint.style = Paint.Style.STROKE
        mStockPaint.strokeWidth = mStockWidth

        mRunStockPaint.color = mRunStockColor
        mRunStockPaint.style = Paint.Style.STROKE
        mRunStockPaint.strokeWidth = mStockWidth
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
        drawStock(canvas)
        drawStockLeft(canvas)
        drawStockRight(canvas)
        startAnimation()
    }

    var isStart = false
    private fun startAnimation() {
        if (!isStart) {
            var animator = ValueAnimator.ofFloat(0f, pathLength)
            animator.setInterpolator(LinearInterpolator())
            animator.setDuration(3000)
            animator.setRepeatCount(ValueAnimator.INFINITE)
            animator.repeatMode = ValueAnimator.RESTART
            animator.addUpdateListener(AnimatorUpdateListener { animation ->
                lineOffset = animation.animatedValue as Float
                invalidate()
            })
            animator.start()
            isStart = true
        }
    }


    private fun drawStock(canvas: Canvas?) {
        canvas?.let { canvas ->
            mStockRect.set(
                getStockMargin(), getStockMargin(),
                mViewWidth - getStockMargin(), mViewHeight - getStockMargin()
            )
            mStockPath.addRoundRect(
                mStockRect,
                mViewHeight / 2f,
                mViewHeight / 2f,
                Path.Direction.CCW
            )
            if (gradient == null) {
                gradient = SweepGradient(
                    mViewWidth / 2f, mViewHeight / 2f,
                    Color.RED, Color.BLUE
                )
                linearGradient = LinearGradient(
                    0f,
                    0f,
                    lineLength,
                    0f,
                    Color.RED,
                    Color.BLUE,
                    Shader.TileMode.CLAMP
                )
            }
            canvas.drawPath(mStockPath, mStockPaint)
            mPathMeasure.setPath(mStockPath, true)
        }
    }

    private fun drawStockLeft(canvas: Canvas?) {
        canvas?.let { canvas ->
            pathLength = mPathMeasure.length
            mRunStockPaint.shader = linearGradient
            mRunStockPath.reset()
            if (lineOffset <= pathLength - lineLength) {
                mPathMeasure.getSegment(lineOffset, lineOffset + lineLength, mRunStockPath, true)
                canvas.drawPath(mRunStockPath, mRunStockPaint)
            } else {
                val remainingLength: Float = lineOffset + lineLength - pathLength
                mPathMeasure.getSegment(0f, remainingLength, mRunStockPath, true)
                canvas.drawPath(mRunStockPath, mRunStockPaint)
                mRunStockPath.reset()
                mPathMeasure.getSegment(
                    pathLength - (lineLength - remainingLength),
                    pathLength,
                    mRunStockPath,
                    true
                )
                canvas.drawPath(mRunStockPath, mRunStockPaint)
            }

        }
    }

    private fun drawStockRight(canvas: Canvas?) {
        canvas?.let { canvas ->
            mRunStockPaint.shader = linearGradient
            pathLength = mPathMeasure.length
            var rightLineOffset = lineOffset + pathLength / 2

            mRunStockPath.reset()
            if (rightLineOffset <= pathLength - lineLength) {
                mPathMeasure.getSegment(
                    rightLineOffset,
                    rightLineOffset + lineLength,
                    mRunStockPath,
                    true
                )
                canvas.drawPath(mRunStockPath, mRunStockPaint)
            } else if (rightLineOffset <= pathLength) {
                val remainingLength: Float = rightLineOffset + lineLength - pathLength
                mPathMeasure.getSegment(0f, remainingLength, mRunStockPath, true)
                canvas.drawPath(mRunStockPath, mRunStockPaint)
                mRunStockPath.reset()
                mPathMeasure.getSegment(
                    pathLength - (lineLength - remainingLength),
                    pathLength,
                    mRunStockPath,
                    true
                )
                canvas.drawPath(mRunStockPath, mRunStockPaint)
            } else {
                mPathMeasure.getSegment(
                    rightLineOffset - pathLength,
                    rightLineOffset - pathLength + lineLength,
                    mRunStockPath,
                    true
                )
                canvas.drawPath(mRunStockPath, mRunStockPaint)
            }

        }
    }

    private fun getStockMargin(): Float {
        return mStockWidth
    }
}