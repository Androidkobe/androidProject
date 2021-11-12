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
            type = CustomTimeView::class,
            attribute = "time",
            event = "timeAttrChanged",
            method = "getCustomTime"
        )
    ]
)
class CustomTimeView : View {

    var time = "2020-01-30"
        set(time) {
            field = time
            timeChangeList?.onTimeChange(time)
            invalidate()
        }
    var timeChangeList: ITimeChangeListener? = null

    var paint = Paint()
    var textPaint = TextPaint()

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        0
    )

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

