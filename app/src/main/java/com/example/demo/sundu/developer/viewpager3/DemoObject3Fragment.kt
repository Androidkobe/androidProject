package com.example.demo.sundu.developer.viewpager3

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
class DemoObject3Fragment : Fragment() {

    var colorsMap = mutableMapOf<Int, Int>()
    var colorsBg = mutableMapOf<Int, Int>()

    var colorList = ArrayList<Int>()

    var index = 0
    var colorTypeId = R.id.color_main

    var created = false

    val bgColors = arrayOf(0x19C20C, 0xC44DFF, 0xFF3362, 0x00C0C7, 0xFCA311, 0x00ABF5)

    init {
        colorsMap[0] = R.mipmap.res_one
        colorsMap[1] = R.mipmap.res_two
        colorsMap[2] = R.mipmap.res_three
        colorsMap[3] = R.mipmap.res_four
        colorsMap[4] = R.mipmap.res_five
        colorsMap[5] = R.mipmap.res_six

        colorsBg[0] = R.drawable.ic_bg_one
        colorsBg[1] = R.drawable.ic_bg_tow
        colorsBg[2] = R.drawable.ic_bg_three
        colorsBg[3] = R.drawable.ic_bg_four
        colorsBg[4] = R.drawable.ic_bg_five
        colorsBg[5] = R.drawable.ic_bg_six
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
                for (i in 0..5) {
                    var ca = colordiff2(bgColors[i], color)
                    Log.e(
                        "color-sundu",
                        " $i 主色：= $color 对比色：= ${bgColors[i].toInt()}   色差 ：= ${diff}"
                    )
                    if (ca < diff) {
                        diff = ca
                        cat = i
                    }
                }
                main_color_bg.setBackgroundColor(color)
                imageview.setBackgroundResource(colorsBg[cat] ?: 0)
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
        var colorConverter1 = com.example.demo.sundu.developer.viewpager4.ColorConverter()
        var colorConverter2 = com.example.demo.sundu.developer.viewpager4.ColorConverter()
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
}