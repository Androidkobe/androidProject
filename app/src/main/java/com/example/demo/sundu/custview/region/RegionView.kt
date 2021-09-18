package com.example.demo.sundu.custview.region

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


class RegionView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val rect1 = Rect(100, 100, 400, 200)
        val rect2 = Rect(200, 0, 300, 300)
        var paint = Paint();
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f
        paint.isAntiAlias = true;

        canvas?.apply {
            drawRect(rect1, paint)
            drawRect(rect2, paint)
        }

        val region = Region(rect1)
        val region2 = Region(rect2)
        region.op(region2, Region.Op.INTERSECT)

        var paintFill = Paint()
        paintFill.color = Color.GREEN

        paintFill.style = Paint.Style.FILL

        drawRegion(canvas, region, paintFill);

    }

    fun drawRegion(canvas: Canvas?, region: Region, paint: Paint) {

        val iter = RegionIterator(region)
        val rect = Rect();
        while (iter.next(rect)) {
            canvas?.drawRect(rect, paint)
        }

    }
}