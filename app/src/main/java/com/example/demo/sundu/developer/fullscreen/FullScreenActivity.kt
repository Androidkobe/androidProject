package com.example.demo.sundu.developer.fullscreen

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_fullscreen.*
import kotlinx.android.synthetic.main.view_fullscreen_splash_slogan.*

class FullScreenActivity : Activity() {
    var time = 5;

    var handler = Handler(Looper.getMainLooper())

    val callBack = object : Runnable {
        override fun run() {
            handler.postDelayed(this, 1000)
            view_skip.setText("跳过广告 $time")
            time -= 1
            if (time < 0) {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SystemUIStatusBarUtils.changeFullScreen(this, true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SystemUIStatusBarUtils.setNavBarColor(window, Color.WHITE)
        }
        setContentView(R.layout.activity_fullscreen)
        image.setImageDrawable(resources.getDrawable(R.mipmap.fullscreen_bg))
        view_skip.setText("跳过广告$time")
        handler.postDelayed(callBack, 1000)
    }
}