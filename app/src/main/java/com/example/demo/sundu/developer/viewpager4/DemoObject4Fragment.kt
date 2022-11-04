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
                var cat = 0
                for (i in 0..12) {
                    var ca = colordiff2(bgColors[i].toInt(), color)
                    Log.e("color-sundu", " $i 主色：= $color 对比色：= ${bgColors[i]}   色差 ：= ${diff}")
                    if (ca < diff) {
                        diff = ca
                        cat = i
                    }
                }
                main_color_bg.setImageResource(R.mipmap.color_bg)
                Log.e("color-sundu", "设置底色 = ：${bgColors[cat]}")
                imageview.setBackgroundColor(bgColors[cat].toInt())
                corve.setImageDrawable(resources.getDrawable(it))
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
        var clab1 = colorConverter1.RGBtoLAB(
            (color1 and 0xff0000 shr 16),
            (color1 and 0x00ff00 shr 8),
            (color1 and 0x0000ff)
        )
        var clab2 = colorConverter2.RGBtoLAB(
            (color2 and 0xff0000 shr 16),
            (color2 and 0x00ff00 shr 8),
            (color2 and 0x0000ff)
        )
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
        val R = color and 0xff0000 shr 16
        val G = color and 0x00ff00 shr 8
        val B = color and 0x0000ff

        //公式运算 /255
        val R_1 = R / 255f
        val G_1 = G / 255f
        val B_1 = B / 255f
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

}