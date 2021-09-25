package com.example.demo.sundu.custview.SliderView

import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import java.util.*
import kotlin.math.abs

abstract class GesturesDispatchAdapter (context:Context) {

    val TOUCH_SLOP = ViewConfiguration.get(context).scaledTouchSlop

    private var viewMap : WeakHashMap<View,Boolean> = WeakHashMap()

    private var viewRectMap : WeakHashMap<View,Rect> = WeakHashMap()

    var isMove = false
    var mDownX = 0f
    var mDownY = 0f

    abstract fun getViews(): Array<View?>?

    init {
        Log.e("sundu","初始化 adapter")
        var views = getViews()
        views?.let {
            for (view in views){
                view?.let {
                    viewMap[view] = false
                }
            }
        }
    }

    private fun initViewRect(){
        var iterorMap = viewMap.iterator()
        while (iterorMap.hasNext()){
            var entry = iterorMap.next()
            var view = entry.key
            if (!viewRectMap.containsKey(view)){
                var rect = Rect()
                view.getGlobalVisibleRect(rect)
                Log.e("sundu","${rect.toString()}")
                viewRectMap[view] = rect
            }
        }
    }

    fun disPatchEvent(event: MotionEvent?): Boolean {
        if (event == null || getViews() == null) {
            return false
        }
        if (viewRectMap.size == 0 ){
            initViewRect()
        }
        event?.let {
            when (event.action) {
                MotionEvent.ACTION_DOWN->{
                    mDownX = event.rawX
                    mDownY = event.rawY
                    return disPatchDownEvent(event)
                }
                MotionEvent.ACTION_MOVE->{
                    if (abs(event.rawX - mDownX) > TOUCH_SLOP
                        || abs(event.rawY - mDownY) > TOUCH_SLOP){
                        isMove = true
                        return disPatchMoveEvent(event)
                    }
                    return false
                }
                MotionEvent.ACTION_CANCEL,
                MotionEvent.ACTION_UP->{
                    isMove = false
                     return disPatchUpCancelEvent(event)
                }
                else ->{}
            }
        }
        return false
    }

    private fun disPatchDownEvent(event: MotionEvent):Boolean{
        var result = false
        var iterorMap = viewMap.iterator()
        while (iterorMap.hasNext()){
            var entry = iterorMap.next()
            var view = entry.key
            var rect = viewRectMap[view]
            view?.let {
                rect?.let {
                    if (rect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                        result = view.dispatchTouchEvent(event)
                        Log.e("sundu","down view = "+view.javaClass.name)
                        viewMap[view] = result
                    }
                }
            }
        }
        return result
    }

    private fun disPatchMoveEvent(event: MotionEvent):Boolean{
        var result = false
        var iterorMap = viewMap.iterator()
        while (iterorMap.hasNext()){
            var entry = iterorMap.next()
            var view = entry.key
            var down = entry.value
            var rect = viewRectMap[view]
            view?.let {
                rect?.let {
                    if(down){
                        if (rect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                            result = view.dispatchTouchEvent(event)
                            Log.e("sundu","move view = "+view.javaClass.name)
                        }else{
                            //滑动出冲突View边界 分发取消action
                            val action = event.action
                            event.action = MotionEvent.ACTION_CANCEL
                            view.dispatchTouchEvent(event)
                            event.action = action
                            viewMap[view] = false
                            Log.e("sundu","cancel view = "+view.javaClass.name)
                        }
                    }
                }
            }
        }
        return result
    }

    private fun disPatchUpCancelEvent(event: MotionEvent):Boolean{
        var result = false
        var iterorMap = viewMap.iterator()
        while (iterorMap.hasNext()){
            var entry = iterorMap.next()
            var view = entry.key
            var down = entry.value
            view?.let {
                if(down){
                    result = view.dispatchTouchEvent(event)
//                    ReflectionUtils.setFieldValue(view,"mHasPerformedLongPress",false)
                    ReflectionUtils.setFieldValue(view,"mIgnoreNextUpEvent",false)
                    Log.e("sundu","up view = "+view.javaClass.name)
                }
                viewMap[view] = false
            }
        }
        return result
    }

}