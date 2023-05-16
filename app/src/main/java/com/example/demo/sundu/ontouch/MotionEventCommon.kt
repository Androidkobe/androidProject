package com.example.demo.sundu.ontouch
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.core.view.forEach

 fun ViewGroup.actionDownChildView(motionEvent: MotionEvent?):Boolean{
     motionEvent?.let {
         forEach { child ->
             if (motionEvent.action == MotionEvent.ACTION_DOWN){
                 child.setTag(-1,false)
             }

             var x = motionEvent.rawX.toInt()
             var y = motionEvent.rawY.toInt()
             var location = IntArray(2)
             child.getLocationOnScreen(location)
             var viewRect = Rect(location[0],location[1],location[0]+child.width,location[1]+child.height)
             if (viewRect.contains(x,y)){
                 //重点：事件分发在ViewGroup中会进行一次转换 产生view的相对坐标 而非直接传入全局坐标
                 val offsetX = scrollX - child.left
                 val offsetY = scrollY - child.top
                 motionEvent.offsetLocation(offsetX.toFloat(), offsetY.toFloat())
                 var result = child.dispatchTouchEvent(motionEvent)
                 motionEvent.offsetLocation(-offsetX.toFloat(), -offsetY.toFloat())
                 if (motionEvent.action == MotionEvent.ACTION_MOVE){
                     child.setTag(-1,true)
                 }
                 return result
             }else{
                 child.getTag(-1)?.let {
                     if( it as Boolean){
                         //重点：事件分发在ViewGroup中会进行一次转换 产生view的相对坐标 而非直接传入全局坐标
                         val offsetX = scrollX - child.left
                         val offsetY = scrollY - child.top
                         motionEvent.offsetLocation(offsetX.toFloat(), offsetY.toFloat())
                         child.dispatchTouchEvent(motionEvent)
                         motionEvent.offsetLocation(-offsetX.toFloat(), -offsetY.toFloat())
                    }
                 }
             }
         }
     }
    return false
}