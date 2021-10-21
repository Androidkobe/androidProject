package com.example.demo.sundu.custview.SliderView

import android.graphics.Rect
import android.os.SystemClock
import android.util.Log
import android.view.MotionEvent
import android.view.View
import java.util.*

abstract class GesturesDispatchAdapter() {

    val TAG = "sundu"

    private var mOwnerSelfHandlerEventView: WeakHashMap<View, Boolean> = WeakHashMap()

    private var viewMap: WeakHashMap<View, Boolean> = WeakHashMap()

    private var viewGlobalRectMap: TreeMap<Rect, View> = TreeMap(RectComparator())

    abstract fun getViews(): Array<View?>?

    /**
     * 此方案是解决 事件分发时
     * 在分发View中的x y 坐标数据 和 实际View 中的getx 和 getY 之间存在差异 导致 滑动时 无法触发点击事件
     *
     *原因是在View的onTouchEvent中处理Move时：
     * // Be lenient about moving outside of buttons
    if (!pointInView(x, y, touchSlop)) {  //使用当前event时 会被判定为已超出当前View 所以会取消点击事件
    // Outside button
    // Remove any future long press/tap checks
    removeTapCallback();
    removeLongPressCallback();
    if ((mPrivateFlags & PFLAG_PRESSED) != 0) {
    setPressed(false);
    }
    mPrivateFlags3 &= ~PFLAG3_FINGER_DOWN;
    }
     *
     * 目前方案：构造新的event，遇到自己处理事件的使用原有的event
     * 当遇到自己处理event 事件时 这个View 需要使用raw* 来处理所有的坐标问题
     */
    abstract fun getOwnerSelfHandlerEventViews(): Array<View?>?

    private fun initViewCacheMap() {

        var views = getViews()
        views?.let {
            for (view in views) {
                view?.let {
                    viewMap[view] = false
                }
            }
        }

        var viewEvents = getOwnerSelfHandlerEventViews()
        viewEvents?.let {
            for (view in viewEvents) {
                view?.let {
                    mOwnerSelfHandlerEventView[view] = true
                }
            }
        }

        var iterorMap = viewMap.iterator()
        while (iterorMap.hasNext()) {
            var entry = iterorMap.next()
            var view = entry.key
            view?.let {
                var rect = Rect()
                view.getGlobalVisibleRect(rect)
                rect?.let {
                    viewGlobalRectMap[rect] = view
                }
            }
        }

        var iterorMap1 = viewGlobalRectMap.iterator()
        while (iterorMap1.hasNext()) {
            var entry = iterorMap1.next()
            var rect = entry.key
            Log.d(
                TAG,
                "Gestures down handler to " + " view = " + viewGlobalRectMap[rect]?.javaClass?.toString()
                    ?: null
            )

        }
//        Log.d(TAG,"Gestures adapter data : viewMap :"+viewMap.toString()+"\n"+
//        "viewRectMap :"+viewGlobalRectMap.toString()+"\n"+
//        "ownerSelfView : "+mOwnerSelfHandlerEventView.toString())
    }

    private fun clearViewCacheMap() {
        viewMap.clear()
        viewGlobalRectMap.clear()
        mOwnerSelfHandlerEventView.clear()
    }

    fun disPatchEvent(event: MotionEvent?): Boolean {
        if (event == null || getViews() == null) {
            return false
        }

        if (viewMap.size == 0) {
            initViewCacheMap()
        }

        val time = SystemClock.uptimeMillis()
        var newEvent =
            MotionEvent.obtain(time, time, event.action, 0f, 0f, 0)

        event?.let {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    return disPatchDownEvent(event, newEvent)
                }
                MotionEvent.ACTION_MOVE -> {
                    return disPatchMoveEvent(event, newEvent)
                }
                MotionEvent.ACTION_CANCEL,
                MotionEvent.ACTION_UP -> {
                    return disPatchUpCancelEvent(event, newEvent)
                }
                else -> {
                }
            }
        }
        newEvent.recycle()
        return false
    }

    private fun disPatchDownEvent(event: MotionEvent, newEvent: MotionEvent): Boolean {
        var result = false
        var iterorMap = viewGlobalRectMap.iterator()
        while (iterorMap.hasNext()) {
            var entry = iterorMap.next()
            var rect = entry.key
            var view = entry.value
            rect?.let {
                view?.let {
                    //down 命中view 区域
                    if (rect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                        result = view.dispatchTouchEvent(
                            reSetEventLocation(
                                event,
                                newEvent,
                                rect
                            )
                        )
                        viewMap[view] = result
                    }
                    if (result) {
                        Log.d(
                            TAG,
                            "Gestures down handler to " + " rect = " + rect + " view is =" + view.javaClass.toString()
                        )
                        return true
                    }
                }
            }
        }
        return result
    }

    private fun disPatchMoveEvent(event: MotionEvent, newEvent: MotionEvent): Boolean {
        var result = false
        var iterorMap = viewGlobalRectMap.iterator()
        while (iterorMap.hasNext()) {
            var entry = iterorMap.next()
            var rect = entry.key
            var view = entry.value
            rect?.let {
                view?.let {
                    var down = viewMap[view] ?: false
                    if (down) {
                        if (rect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                            result = view.dispatchTouchEvent(
                                reSetEventLocation(
                                    event,
                                    newEvent,
                                    rect
                                )
                            )
                            if (result) {
                                Log.d(
                                    TAG,
                                    "Gestures move handler to " + " rect = " + rect + " view is =" + view.javaClass.toString()
                                )
                                return true
                            }
                        } else {
                            //滑动出冲突View边界 分发取消action
                            val action = newEvent.action
                            var tempEvent = reSetEventLocation(
                                event,
                                newEvent,
                                rect
                            )
                            tempEvent.action = MotionEvent.ACTION_CANCEL
                            view.dispatchTouchEvent(tempEvent)
                            tempEvent.action = action
                            viewMap[view] = false
                        }
                    }
                }
            }
        }
        return result
    }

    private fun disPatchUpCancelEvent(event: MotionEvent, newEvent: MotionEvent): Boolean {
        var result = false
        var iterorMap = viewGlobalRectMap.iterator()
        while (iterorMap.hasNext()) {
            var entry = iterorMap.next()
            var rect = entry.key
            var view = entry.value
            rect?.let {
                view?.let {
                    var down = viewMap[view] ?: false
                    if (down) {
                        result = view.dispatchTouchEvent(
                            reSetEventLocation(
                                event,
                                newEvent,
                                rect
                            )
                        )
                    }
                }
                viewMap[view] = false
                if (result) {
                    Log.d(
                        TAG,
                        "Gestures up handler to " + " rect = " + rect + " view is =" + view.javaClass.toString()
                    )
                    return true
                }
            }
        }
        clearViewCacheMap()
        return result
    }

    private fun reSetEventLocation(
        event: MotionEvent,
        newEvent: MotionEvent,
        rect: Rect
    ): MotionEvent {
        val rawx = event.rawX
        val rawy = event.rawY
        var view = viewGlobalRectMap[rect]
        rect?.let {
            view?.let {
                if (rect.contains(
                        rawx.toInt(),
                        rawy.toInt()
                    ) && mOwnerSelfHandlerEventView[view] != true
                ) {
                    var x = rawx - rect.left
                    var y = rawy - rect.top
                    Log.d(TAG, "Gestures adapter set new Event $x  $y")
                    newEvent.offsetLocation(x, y)
                    return newEvent
                }
            }
        }
        return event
    }

    open class RectComparator : Comparator<Rect> {
        override fun compare(o1: Rect, o2: Rect): Int {
            val r1 = (o1.right - o1.left) * (o1.bottom - o1.top)
            val r2 = (o2.right - o2.left) * (o2.bottom - o2.top)
            return r1.compareTo(r2)
        }
    }
}