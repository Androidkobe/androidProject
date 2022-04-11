package com.example.demo.sundu.recycleview.customlayoutone

import android.util.Log
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
        if (state.itemCount <= 0 || state.isPreLayout) {
            return
        }
        if (childCount <= 0) {
            detachAndScrapAttachedViews(recycler)
            fillLayoutChild(recycler, state, 0, 0, getAbleHeight())
        }
    }


    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        var scrolloffect = dy
        if (scrolloffect > 0) {
            scrolloffect = scrollUp(recycler, state, scrolloffect)
            offsetChildrenVertical(-scrolloffect)
            scrollUpRemoveRecycleView(recycler, state)
        }
        if (scrolloffect < 0) {
            scrolloffect = scrollDown(recycler, state, scrolloffect)
            offsetChildrenVertical(-scrolloffect)
            scrollDownRemoveRecycleView(recycler, state)
        }
        return scrolloffect
    }


    //从上往下开始布局
    fun fillLayoutChild(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        po: Int,
        t: Int,
        scrolloffectSize: Int
    ) {
        var scrolloffect = scrolloffectSize
        var position = po
        var top = t
        while (scrolloffect >= 0 && position < state.itemCount) {
            Log.e("sundu", "开始布局 $position")
            var view = recycler.getViewForPosition(position)
            addView(view)
            measureChildWithMargins(view, 0, 0)
            val with = getDecoratedMeasuredWidth(view)
            val height = getDecoratedMeasuredHeight(view)
            layoutDecorated(view, view.left, top, with, top + height)
            scrolloffect -= height
            top += height
            position += 1
        }
    }

    /**
     * 向上滑动
     */
    fun scrollUp(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        scrolloffect: Int
    ): Int {
        var lastView = getChildAt(childCount - 1)
        lastView?.let {
            if (lastView.bottom >= getAbleHeight()) {
                var lastPostion = getPosition(lastView)
                if (lastPostion == state.itemCount - 1) {
                    var offect = (lastView.bottom - getAbleHeight()).coerceAtMost(scrolloffect)
                    Log.e("sundu", "last view move $offect")
                    return offect
                } else {
                    //底部可滑动距离大于 当前移动距离
                    if (lastView.bottom - getAbleHeight() >= scrolloffect) {
                        Log.e("sundu", "this $lastPostion 在底部 滑动 $scrolloffect")
                        return scrolloffect
                    } else {//底部可滑动距离 小于当前滑动距离
                        Log.e(
                            "sundu",
                            "this ${lastPostion + 1} 在底部 滑动 $scrolloffect + 并需要布局下面 item"
                        )
                        fillLayoutChild(
                            recycler,
                            state,
                            lastPostion + 1,
                            lastView.bottom,
                            scrolloffect
                        )
                        var fillLastView = getChildAt(childCount - 1)
                        fillLastView?.let {
                            var position = getPosition(fillLastView)
                            if (position == state.itemCount - 1) {//最后一个item显示后 不能在往上滑动了
                                return fillLastView.bottom - getAbleHeight()
                            }
                        }
                        return scrolloffect
                    }
                }
            } else {
                return 0
            }
        }
        return 0
    }

    //向上滑动结束开始回收 视图
    fun scrollUpRemoveRecycleView(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (state.itemCount > 0) {
            var recycleStop = false
            while (!recycleStop) {
                var topView = getChildAt(0)
                if (topView != null && topView.bottom < 0) {
                    removeAndRecycleView(topView, recycler)
                    Log.e("sundu", "回收 ${getPosition(topView)}")
                } else {
                    recycleStop = true
                }
            }
        }
    }


    fun scrollDown(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        scrolloffect: Int
    ): Int {
        var firstView = getChildAt(0)
        firstView?.let {
            var firstPostion = getPosition(firstView)
            Log.e("sundu", "this $firstPostion 滑动 $scrolloffect")
            if (firstPostion > 0) {
                if (firstView.top < scrolloffect) {
                    Log.e("sundu", "this $firstPostion 在顶部 滑动 $scrolloffect")
                    return scrolloffect
                } else {
                    fillLayoutChildFormBottom(
                        recycler,
                        firstPostion - 1,
                        firstView.top,
                        scrolloffect
                    )
                    var fillLastView = getChildAt(0)
                    fillLastView?.let {
                        var position = getPosition(fillLastView)
                        if (position == 0) {
                            return fillLastView.top
                        }
                    }
                    return scrolloffect
                }
            } else if (firstPostion == 0 && firstView.top < 0) {
                return Math.max(firstView.top, scrolloffect)
            }
        }
        return 0
    }

    /**
     * 从顶部往上布局
     */
    fun fillLayoutChildFormBottom(
        recycler: RecyclerView.Recycler,
        po: Int,
        tp: Int,
        scrolloffectSize: Int
    ) {
        var scrolloffect = scrolloffectSize
        var position = po
        var top = tp
        while (scrolloffect <= 0 && position >= 0) {
            Log.e("sundu", "开始布局 向上 $position")
            var view = recycler.getViewForPosition(position)
            addView(view, 0)
            measureChildWithMargins(view, 0, 0)
            val with = getDecoratedMeasuredWidth(view)
            val height = getDecoratedMeasuredHeight(view)
            layoutDecorated(view, view.left, top - height, with, top)
            scrolloffect += height
            top -= height
            position -= 1
        }
    }


    //向下滑动结束开始回收 视图
    fun scrollDownRemoveRecycleView(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (state.itemCount > 0) {
            var recycleStop = false
            while (!recycleStop) {
                var bottomView = getChildAt(childCount - 1)
                if (bottomView != null && bottomView.top > getAbleHeight()) {
                    removeAndRecycleView(bottomView, recycler)
                    Log.e("sundu", "下边界 回收 ${getPosition(bottomView)}")
                } else {
                    recycleStop = true
                }
            }
        }
    }
}

