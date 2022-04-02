package com.example.demo.sundu.recycleview.customlayoutone

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

    fun getAbleHeight(): Int {
        return height - paddingBottom - paddingTop
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)
        if (state.itemCount <= 0) {
            return
        }
        detachAndScrapAttachedViews(recycler)
        isCanScrollVertically(recycler, state, 0)
    }


    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        if (isCanScrollVertically(recycler, state, dy)) {
            offsetChildrenVertical(-dy)
        }
        return dy
    }


    fun isCanScrollVertically(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        offset: Int
    ): Boolean {
        if (childCount == 0) {
            fillLayoutChildDown(recycler, state, 0, 0)
        } else {
            if (offset > 0) {//向下滚动
                if (isCanScrollDown(state)) {

                }

            } else {//向上滚动

            }
        }

        return false
    }

    //判断是否可以向下滚动
    fun isCanScrollDown(state: RecyclerView.State): Boolean {
        var view = getChildAt(childCount - 1)
        var itemCount = state.itemCount
        view?.let {
            val positon = getPosition(view)
            if (positon < itemCount) {
                return true
            }
            return positon == itemCount && view.bottom > getAbleHeight()

        } ?: let {
            return false
        }
        return false
    }


    fun fillLayoutChildDown(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        position: Int,
        topOffset: Int
    ) {
        var visiavleHeight = getAbleHeight() + topOffset
        var tagTopOffset = topOffset
        var tagPosition = position
        var tagMaxPosition = state.itemCount - 1
        while (visiavleHeight >= 0 && tagPosition <= tagMaxPosition) {
            var view = recycler.getViewForPosition(tagPosition)
            addView(view)
            measureChildWithMargins(view, 0, 0)
            val with = getDecoratedMeasuredWidth(view)
            val height = getDecoratedMeasuredHeight(view)
            layoutDecorated(view, view.left, tagTopOffset, with, tagTopOffset + height)
            visiavleHeight -= height
            tagTopOffset += height
            tagPosition += 1
        }
    }


    fun fillLayoutChildUp(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        position: Int
    ) {


    }

}

