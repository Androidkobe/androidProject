package com.example.demo.sundu.custview.path

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class AnimatedRectView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val rectPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 4f
        color = Color.GRAY
    }
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 100f
        strokeCap = Paint.Cap.ROUND
        shader = createGradientShader()
    }

    private var lineOffset = 0f
    private val animator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 2000
        repeatMode = ValueAnimator.REVERSE
        repeatCount = ValueAnimator.INFINITE
        addUpdateListener { valueAnimator ->
            lineOffset = valueAnimator.animatedFraction
            invalidate()
        }
    }

    init {
        animator.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator.cancel()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val radius = height / 2f
        val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        canvas.drawRoundRect(rect, radius, radius, rectPaint)

        val startX = 0f
        val endX = width.toFloat()
        val y = height / 2f
        canvas.drawLine(startX, y, endX, y, linePaint.apply {
            pathEffect = createPathEffect(lineOffset, endX - startX)
        })
    }

    private fun createGradientShader(): Shader {
        val colors = intArrayOf(Color.RED, Color.BLUE)
        return LinearGradient(0f, 0f, 100f, 0f, colors, null, Shader.TileMode.CLAMP)
    }

    private fun createPathEffect(offset: Float, length: Float): PathEffect {
        return DashPathEffect(floatArrayOf(length, length), offset * length)
    }

    private fun Float.dpToPx(): Float {
        val scale = resources.displayMetrics.density
        return this * scale
    }
}
