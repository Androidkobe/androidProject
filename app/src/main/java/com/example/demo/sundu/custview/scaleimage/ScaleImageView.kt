package com.example.demo.sundu.custview.scaleimage

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.appcompat.widget.AppCompatImageView


class ScaleImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet) :
    AppCompatImageView(context, attrs), OnTouchListener {

    private var isCanTouch = false
    private var oldDist = 0.0
    private var moveDist = 0.0
    private var downX1 = 0f
    private var downX2 = 0f
    private var downY1 = 0f
    private var downY2 = 0f

    fun setIsCanTouch(canTouch: Boolean) {
        isCanTouch = canTouch
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        Log.d(TAG, "onTouch isCanTouch= $isCanTouch")
        val pointerCount = event.pointerCount
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_UP -> if (pointerCount == 2) {
                downX1 = 0f
                downY1 = 0f
                downX2 = 0f
                downY2 = 0f
            }
            MotionEvent.ACTION_MOVE -> if (pointerCount == 2) {
                val x1 = event.getX(0)
                val x2 = event.getX(1)
                val y1 = event.getY(0)
                val y2 = event.getY(1)
                val changeX1 = (x1 - downX1).toDouble()
                val changeX2 = (x2 - downX2).toDouble()
                val changeY1 = (y1 - downY1).toDouble()
                val changeY2 = (y2 - downY2).toDouble()
                if (scaleX > 1) { //滑动
                    val lessX = (changeX1 / 2 + changeX2 / 2).toFloat()
                    val lessY = (changeY1 / 2 + changeY2 / 2).toFloat()
                    setSelfPivot(-lessX, -lessY)
                    Log.d(TAG, "此时为滑动")
                }
                //缩放处理
                moveDist = spacing(event)
                val space = moveDist - oldDist
                val scale = (scaleX + space / v.width).toFloat()
                if (scale < SCALE_MIN) {
                    setScale(SCALE_MIN)
                } else if (scale > SCALE_MAX) {
                    setScale(SCALE_MAX)
                } else {
                    setScale(scale)
                }
            }
            MotionEvent.ACTION_POINTER_DOWN -> if (pointerCount == 2) {
                downX1 = event.getX(0)
                downX2 = event.getX(1)
                downY1 = event.getY(0)
                downY2 = event.getY(1)
                Log.d(
                    TAG, "ACTION_POINTER_DOWN 双指按下 downX1=" + downX1 + " downX2="
                            + downX2 + "  downY1=" + downY1 + " downY2=" + downY2
                )
                oldDist = spacing(event) //两点按下时的距离
            }
            MotionEvent.ACTION_POINTER_UP -> Log.d(TAG, "ACTION_POINTER_UP")
            else -> {}
        }
        return true
    }

    /**
     * 触摸使用的移动事件
     *
     * @param lessX
     * @param lessY
     */
    private fun setSelfPivot(lessX: Float, lessY: Float) {
        var setPivotX = 0f
        var setPivotY = 0f
        setPivotX = pivotX + lessX
        setPivotY = pivotY + lessY
        if (setPivotX < 0 && setPivotY < 0) {
            setPivotX = 0f
            setPivotY = 0f
        } else if (setPivotX > 0 && setPivotY < 0) {
            setPivotY = 0f
            if (setPivotX > width) {
                setPivotX = width.toFloat()
            }
        } else if (setPivotX < 0 && setPivotY > 0) {
            setPivotX = 0f
            if (setPivotY > height) {
                setPivotY = height.toFloat()
            }
        } else {
            if (setPivotX > width) {
                setPivotX = width.toFloat()
            }
            if (setPivotY > height) {
                setPivotY = height.toFloat()
            }
        }
        setPivot(setPivotX, setPivotY)
    }

    /**
     * 计算两个点的距离
     *
     * @param event
     * @return
     */
    private fun spacing(event: MotionEvent): Double {
        return if (event.pointerCount == 2) {
            val x = event.getX(0) - event.getX(1)
            val y = event.getY(0) - event.getY(1)
            Math.sqrt((x * x + y * y).toDouble())
        } else {
            0.00
        }
    }

    /**
     * 平移画面，当画面的宽或高大于屏幕宽高时，调用此方法进行平移
     *
     * @param x
     * @param y
     */
    fun setPivot(x: Float, y: Float) {
        pivotX = x
        pivotY = y
    }

    /**
     * 设置放大缩小
     *
     * @param scale
     */
    fun setScale(scale: Float) {
        scaleX = scale
        scaleY = scale
    }

    /**
     * 初始化比例，也就是原始比例
     */
    fun setInitScale() {
        scaleX = 1.0f
        scaleY = 1.0f
        setPivot((width / 2).toFloat(), (height / 2).toFloat())
    }

    companion object {
        const val TAG = "ScaleImageView"
        const val SCALE_MAX = 5.0f //最大的缩放比例
        private const val SCALE_MIN = 1.0f
    }

    init {
        setOnTouchListener(this)
    }
}