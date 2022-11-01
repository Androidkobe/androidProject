package com.example.demo.sundu.developer.viewpager2

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.example.demo.R
import kotlinx.android.synthetic.main.developer_viewpage2_collection_demo.*
import kotlinx.android.synthetic.main.developer_viewpage_collection_demo_itemfragment.*


/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */


private const val ARG_OBJECT = "object"

// Instances of this class are fragments representing a single
// object in our collection.
class DemoObject2Fragment : Fragment() {

    var map = mutableMapOf<Int, Int>()

    var colorList = ArrayList<Int>()

    var index = 0
    var radio = 0

    var created = false

    init {
        map[0] = R.mipmap.a
        map[1] = R.mipmap.b
        map[2] = R.mipmap.c
        map[3] = R.mipmap.d
        map[4] = R.mipmap.e
        map[5] = R.mipmap.f
        map[6] = R.mipmap.g
        map[7] = R.mipmap.h
        map[8] = R.mipmap.i
        map[9] = R.mipmap.j
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
            bindData(index, radio, colorList)
        }
    }

    fun bindData(imageResource: Int, radio: Int, colors: ArrayList<Int>) {
        index = imageResource
        colorList = colors
        this.radio = radio
        if (created) {
            map[index]?.let {
                val bitmap = BitmapFactory.decodeResource(resources, it)
                val builder: Palette.Builder = Palette.from(bitmap)

                val palette: Palette = builder.generate()

                val colors = mutableListOf<Int>()

                for (color in colorList) {
                    when (color) {
                        0 -> {
                            colors.add(palette.getDominantColor(Color.WHITE))
                        }
                        1 -> {
                            colors.add(palette.getMutedColor(Color.WHITE))
                        }
                        2 -> {
                            colors.add(palette.getLightMutedColor(Color.WHITE))
                        }
                        3 -> {
                            colors.add(palette.getDarkMutedColor(Color.WHITE))
                        }
                        4 -> {
                            colors.add(palette.getLightVibrantColor(Color.WHITE))
                        }
                        5 -> {
                            colors.add(palette.getDarkVibrantColor(Color.WHITE))
                        }
                    }
                }

                val gradientDrawable = GradientDrawable()
                gradientDrawable.shape = GradientDrawable.RECTANGLE
                gradientDrawable.colors = colors.toIntArray() //添加颜色组

                gradientDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT //设置线性渐变
                var orientation = GradientDrawable.Orientation.TL_BR //设置渐变方向

                when (radio) {
                    R.id.tb -> {
                        orientation = GradientDrawable.Orientation.TOP_BOTTOM
                    }
                    R.id.bt -> {
                        orientation = GradientDrawable.Orientation.BOTTOM_TOP
                    }
                    R.id.lr -> {
                        orientation = GradientDrawable.Orientation.LEFT_RIGHT
                    }
                    R.id.rl -> {
                        orientation = GradientDrawable.Orientation.RIGHT_LEFT
                    }
                    R.id.tlbr -> {
                        orientation = GradientDrawable.Orientation.TL_BR
                    }
                    R.id.trbl -> {
                        orientation = GradientDrawable.Orientation.TR_BL
                    }
                    R.id.bltr -> {
                        orientation = GradientDrawable.Orientation.BL_TR
                    }
                    R.id.brtl -> {
                        orientation = GradientDrawable.Orientation.BR_TL
                    }
                }

                gradientDrawable.orientation = orientation

                imageview.setBackground(gradientDrawable)

                corve.setImageDrawable(resources.getDrawable(it))
            }
        }
    }
}