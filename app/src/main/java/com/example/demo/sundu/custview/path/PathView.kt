package com.example.demo.sundu.custview.path

import android.R
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView


class PathView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val stroke_width = 6f
    private val margin = 26
    private val stk_er: Float
    private val mPaint: Paint
    private val radiuRightTop = 100
    private val mLength: Float
    private val mLength2: Float
    private var mAnimatorValue = 0f
    private val mPathMeasure: PathMeasure
    private val mPathMeasure2: PathMeasure
    private val bitmap: Bitmap
    private val path: Path
    private val path2: Path
    private val pos: FloatArray
    private val tan: FloatArray
    private val pos1: FloatArray
    private val tan1: FloatArray
    private var canvas2: Canvas? = null
    private var bitmapCanvas: Bitmap? = null
    private val x: Int
    private val y: Int

    init {
        stk_er = stroke_width / 2
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.setStyle(Paint.Style.STROKE)
        mPaint.setStrokeWidth(stroke_width)
        mPaint.setColor(Color.WHITE)

        // 得到原始的图片
//        int x = getWidth();
//        int y = getHeight();

        //放入事先的宽高
        x = dip2px(getContext(), 300f)
        y = dip2px(getContext(), 100f)
        path = Path()
        path.moveTo(x - stk_er - margin, y / 2f)
        path.lineTo(x - stk_er - margin, radiuRightTop.toFloat())
        path.quadTo(
            x - margin.toFloat(),
            0 + margin.toFloat(),
            x - margin.toFloat() - radiuRightTop,
            0 + stk_er + margin
        )
        path.lineTo(radiuRightTop + margin.toFloat(), 0 + stk_er + margin)
        path.quadTo(
            0 + margin.toFloat(),
            0 + margin.toFloat(),
            0 + stk_er + margin,
            radiuRightTop.toFloat()
        )
        path.lineTo(0 + stk_er + margin, y / 2f)
        path2 = Path()
        path2.moveTo(0 + stk_er + margin, y / 2f)
        path2.lineTo(0 + stk_er + margin, y - radiuRightTop.toFloat())
        path2.quadTo(
            0 + margin.toFloat(),
            y - margin.toFloat(),
            radiuRightTop + margin.toFloat(),
            y - stk_er - margin
        )
        path2.lineTo(x - radiuRightTop - margin.toFloat(), y - stk_er - margin)
        path2.quadTo(
            x - margin.toFloat(),
            y - margin.toFloat(),
            x - stk_er - margin,
            y - radiuRightTop.toFloat()
        )
        path2.lineTo(x - stk_er - margin, y / 2f)
        mPathMeasure = PathMeasure()
        mPathMeasure.setPath(path, false)
        mLength = mPathMeasure.length
        mPathMeasure2 = PathMeasure()
        mPathMeasure2.setPath(path2, false)
        mLength2 = mPathMeasure2.length
        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.addUpdateListener { valueAnimator ->
            mAnimatorValue = valueAnimator.animatedValue as Float
            invalidate()
        }
        valueAnimator.duration = 2000
        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator.start()
        pos = FloatArray(2)
        tan = FloatArray(2)
        pos1 = FloatArray(2)
        tan1 = FloatArray(2)
        val imageView = ImageView(context)
        imageView.setImageResource(R.drawable.alert_dark_frame)


//        imageView.setDrawingCacheEnabled(true);
//        bitmap = Bitmap.createBitmap(imageView.getDrawingCache());
//        imageView.setDrawingCacheEnabled(false);

//        imageView.setBackground(context.getResources().getDrawable(R.drawable.line));
//
//        Drawable shape = getResources().getDrawable(R.drawable.line);
//        bitmap = Bitmap.createBitmap(shape.getIntrinsicWidth(),
//                shape.getIntrinsicHeight(),
//                shape.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
//
//        Canvas canvas = new Canvas(bitmap);
//        shape.setBounds(0, 0, shape.getIntrinsicWidth(), shape.getIntrinsicHeight());
//        shape.draw(canvas);


//        bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.alert_dark_frame)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas != null) {
            canvas.save()
            canvas.translate(0f, 0f)
            canvas.drawPath(path, mPaint)
            mPathMeasure.getPosTan(mLength * mAnimatorValue, pos, tan)
            val degrees =
                (Math.atan2(tan[1].toDouble(), tan[0].toDouble()) * 180.0 / Math.PI).toFloat()
            canvas.rotate(degrees, pos[0], pos[1])
            val left = pos[0] - bitmap.width / 2
            val top = pos[1] - bitmap.height / 2
            //        float top = pos[1] - bitmap.getHeight() ;// 不除2会始终悬浮在 线上  俩种效果可以看一下
            canvas.drawBitmap(bitmap, left, top, mPaint)
            canvas.restore()
            bitmapCanvas = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888)
            canvas2 = Canvas(bitmapCanvas!!)
            canvas2?.save()
            canvas2?.translate(0f, 0f)
            canvas2?.drawPath(path2, mPaint)
            mPathMeasure2.getPosTan(mLength2 * mAnimatorValue, pos1, tan1)
            val degrees2 =
                (Math.atan2(tan1[1].toDouble(), tan1[0].toDouble()) * 180.0 / Math.PI).toFloat()
            canvas2?.rotate(degrees2, pos1[0], pos1[1])
            val left1 = pos1[0] - bitmap.width / 2
            val top1 = pos1[1] - bitmap.height / 2
            //        float top = pos[1] - bitmap.getHeight() ;// 不除2会始终悬浮在 线上  俩种效果可以看一下
            canvas2?.drawBitmap(bitmap, left1, top1, mPaint)
            canvas.drawBitmap(bitmapCanvas!!, 0f, 0f, null)
            canvas2?.restore()
        }
    }

    companion object {
        fun dip2px(context: Context, dpValue: Float): Int {
            val scale: Float = context.getResources().getDisplayMetrics().density
            return (dpValue * scale + 0.5f).toInt()
        }
    }
}