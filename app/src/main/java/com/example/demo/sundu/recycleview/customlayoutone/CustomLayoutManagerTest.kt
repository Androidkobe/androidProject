package com.example.demo.sundu.recycleview.customlayoutone

import android.graphics.Rect
import android.util.Log
import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CustomLayoutManagerTest : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        );
    }

    override fun canScrollVertically(): Boolean {
        return true
    }

    private var layoutState = LayoutState()

    fun getAbleHeight(): Int {
        return height - paddingBottom - paddingTop
    }


    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)
        if (state.itemCount <= 0) {
            return
        }
        detachAndScrapAttachedViews(recycler)
        fill(recycler, state, layoutState)
    }

    fun fill(recycler: RecyclerView.Recycler, state: RecyclerView.State, layoutState: LayoutState) {
        var vableHeight = getAbleHeight()
        var postion = layoutState.topPostion
        var layoutOffect = layoutState.topOffect
        while (vableHeight > 0 && postion < state.itemCount) {
            Log.e("sundu","vable height $vableHeight + postion = $postion")
            var view = recycler.getViewForPosition(postion)
            if(view.top < 0){
                vableHeight+=-view.top
                layoutOffect = view.top
                layoutState.topOffect = layoutOffect
                if (view.bottom < 0) {
                    removeAndRecycleView(view, recycler)
                    postion += 1
                    layoutState.topPostion = postion
                    view = recycler.getViewForPosition(postion)
                    layoutOffect = view.top
                    layoutState.topOffect = layoutOffect
                }
            }
            addView(view)
            measureChildWithMargins(view, 0, 0)
            val width = getDecoratedMeasuredWidth(view)
            val height = getDecoratedMeasuredHeight(view)
            layoutDecorated(view, 0, layoutOffect, width, layoutOffect + height)
            layoutOffect += height
            vableHeight -= height
            postion += 1
        }
        layoutState.topPostion = postion
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        offsetChildrenVertical(-dy)
        fill(recycler,state,layoutState)
        return dy
    }

    class LayoutState {
        var state: Int = 1;
        var topPostion = 0;
        var topOffect = 0;
    }
}

