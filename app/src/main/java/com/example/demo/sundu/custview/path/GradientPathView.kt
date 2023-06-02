package com.example.demo.sundu.custview.path

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class GradientPathView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val path = Path()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 8f
    }
    private lateinit var gradientShader: Shader

    init {
        // 创建路径
        path.moveTo(100f, 100f)
        path.lineTo(300f, 300f)
        path.lineTo(500f, 200f)

        // 创建渐变着色器
        val startColor = Color.RED
        val endColor = Color.BLUE
        gradientShader = LinearGradient(
            100f, 100f, 500f, 200f,
            startColor, endColor, Shader.TileMode.CLAMP
        )

        // 设置着色器到画笔
        paint.shader = gradientShader
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 画路径
        canvas.drawPath(path, paint)
    }
}
