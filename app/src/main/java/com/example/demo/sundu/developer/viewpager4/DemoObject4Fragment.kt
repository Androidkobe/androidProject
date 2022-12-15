package com.example.demo.sundu.developer.viewpager4

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.example.demo.R
import kotlinx.android.synthetic.main.developer_viewpage_collection_demo_itemfragment.*


/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */


private const val ARG_OBJECT = "object"

// Instances of this class are fragments representing a single
// object in our collection.
class DemoObject4Fragment : Fragment() {

    var colorsMap = mutableMapOf<Int, Int>()

    var index = 0

    var colorTypeId = R.id.color_main

    var created = false

    val bgColors = arrayOf<Long>(
        0xFFFCA311, 0xFF00B372, 0xFF1A68FF,
        0xFF00C0C7, 0xFFFF3362, 0xFF894DFF,
        0xFF00ABF5, 0xFF19C20C, 0xFFFF6AC1,
        0xFFFF8526, 0xFFF84047, 0xFFC44DFF,
        0xFFA6A6A6,
    )

    val bgColors2 = arrayOf<Long>(
        0xFFF02522, 0xFFFE644C, 0xFFFE8433,
        0xFF19C20C, 0xFF0CC280,0xFF00CCBB,
        0xFF00ABF5, 0xFF6F70FE,0xFF766EFA,
        0xFFC23FFA, 0xFFFF4DC1,0xFFFF3770,
        0xFFA6A6A6,
    )

    val colorAdapterMap = mutableMapOf<ArrayList<Int>,Long>()

    init {
        colorsMap[0] = R.mipmap.res_one
        colorsMap[1] = R.mipmap.res_two
        colorsMap[2] = R.mipmap.res_three
        colorsMap[3] = R.mipmap.res_four
        colorsMap[4] = R.mipmap.res_five
        colorsMap[5] = R.mipmap.res_six
        colorsMap[6] = R.mipmap.res_seven
        colorsMap[7] = R.mipmap.res_eigth
        colorsMap[8] = R.mipmap.res_nine
        colorsMap[9] = R.mipmap.res_ten
        colorsMap[10] = R.mipmap.res_eleven
        colorsMap[11] = R.mipmap.res_twelve
        colorsMap[12] = R.mipmap.res_thirteen

        var list1 = ArrayList<Int>()
        var list2 = ArrayList<Int>()
        var list3 = ArrayList<Int>()
        var list4 = ArrayList<Int>()
        var list5 = ArrayList<Int>()
        var list6 = ArrayList<Int>()
        var list7 = ArrayList<Int>()
        var list8 = ArrayList<Int>()
        var list9 = ArrayList<Int>()
        var list10 = ArrayList<Int>()
        var list11 = ArrayList<Int>()
        var list12 = ArrayList<Int>()

        list1.add(0)
        list1.add(8)

        list2.add(8)
        list2.add(20)

        list3.add(20)
        list3.add(90)

        list4.add(90)
        list4.add(130)

        list5.add(130)
        list5.add(160)

        list6.add(160)
        list6.add(180)

        list7.add(180)
        list7.add(210)

        list8.add(210)
        list8.add(240)

        list9.add(240)
        list9.add(280)

        list10.add(280)
        list10.add(300)

        list11.add(300)
        list11.add(330)

        list12.add(330)
        list12.add(360)

        colorAdapterMap[list1] = bgColors2[0]
        colorAdapterMap[list2] = bgColors2[1]
        colorAdapterMap[list3] = bgColors2[2]
        colorAdapterMap[list4] = bgColors2[3]
        colorAdapterMap[list5] = bgColors2[4]
        colorAdapterMap[list6] = bgColors2[5]
        colorAdapterMap[list7] = bgColors2[6]
        colorAdapterMap[list8] = bgColors2[7]
        colorAdapterMap[list9] = bgColors2[8]
        colorAdapterMap[list10] = bgColors2[9]
        colorAdapterMap[list11] = bgColors2[10]
        colorAdapterMap[list12] = bgColors2[11]


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        return inflater.inflate(
            R.layout.developer_viewpage_collection_demo_itemfragment,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        created = true
        arguments?.takeIf {
            it.containsKey(ARG_OBJECT)
        }?.apply {
            bindData(index, colorTypeId)
        }
    }

    fun bindData(imageResource: Int, colorTypeId: Int) {
        index = imageResource
        if (created) {
            colorsMap[index]?.let {
                Log.e("color-sundu", "start")
                val bitmap = BitmapFactory.decodeResource(resources, it)
                val builder: Palette.Builder = Palette.from(bitmap)

                val palette: Palette = builder.generate()

                var color = Color.WHITE

                when (colorTypeId) {
                    R.id.color_main -> {
                        color = palette.getDominantColor(Color.WHITE)
                    }
                    R.id.color_mute -> {
                        color = palette.getMutedColor(Color.WHITE)
                    }
                    R.id.color_light -> {
                        color = palette.getLightMutedColor(Color.WHITE)
                    }
                    R.id.color_dark -> {
                        color = palette.getDarkMutedColor(Color.WHITE)
                    }
                    R.id.color_va_light -> {
                        color = palette.getLightVibrantColor(Color.WHITE)
                    }
                    R.id.color_va_dark -> {
                        color = palette.getDarkVibrantColor(Color.WHITE)
                    }
                }
                var diff = Double.MAX_VALUE
                var adapterColor = 0
                for (i in 0..12) {
                    var currentDiff = colordiff2(color,bgColors[i].toInt())
                    Log.e("color-sundu", " $i 主色：= $color 对比色：= ${bgColors[i]}   色差 ：= ${currentDiff}")
                    if (currentDiff < diff) {
                        diff = currentDiff
                        adapterColor = i
                    }
                }
                main_color_bg.setBackgroundColor(color)
//
//                var H = colorDiff5(color)
//                Log.e("color-sundu","H = $H ")
//                var adapterColor = bgColors2[12]
//                colorAdapterMap.iterator().forEach {
//                   val l =  it.key[0]
//                    val r = it.key[1]
//                    if(H >= l && H <=r){
//                        adapterColor = it.value
//                    }
//                }
//                imageview.setBackgroundColor(adapterColor.toInt())
                imageview.setBackgroundColor(bgColors[adapterColor].toInt())
                corve.setImageDrawable(resources.getDrawable(it))
                top_bg.setImageResource(R.mipmap.color_bg)
            }
        }
    }

    fun colordiff(color1: Int, color2: Int): Double {
        var colorConverter1 = ColorConverter()
        var colorConverter2 = ColorConverter()
        return CIEDE2000.calculateDeltaE(
            colorConverter1.RGBtoLAB(
                (color1 and 0xff0000 shr 16),
                (color1 and 0x00ff00 shr 8),
                (color1 and 0x0000ff)
            ),
            colorConverter2.RGBtoLAB(
                (color2 and 0xff0000 shr 16),
                (color2 and 0x00ff00 shr 8),
                (color2 and 0x0000ff)
            )
        )
    }

    fun colordiff2(color1: Int, color2: Int): Double {
        var colorConverter1 = ColorConverter()
        var colorConverter2 = ColorConverter()
        var clab1 = rgbToLab(
            (color1 and 0xff0000 shr 16),
            (color1 and 0x00ff00 shr 8),
            (color1 and 0x0000ff)
        )
        var clab2 = rgbToLab(
            (color2 and 0xff0000 shr 16),
            (color2 and 0x00ff00 shr 8),
            (color2 and 0x0000ff)
        )
        Log.e("color-sundu","色相 = ${clab1[0]} - ${clab2[0]}  饱和度 = ${clab1[1]} - ${clab2[1]}  色彩 =  ${clab1[2]} - ${clab2[2]}")
        val dL = Math.pow(clab1[0] - clab2[0], 2.0)
        val da = Math.pow(clab1[1] - clab2[1], 2.0)
        val db = Math.pow(clab1[2] - clab2[2], 2.0)
        return Math.sqrt(dL + da + db)
    }

    var R1 = 100;
    var angle = 30;
    var h = R1 * Math.cos(angle / 180 * Math.PI);
    var r = R1 * Math.sin(angle / 180 * Math.PI);

    fun colordiff3(color1: Int, color2: Int): Double {
        var hsv1 = rgbToHsv(color1)
        var hsv2 = rgbToHsv(color2)

        var x1 = r * hsv1[2] * hsv1[1] * Math.cos(hsv1[0] / 180 * Math.PI);
        var y1 = r * hsv1[2] * hsv1[1] * Math.sin(hsv1[0] / 180 * Math.PI);
        var z1 = h * (1 - hsv1[2]);
        var x2 = r * hsv2[2] * hsv2[1] * Math.cos(hsv2[0] / 180 * Math.PI);
        var y2 = r * hsv2[2] * hsv2[1] * Math.sin(hsv2[0] / 180 * Math.PI);
        var z2 = h * (1 - hsv2[2]);
        var dx = x1 - x2;
        var dy = y1 - y2;
        var dz = z1 - z2;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }


    fun rgbToHsv(color: Int): FloatArray {
        //切割rgb数组
        val r = color and 0xff0000 shr 16
        val g = color and 0x00ff00 shr 8
        val b = color and 0x0000ff

        //公式运算 /255
        val R_1 = r / 255f
        val G_1 = g / 255f
        val B_1 = b / 255f
        //重新拼接运算用数组
        val all = floatArrayOf(R_1, G_1, B_1)
        var max = all[0]
        var min = all[0]
        //循环查找最大值和最小值
        for (i in all.indices) {
            if (max <= all[i]) {
                max = all[i]
            }
            if (min >= all[i]) {
                min = all[i]
            }
        }
        val C_max = max
        val C_min = min
        //计算差值
        val diff = C_max - C_min
        var hue = 0f
        //判断情况计算色调H
        if (diff == 0f) {
            hue = 0f
        } else {
            if (C_max == R_1) {
                hue = (G_1 - B_1) / diff % 6 * 60f
            }
            if (C_max == G_1) {
                hue = ((B_1 - R_1) / diff + 2f) * 60f
            }
            if (C_max == B_1) {
                hue = ((R_1 - G_1) / diff + 4f) * 60f
            }
        }
        //计算饱和度S
        val saturation: Float
        saturation = if (C_max == 0f) {
            0f
        } else {
            diff / C_max
        }
        //计算明度V
        return floatArrayOf(hue, saturation, C_max)
    }


    fun colordiff4(color1: Int,color2: Int):Double{
        var red1=Color.red(color1)
        var green1=Color.green(color1)
        var  blue1=Color.blue(color1)

        var red2=Color.red(color2)
        var green2=Color.green(color2)
        var  blue2=Color.blue(color2)
        var rmean=(red1+red2).toDouble()/2
        var red=red1-red2
        var green=green1-green2
        var blue=blue1-blue2

        return Math.sqrt((2+rmean/256)*(red*red)+4*(green*green)+(2+(255-rmean)/256)*(blue*blue));
    }

    fun rgbToLab(R: Int, G: Int, B: Int): DoubleArray {
        var r: Double
        var g: Double
        var b: Double
        val X: Double
        val Y: Double
        val Z: Double
        var xr: Double
        var yr: Double
        var zr: Double

        // D65/2°
        val Xr = 95.047
        val Yr = 100.0
        val Zr = 108.883


        // --------- RGB to XYZ ---------//
        r = R / 255.0
        g = G / 255.0
        b = B / 255.0
        r = if (r > 0.04045) Math.pow((r + 0.055) / 1.055, 2.4) else r / 12.92
        g = if (g > 0.04045) Math.pow((g + 0.055) / 1.055, 2.4) else g / 12.92
        b = if (b > 0.04045) Math.pow((b + 0.055) / 1.055, 2.4) else b / 12.92
        r *= 100.0
        g *= 100.0
        b *= 100.0
        X = 0.4124 * r + 0.3576 * g + 0.1805 * b
        Y = 0.2126 * r + 0.7152 * g + 0.0722 * b
        Z = 0.0193 * r + 0.1192 * g + 0.9505 * b


        // --------- XYZ to Lab --------- //
        xr = X / Xr
        yr = Y / Yr
        zr = Z / Zr
        xr = if (xr > 0.008856) Math.pow(xr, 1 / 3.0).toFloat()
            .toDouble() else (7.787 * xr + 16 / 116.0).toFloat().toDouble()
        yr = if (yr > 0.008856) Math.pow(yr, 1 / 3.0).toFloat()
            .toDouble() else (7.787 * yr + 16 / 116.0).toFloat().toDouble()
        zr = if (zr > 0.008856) Math.pow(zr, 1 / 3.0).toFloat()
            .toDouble() else (7.787 * zr + 16 / 116.0).toFloat().toDouble()
        val lab = DoubleArray(3)
        lab[0] = 116 * yr - 16
        lab[1] = 500 * (xr - yr)
        lab[2] = 200 * (yr - zr)
        return lab
    }

    fun colorDiff5(color:Int):Int{
        val R = color and 0xff0000 shr 16
        val G = color and 0x00ff00 shr 8
        val B = color and 0x0000ff
        var H = rgbtoHsb(R,G,B)
        return H
    }

    fun rgbtoHsb(r:Int,g:Int,b:Int):Int{
        val var_Min = Math.min(Math.min(r, g),b)
        val var_Max = Math.max(Math.max(r, g), b)
        var H = 0
        if (var_Min == var_Max) {
            H = 0
        } else if (var_Max == r && g >= b) {
            H = 60 * ((g - b) / (var_Max - var_Min))
        } else if (var_Max == r && g < b) {
            H = 60 * ((g - b) / (var_Max - var_Min)) + 360
        } else if (var_Max == g) {
            H = 60 * ((b - r) / (var_Max - var_Min)) + 120
        } else if (var_Max == b) {
            H = 60 * ((r - g) / (var_Max - var_Min)) + 240
        }
        H = if (H >= 360) 0 else H
        return H
    }
}