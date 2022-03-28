package com.example.demo.sundu.recycleview.imtalk

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.R.attr.dividerHeight
import android.util.Log


class LineItemDecoration: RecyclerView.ItemDecoration() {

    var paint = Paint()

    init {
        paint.color = Color.RED
    }
    /**
     * 绘制背景
     */
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val childCount: Int = parent.childCount
        val left: Int = parent.paddingLeft
        val right: Int = parent.width - parent.paddingRight

        for (i in 0 until childCount - 1) {
            val view: View = parent.getChildAt(i)
            val top = view.bottom.toFloat()
            val bottom = (view.bottom + dividerHeight).toFloat()
            paint.color = Color.parseColor("#0000ff")
            c.drawRect(left.toFloat(), top, right.toFloat(), bottom, paint)
            Log.e("sundu","line item decoration 绘制背景 $i")
        }
    }

    /**
     * itemView 上绘制
     */
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val tagWidth = 20
        val childCount: Int = parent.getChildCount()
        for (i in 0 until childCount) {
            val child: View = parent.getChildAt(i)
            val pos: Int = parent.getChildAdapterPosition(child)
            val isLeft = pos % 2 == 0
            if (isLeft) {
                val left = child.left.toFloat()
                val right: Float = left + tagWidth
                val top = child.top.toFloat()
                val bottom = child.bottom.toFloat()
                paint.color = Color.parseColor("#ff0000")
                c.drawRect(left, top, right, bottom, paint)
                Log.e("sundu","line item decoration 绘制left 标签 $i")
            } else {
                val right = child.right.toFloat()
                val left: Float = right - tagWidth
                val top = child.top.toFloat()
                val bottom = child.bottom.toFloat()
                paint.color = Color.parseColor("#00ff00")
                c.drawRect(left, top, right, bottom, paint)
                Log.e("sundu","line item decoration 绘制right 标签 $i")
            }
        }
    }

    /**
     * itemView 位置
     */
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = 2
    }
}