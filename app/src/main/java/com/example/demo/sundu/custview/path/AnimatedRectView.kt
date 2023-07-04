package com.example.demo.sundu.custview.path

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class AnimatedRectView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val paint: Paint = Paint()
    private val arcRect: RectF = RectF()
    private var startAngle = 0f
    private val sweepAngle = 70f
    private val animationDuration = 2000L // 动画时长，单位为毫秒
    private val startColor = Color.RED
    private val endColor = Color.GREEN
    private val startTime: Long = System.currentTimeMillis()
    private var mMatrix = Matrix()

    private var colorList = IntArray(6)

    private var postionList = FloatArray(6)

    init {
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 20f
        paint.strokeCap = Paint.Cap.ROUND

        // 创建线性渐变
        val width = width
        val height = height
        val radius = 80
        val centerX = width / 2
        val centerY = height / 2
        colorList[0] = Color.parseColor("#00FFFFFF")
        colorList[1] = Color.parseColor("#22FFFFFF")
        colorList[2] = Color.parseColor("#55FFFFFF")
        colorList[3] = Color.parseColor("#FFFFFFFF")
        colorList[4] = Color.parseColor("#EEFFFFFF")
        colorList[5] = Color.TRANSPARENT

        postionList[0] = 0f
        postionList[1] = 0.05f
        postionList[2] = 0.1f
        postionList[3] = 0.18f
        postionList[4] = 0.19f
        postionList[5] = 1f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 获取控件的宽度和高度
        val width = width
        val height = height

        // 计算扇形的位置
        val radius = 190
        val centerX = width / 2
        val centerY = height / 2
        arcRect.set(
            (centerX - radius).toFloat(),
            (centerY - radius).toFloat(),
            (centerX + radius).toFloat(),
            (centerY + radius).toFloat()
        )

        canvas.save()
        // 更新渐变矩阵
        mMatrix.reset()
        mMatrix.setRotate(startAngle, centerX.toFloat(), centerY.toFloat())
        canvas.setMatrix(mMatrix)
        // 设置画笔颜色为渐变
        paint.shader = SweepGradient(centerX * 1f, centerY * 1f, colorList, postionList)
        // 绘制扇形
        canvas.drawArc(arcRect, 0f, 70f, false, paint)
        canvas.restore()

        canvas.save()
        // 更新渐变矩阵
        mMatrix.reset()
        mMatrix.setRotate(startAngle + 180, centerX.toFloat(), centerY.toFloat())
        canvas.setMatrix(mMatrix)
        // 设置画笔颜色为渐变
        paint.shader = SweepGradient(centerX * 1f, centerY * 1f, colorList, postionList)
        // 绘制扇形
        canvas.drawArc(arcRect, 0f, 70f, false, paint)
        canvas.restore()

        // 更新起始角度
        startAngle += 5f // 每次增加的角度，控制旋转速度

        if (startAngle > 360) {
            startAngle = 0f
        }
        // 强制重绘，形成动画效果
        invalidate()
    }
}