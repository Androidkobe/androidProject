package com.example.demo.sundu.custview.scaleimage

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_scale_image.*

class ScaleImageActivity : AppCompatActivity() {
    val url = "https://img2.baidu.com/it/u=4106248623,2958754056&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scale_image)
        custom_scale_image.setImageResource(R.mipmap.scenery)
    }
}