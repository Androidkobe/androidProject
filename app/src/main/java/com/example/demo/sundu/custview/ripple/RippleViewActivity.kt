package com.example.demo.sundu.custview.ripple

import android.app.Activity
import android.graphics.PorterDuff
import android.os.Bundle
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_ripple_layout.*

class RippleViewActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ripple_layout)
        mode.setText("PorterDuff.Mode.CLEAR")
        var i = 0
        mode.setOnClickListener {
            i += 1
            when (i) {
                1 -> {
                    mode.text = "PorterDuff.Mode.SRC"
                    rect_round_pipple.setXfermod(PorterDuff.Mode.SRC)
                }
                2 -> {
                    mode.text = "PorterDuff.Mode.DST"
                    rect_round_pipple.setXfermod(PorterDuff.Mode.DST)
                }
                3 -> {
                    mode.text = "PorterDuff.Mode.SRC_OVER"
                    rect_round_pipple.setXfermod(PorterDuff.Mode.SRC_OVER)
                }
                4 -> {
                    mode.text = "PorterDuff.Mode.DST_OVER"
                    rect_round_pipple.setXfermod(PorterDuff.Mode.DST_OVER)
                }
                5 -> {
                    mode.text = "PorterDuff.Mode.SRC_IN"
                    rect_round_pipple.setXfermod(PorterDuff.Mode.SRC_IN)
                }
                6 -> {
                    mode.text = "PorterDuff.Mode.DST_IN"
                    rect_round_pipple.setXfermod(PorterDuff.Mode.DST_IN)
                }
                7 -> {
                    mode.text = "PorterDuff.Mode.SRC_OUT"
                    rect_round_pipple.setXfermod(PorterDuff.Mode.SRC_OUT)
                }
                8 -> {
                    mode.text = "PorterDuff.Mode.DST_OUT"
                    rect_round_pipple.setXfermod(PorterDuff.Mode.DST_OUT)
                }
                9 -> {
                    mode.text = "PorterDuff.Mode.SRC_ATOP"
                    rect_round_pipple.setXfermod(PorterDuff.Mode.SRC_ATOP)
                }
                10 -> {
                    mode.text = "PorterDuff.Mode.DST_ATOP"
                    rect_round_pipple.setXfermod(PorterDuff.Mode.DST_ATOP)
                }
                11 -> {
                    mode.text = "PorterDuff.Mode.XOR"
                    rect_round_pipple.setXfermod(PorterDuff.Mode.XOR)
                }
                12 -> {
                    mode.text = "PorterDuff.Mode.DARKEN"
                    rect_round_pipple.setXfermod(PorterDuff.Mode.DARKEN)
                }
                13 -> {
                    mode.text = "PorterDuff.Mode.LIGHTEN"
                    rect_round_pipple.setXfermod(PorterDuff.Mode.LIGHTEN)
                }
                14 -> {
                    mode.text = "PorterDuff.Mode.MULTIPLY"
                    rect_round_pipple.setXfermod(PorterDuff.Mode.MULTIPLY)
                }
                15 -> {
                    mode.text = "PorterDuff.Mode.SCREEN"
                    rect_round_pipple.setXfermod(PorterDuff.Mode.SCREEN)
                }
                else -> {
                    i = 0
                    mode.text = "PorterDuff.Mode.CLEAR"
                    rect_round_pipple.setXfermod(PorterDuff.Mode.CLEAR)
                }
            }
        }


        mode2.setOnClickListener {
            rect_round_pipple.startRippleAnimation()
        }

        mode3.setOnClickListener {
            rect_round_pipple.startSweepLightAnimation()
        }

        mode4.setOnClickListener {
            rect_round_pipple.startBreathAnimation()
        }

        mode5.setOnClickListener {
            rect_round_pipple.startGradientAnimation()
        }
    }
}