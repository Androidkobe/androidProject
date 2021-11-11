package com.example.demo.sundu.jetpack.databinding.bidirectional

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.databinding.InverseBindingMethod
import androidx.databinding.InverseBindingMethods

@InverseBindingMethods(
    value = [
        InverseBindingMethod(
            type = com.example.demo.sundu.jetpack.databinding.bidirectional.CustomTimeView::class,
            attribute = "android:time",
            event = "android:timeAttrChanged",
            method = "getCustomTime()"
        )
    ]
)
class CustomTimeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var time = "2020-01-30"
        set(value) {
            field = value
            timeChangeList?.onTimeChange(time)
            invalidate()
        }
    var paint = Paint()
    var textPaint = TextPaint()
    var timeChangeList: ITimeChangeListener? = null

    init {
        paint.textSize = 100f
        paint.isAntiAlias = true
        textPaint.textSize = 100f
        textPaint.measureText(time)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val y = textPaint.fontMetrics.descent - textPaint.fontMetrics.ascent
        canvas?.drawText(time, 20f, y, paint)
    }

    fun getCustomTime(): String {
        return time
    }

}
