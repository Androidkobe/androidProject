package com.example.demo.sundu.recycleview.citylist

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class StickyDecoration : RecyclerView.ItemDecoration {

    private var callback: DecorationCallback? = null
    private var textPaint: TextPaint? = null
    private var paint: Paint? = null
    private var topGap = 0
    private var fontMetrics: Paint.FontMetrics? = null

    constructor(context: Context, callback: DecorationCallback) {
        this.callback = callback
        paint = Paint()
        paint?.color = Color.RED
        fontMetrics = Paint.FontMetrics()

        textPaint = TextPaint()
        textPaint?.typeface = Typeface.DEFAULT_BOLD
        textPaint?.isAntiAlias = true
        textPaint?.textSize = 80f
        textPaint?.color = Color.BLACK
        textPaint?.getFontMetrics(fontMetrics)
        textPaint?.textAlign = Paint.Align.LEFT
        topGap = 64

    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
    }

    var i = 0
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val itemCount = state.itemCount
        val childCount: Int = parent.childCount
        val left: Int = parent.paddingLeft
        val right: Int = parent.width - parent.paddingRight
        Log.e("sundu", "item cont = $itemCount + childCount = $childCount")
        var preGroupId: String = ""
        var groupId: String = ""
        for (i in 0 until childCount) {
            val view: View = parent.getChildAt(i)
            val position: Int = parent.getChildAdapterPosition(view)
            preGroupId = groupId
            //上一个的组名
            groupId = callback!!.getGroupString(position)
            if (groupId == preGroupId) continue
            //组名不能为空
            val textLine: String = callback!!.getGroupString(position)
            if (TextUtils.isEmpty(textLine)) continue
            //当前View 新组员的头结点
            val viewBottom = view.bottom
            var textY = Math.max(topGap, view.top).toFloat()
            if (position + 1 < itemCount) {
                val nextGroupId: String = callback!!.getGroupString(position + 1)
                if (nextGroupId != groupId && viewBottom < textY) { //组内最后一个view进入了header
                    //设置跟随移动
                    textY = viewBottom.toFloat()
                }
            }
            c.drawRect(left.toFloat(), textY - topGap, right.toFloat(), textY, paint!!)
            c.drawText(textLine, left.toFloat(), textY, textPaint!!)
        }

    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val pos: Int = parent.getChildAdapterPosition(view)
        if (pos == 0 || isFirstInGroup(pos)) { //同组的第一个才添加padding
            outRect.top = topGap
        } else {
            outRect.top = 0
        }
    }

    private fun isFirstInGroup(pos: Int): Boolean {
        return if (pos == 0) {
            true
        } else {
            val prevGroupId = callback?.getGroupString(pos - 1)
            val groupId = callback?.getGroupString(pos)
            return !prevGroupId.equals(groupId)
        }
    }
}