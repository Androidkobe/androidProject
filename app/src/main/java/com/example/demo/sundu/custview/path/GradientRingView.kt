package com.example.demo.sundu.custview.path

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class GradientRingView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 8f
    }
    private lateinit var gradientShader: Shader

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)

        val centerX = width.toFloat() / 2
        val centerY = height.toFloat() / 2
        val radius = (width.toFloat() - paint.strokeWidth) / 2

        // 创建渐变着色器
        val startColor = Color.RED
        val endColor = Color.BLUE
        gradientShader = SweepGradient(centerX, centerY, startColor, endColor)

        // 创建旋转矩阵
        val matrix = Matrix()
        matrix.setRotate(-90f, centerX, centerY)
        gradientShader.setLocalMatrix(matrix)

        // 设置着色器到画笔
        paint.shader = gradientShader
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width.toFloat() / 2
        val centerY = height.toFloat() / 2
        val radius = (width.toFloat() - paint.strokeWidth) / 2

        // 画圆环
        canvas.drawCircle(centerX, centerY, radius, paint)
    }
}
