package com.example.demo.sundu.custview.cropimage

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class CropImageView2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    val paint = Paint()
    var fullBitmap: Bitmap? = null

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
            canvas.drawBitmap(it, 0f, 0f, null)
        }
    }

    private fun getFullBitmap(bitmap: Bitmap): Bitmap {
        val outputWidth = width.toFloat()
        val outputHeight = width / (bitmap.width.toFloat() / bitmap.height.toFloat())
        val output =
            Bitmap.createBitmap(outputWidth.toInt(), outputHeight.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val rect = RectF(0f, 0f, outputWidth, outputHeight)
        canvas.drawBitmap(bitmap, null, rect, paint)
        return output
    }
}