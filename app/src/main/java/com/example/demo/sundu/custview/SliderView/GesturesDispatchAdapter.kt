package com.example.demo.sundu.custview.SliderView

import android.graphics.Rect
import android.view.MotionEvent
import android.view.View

abstract class GesturesDispatchAdapter {

    private var isHandlerActionDown = false

    abstract fun getViews(): Array<View>?

    fun disPatchEvent(event: MotionEvent?): Boolean {
        if (event == null || getViews() == null) {
            return false
        }
        getViews()?.let {
            var rect = Rect()
            for (view in it) {
                view?.let {
                    view.getGlobalVisibleRect(rect)
                    rect?.let {
                        when (event.action) {
                            MotionEvent.ACTION_DOWN -> {
                                if (rect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                                    isHandlerActionDown = view.dispatchTouchEvent(event)
                                    return isHandlerActionDown
                                }
                            }
                            MotionEvent.ACTION_MOVE -> {
                                if (isHandlerActionDown) {
                                    return if (rect.contains(
                                            event.rawX.toInt(),
                                            event.rawY.toInt()
                                        )
                                    ) {
                                        view.dispatchTouchEvent(event)
                                    } else {
                                        //滑动出冲突View边界 分发取消action
                                        val action = event.action
                                        event.action = MotionEvent.ACTION_CANCEL
                                        view.dispatchTouchEvent(event)
                                        event.action = action
                                        isHandlerActionDown = false
                                        false
                                    }
                                }
                            }
                            MotionEvent.ACTION_UP -> {
                                //接收处理down事件 必须接收处理up事件
                                if (isHandlerActionDown) {
                                    isHandlerActionDown = false
                                    return view.dispatchTouchEvent(event)
                                }
                            }
                        }
                    }
                }
            }
        }
        return false
    }
}