package com.example.demo.sundu.custview.path

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class GradientCircleView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val path = Path()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 8f
    }
    private lateinit var gradientShader: Shader

    init {
        // 创建圆的路径
        val centerX = 300f
        val centerY = 300f
        val radius = 200f
        path.addCircle(centerX, centerY, radius, Path.Direction.CW)

        // 创建渐变着色器
        val startColor = Color.RED
        val endColor = Color.BLUE
        gradientShader = LinearGradient(
            centerX - radius, centerY, centerX + radius, centerY,
            startColor, endColor, Shader.TileMode.CLAMP
        )

        // 设置着色器到画笔
        paint.shader = gradientShader
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 画圆
        canvas.drawPath(path, paint)
    }
}
