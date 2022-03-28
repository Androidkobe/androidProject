package com.example.demo.sundu.recycleview.normaluse.itemdecoration

import android.graphics.Canvas
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MyDecoration :RecyclerView.ItemDecoration() {

    //先绘制  出现在试图下方
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        Log.e("sundu","onDraw ---- ")
    }

    //后绘制 出现在试图上方
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        Log.e("sundu","onDrawOver ---- ")
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        Log.e("sundu","getItemOffsets ---- ${(view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition} ")
        outRect.set(0,0,0,10)
    }
}