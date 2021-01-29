package com.example.demo.sundu.developer.nineimageload

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.drawable.NinePatchDrawable
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_developer_nine_img_load.*
import java.io.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * .9图片制作
 * 在build-tools\26.02(版本号) 目录下打开cmd. 例如我目录: C:\Users\dell\AppData\Local\Android\sdk\build-tools\26.0.2.
 * 然后输入命令.\aapt s -i .9图文件位置 -o 转换后文件位置.
 * 例如: 源文件a.9.png 和 转换后的文件b.png都在\sdk\build-tools\26.0.2目录下. 那我直接在cmd里面输入: .\aapt s -i .\a.9.png -o .\b.png 就会生成b.png图片
 */
class NineImgLoadActivity : AppCompatActivity() {
    val TAG = "NineImgLoadActivity"
    val sdkPath = Environment.getExternalStorageDirectory().path
    val handles = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer_nine_img_load)
        image.setImageDrawable(resources.getDrawable(R.mipmap.bg_splash))

        val displayMetrics = resources.displayMetrics
        val density = displayMetrics.density //屏幕密度

        val densityDpi = displayMetrics.densityDpi //屏幕密度dpi

        val heightPixels = displayMetrics.heightPixels //屏幕高度的像素

        val widthPixels = displayMetrics.widthPixels //屏幕宽度的像素

        val scaledDensity = displayMetrics.scaledDensity //字体的放大系数

        val xdpi = displayMetrics.xdpi //宽度方向上的dpi

        val ydpi = displayMetrics.ydpi //高度方向上的dpi

        Log.i(TAG, "density = $density")
        Log.i(TAG, "densityDpi = $densityDpi")
        Log.i(TAG, "scaledDensity = $scaledDensity")
        Log.i(TAG, "Screen resolution = $widthPixels×$heightPixels")
        Log.i(TAG, "xdpi = $xdpi")
        Log.i(TAG, "ydpi = $ydpi")

//        image.setImageDrawable(BitmapDrawable.createFromPath(sdkPath+"/"+"b.png"))
        handles.sendEmptyMessage(1)
       handles.postDelayed({

           setPlaceHolderImage(sdkPath+"/"+"b.png")
       },5000)
    }

    fun setPlaceHolderImage(localPath: String) {
        val bitmap  = getBitMapFromFile(localPath)
        try {
            if (bitmap != null && bitmap.ninePatchChunk != null) {
                val metrics = DisplayMetrics()
                val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
                wm.defaultDisplay.getMetrics(metrics)
                val rect = Rect(0, 0, metrics.widthPixels, metrics.heightPixels)
                val ninePatchDrawable = NinePatchDrawable(bitmap, bitmap.ninePatchChunk, rect, null)
                image.setImageDrawable(ninePatchDrawable)
                Toast.makeText(this, ".9图片加载!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "普通图片加载!", Toast.LENGTH_SHORT).show()
                image.setImageBitmap(bitmap)
            }
        } catch (e: Exception) {
            Log.e("sundu",e.toString())
        }
    }


    fun getBitMapFormUitlFile(localPath: String):Bitmap?{
        val value = TypedValue()
        value.density = creatBitMapOption()?.inDensity?:480
        var bitmap:Bitmap? = null
        var input :InputStream ? = null
        try {
             input = FileInputStream(File(localPath))
             bitmap = BitmapFactory.decodeResourceStream(resources, value, input, null, null)!!
        } catch (e: java.lang.Exception) {

        } finally {
            try {
                 input?.close()
            } catch (e: IOException) {
                // Ignore
            }
        }
        return bitmap
    }

    fun getBitMapFromFile(localPath: String?): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val metrics = DisplayMetrics()
            val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            wm.defaultDisplay.getMetrics(metrics)
            val rect = Rect(0, 0, metrics.widthPixels, metrics.heightPixels)
            bitmap = BitmapFactory.decodeStream(FileInputStream(File(localPath)), rect, creatBitMapOption())
//            bitmap = BitmapFactory.decodeResource(resources,R.mipmap.bg_splash)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return bitmap
    }


    fun creatBitMapOption(): BitmapFactory.Options? {
        val options = BitmapFactory.Options()
        options.inScaled = true
        var mDesity = 480
        if (resources.displayMetrics.density != null) {
            val density = resources.displayMetrics.density
            mDesity = if (density <= 0.75) {
                DisplayMetrics.DENSITY_LOW
            } else if (0.75 < density && density <= 1.0) {
                DisplayMetrics.DENSITY_MEDIUM
            } else if (1.0 < density && density <= 1.5) {
                DisplayMetrics.DENSITY_HIGH
            } else if (1.5 < density && density < 2.0) {
                DisplayMetrics.DENSITY_XHIGH
            } else if (2.0 <= density && density <= 3.0) {
                DisplayMetrics.DENSITY_XXHIGH
            } else if (3.0 < density && density <= 4.0) {
                DisplayMetrics.DENSITY_XXXHIGH
            } else {
                DisplayMetrics.DENSITY_XXXHIGH
            }
        }
        options.inDensity = mDesity
        options.inTargetDensity = resources.displayMetrics.densityDpi
        return options
    }
}