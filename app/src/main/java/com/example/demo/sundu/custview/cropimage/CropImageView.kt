package com.example.demo.sundu.custview.cropimage

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView

class CropImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var cropFloat = 0f
    val paint = Paint()
    var fullBitmap: Bitmap? = null
    var cropRect = RectF()
    var outputWidth = 0f
    var outputHeight = 0f

    init {
        paint.isAntiAlias = true
        paint.color = Color.BLUE
    }

    override fun onDraw(canvas: Canvas) {
        val drawable = drawable
        if (drawable == null) {
            super.onDraw(canvas)
            return
        }
        if (width == 0 || height == 0) {
            return
        }
        fullBitmap ?: let {
            val bitmap = (drawable as BitmapDrawable).bitmap
            fullBitmap = getFullBitmap(bitmap)
        }
        fullBitmap?.let {
            canvas.save()
            cropRect.set(0f, 0f, width * cropFloat, height.toFloat())
            canvas.clipRect(cropRect)
            canvas.drawBitmap(it, 0f, 0f, null)
            canvas.restore()
            paint.strokeWidth = 10f
            paint.color = Color.BLACK
            canvas.drawLine(width * cropFloat, 0f, width * cropFloat, outputHeight, paint)
        }
    }

    private fun getFullBitmap(bitmap: Bitmap): Bitmap {
        outputWidth = width.toFloat()
        outputHeight = width / (bitmap.width.toFloat() / bitmap.height.toFloat())
        val output =
            Bitmap.createBitmap(outputWidth.toInt(), outputHeight.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val rect = RectF(0f, 0f, outputWidth, outputHeight)
        canvas.drawBitmap(bitmap, null, rect, paint)
        return output
    }

    fun setAngle(angle: Float) {
        cropFloat = angle
        invalidate()
        Log.e("sundu", "$angle")
    }
}