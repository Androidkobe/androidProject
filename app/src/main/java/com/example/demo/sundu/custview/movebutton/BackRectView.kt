package com.example.demo.sundu.custview.movebutton

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_process_lottie.view.*

class BackRectView(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    //默认大小
    val defaultWidth = dpChangepx(363f)
    val defaultHeight = dpChangepx(109f)

    var mViewWidth = defaultWidth
    var mViewHeight = defaultHeight

    var textAdd = false

    var mBgPaint = Paint()
    //按钮背景色
    var mBgColor = Color.parseColor("#000000")
    var mBgColorAlpha = 0.4*255
    //背景区域
    var mRectBg = RectF()

    //边缘线框宽度
    var mStrokeWidth = dpChangepx(3.6f)
    var mStrokeColor = Color.parseColor("#ffffff")
    var mStockAlpha = 0.3*255

    var mImv : ImageView? = null

    var leftSpace = 0
    var rightSpace = 0
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMeasureSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMeasureSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthMeasure = MeasureSpec.getSize(widthMeasureSpec)
        val heightMeasure = MeasureSpec.getSize(heightMeasureSpec)
        when (widthMeasureSpecMode) {
            MeasureSpec.AT_MOST -> {
                mViewWidth = defaultWidth
            }
            MeasureSpec.UNSPECIFIED,
            MeasureSpec.EXACTLY -> {
                mViewWidth = widthMeasure.toFloat()
            }
        }
        when (heightMeasureSpecMode) {
            MeasureSpec.AT_MOST -> {
                mViewHeight = defaultHeight
            }
            MeasureSpec.UNSPECIFIED,
            MeasureSpec.EXACTLY -> {
                mViewHeight = heightMeasure.toFloat()
            }
        }
        if(mViewHeight > 0 && !textAdd){
            addTextView((mViewHeight*0.75f).toInt())
            textAdd = true
        }
    }

    private fun addTextView(size:Int){
        var space = (mViewHeight - size)/2

        var leftTv = TextView(context)
        var layoutParamsLeft = LayoutParams(size,size)
        layoutParamsLeft.topMargin = space.toInt()
        layoutParamsLeft.leftMargin = space.toInt()
        leftTv.gravity = Gravity.CENTER
        addView(leftTv,layoutParamsLeft)

        var rightTv = TextView(context)
        var layoutParamsRight = LayoutParams(size,size)
        layoutParamsRight.topMargin = space.toInt()
        layoutParamsRight.leftMargin = (mViewWidth - space-size).toInt()
        rightTv.gravity = Gravity.CENTER
        addView(rightTv,layoutParamsRight)

        leftTv.text = "查看\n详情"
        rightTv.text = "查看\n详情"

        leftTv.setTextColor(Color.parseColor("#95ffffff"))
        rightTv.setTextColor(Color.parseColor("#95ffffff"))
        leftTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12f)
        rightTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12f)
        leftTv.background = resources.getDrawable(R.drawable.splash_double_text_bg)
        rightTv.background = resources.getDrawable(R.drawable.splash_double_text_bg)

        mImv = ImageView(context)
        var imgLayoutParams = FrameLayout.LayoutParams(size,size)
        imgLayoutParams.topMargin = space.toInt()
        imgLayoutParams.leftMargin = ((mViewWidth - size)/2).toInt()
        addView(mImv,imgLayoutParams)
        mImv?.setImageResource(R.mipmap.s)

        leftSpace = ((mViewWidth - size)/2).toInt()
        rightSpace = (mViewWidth-leftSpace-size).toInt()
    }

    fun setProcess(ori:Int,process:Float){
        var left = 0
        if (ori == 1){
            left = (leftSpace*process).toInt()
        }
        if (ori == 2){
            left = leftSpace+(rightSpace*process).toInt()
        }
        mImv?.let {
            (it.layoutParams as FrameLayout.LayoutParams).leftMargin = left
            requestLayout()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBg(canvas)
    }

    /**
     * 绘制基础背景 绘制呼吸动效
     */
    private fun drawBg(canvas: Canvas?) {
        canvas?.let { canvas ->
            mRectBg.set(
                getStockRectFMargin(),
                getStockRectFMargin(),
                mViewWidth - getStockRectFMargin(),
                mViewHeight - getStockRectFMargin()
            )
            mBgPaint.color = mStrokeColor
            mBgPaint.style = Paint.Style.STROKE
            mBgPaint.alpha = mStockAlpha.toInt()
            mBgPaint.strokeWidth = mStrokeWidth
            canvas.drawRoundRect(mRectBg, mViewHeight / 2f, mViewHeight / 2f, mBgPaint)

            //贴着stoke边进行背景绘制
            mRectBg.set(
                getBackGroundRectFMargin(),
                getBackGroundRectFMargin(),
                mViewWidth - getBackGroundRectFMargin(),
                mViewHeight - getBackGroundRectFMargin()
            )
            mBgPaint.color = mBgColor
            mBgPaint.style = Paint.Style.FILL
            canvas.drawRoundRect(mRectBg, mViewHeight / 2f, mViewHeight / 2f, mBgPaint)
        }
    }

    private fun getStockRectFMargin(): Float {
        return mStrokeWidth
    }

    private fun getBackGroundRectFMargin(): Float {
        return getStockRectFMargin() + mStrokeWidth / 2
    }


    private fun dpChangepx(configSize: Float): Float {
        val displayMetrics: DisplayMetrics = getResources().getDisplayMetrics()
        val px = Math.round(configSize * displayMetrics.density).toInt()
        return px.toFloat()
    }
}