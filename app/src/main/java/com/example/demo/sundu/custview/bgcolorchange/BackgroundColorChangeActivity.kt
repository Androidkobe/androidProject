package com.example.demo.sundu.custview.bgcolorchange

import android.app.Activity
import android.os.Bundle
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_backgroun_gradient_layout.*

class BackgroundColorChangeActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backgroun_gradient_layout)
        button.setOnClickListener {
            gradient_gradient_view.startGradientAnimation()
        }
    }
}