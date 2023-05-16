package com.example.demo.sundu.ontouch

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.example.demo.R

open class MyViewGroup : FrameLayout {

    private var firstHierarchy : ChildEventInterface? = null

    private var secondHierarchy : ChildEventInterface? = null

    private var threeHierarchy : ChildEventInterface? = null

    private var fourHierarchy : ChildEventInterface? = null

    private var DOWN_KEY_STORE = -1

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.e("sundu", "MyViewGroup view dispatchTouchEvent action " + ev?.action)
        //所有action 都会经过第三层
        ev?.let {
            when(it.action){
                MotionEvent.ACTION_DOWN->{
                    setHierarchyDownTag(firstHierarchy,false)
                    setHierarchyDownTag(secondHierarchy,false)
                    setHierarchyDownTag(fourHierarchy,false)
                    //第一层
                    var firstHierarchyResult = firstHierarchy?.dispatchEvent(ev)
                    setHierarchyDownTag(firstHierarchy,firstHierarchyResult)
                    if (firstHierarchyResult == false){
                        //第二层
                        var secondHierarchyResult = secondHierarchy?.dispatchEvent(ev)
                        setHierarchyDownTag(secondHierarchy,secondHierarchyResult)
                        if (secondHierarchyResult == false){
                            //第三层
                            threeHierarchy?.dispatchEvent(ev)
                            //第四层
                            setHierarchyDownTag(fourHierarchy,fourHierarchy?.dispatchEvent(ev))
                        } else {

                        }
                    } else {

                    }
                }
                MotionEvent.ACTION_MOVE->{
                    if (!actionMoveUpEvent(firstHierarchy,ev)){
                        if (!actionMoveUpEvent(secondHierarchy,ev)){
                            if (threeHierarchy?.dispatchEvent(ev) == false){
                                 actionMoveUpEvent(fourHierarchy,ev)
                            } else {
                                //第三层开始处理事件，第四层屏蔽其
                                setHierarchyDownTag(fourHierarchy,false)
                                ev.action = MotionEvent.ACTION_CANCEL
                                fourHierarchy?.dispatchEvent(ev)
                            }
                        } else {

                        }
                    } else {

                    }
                }
                MotionEvent.ACTION_CANCEL,
                MotionEvent.ACTION_UP->{
                    actionMoveUpEvent(firstHierarchy,ev)
                    actionMoveUpEvent(secondHierarchy,ev)
                    threeHierarchy?.dispatchEvent(ev)
                    actionMoveUpEvent(fourHierarchy,ev)
                }
                else -> {}
            }
        }
        return true
    }


    private fun actionMoveUpEvent(childHierarchy : ChildEventInterface?,ev: MotionEvent?):Boolean{
        var result = false
        childHierarchy?.let { hier ->
            (hier as ViewGroup).getTag(DOWN_KEY_STORE)?.let { tag->
                if (tag as Boolean){
                    result = hier.dispatchEvent(ev)
                }
             }
        }
        return result
    }

    private fun setHierarchyDownTag(childHierarchy : ChildEventInterface?,tag:Boolean?){
        childHierarchy?.let { hierarchy->
            (hierarchy as ViewGroup).setTag(DOWN_KEY_STORE,tag)
        }
    }


    override fun onViewAdded(child: View?) {
        super.onViewAdded(child)
        child?.let {
            when (it) {
                is FirstViewGroup -> firstHierarchy = it
                is SecondViewGroup -> secondHierarchy = it
                is ThreeViewGroup -> threeHierarchy = it
                is FourViewGroup -> fourHierarchy = it
            }
        }
    }
}