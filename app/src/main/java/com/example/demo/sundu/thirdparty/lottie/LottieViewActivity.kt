package com.example.demo.sundu.thirdparty.lottie

import android.animation.ValueAnimator
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_lottieview_layout.*


class LottieViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottieview_layout)
        ///设置默认值
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        lottie_view_animation.layoutParams = params

//        lottie_view_group.layoutParams.width = -1
//        lottie_view_group.layoutParams.height = -1
//        lottie_view_group.requestLayout()
        load_lottie.setOnClickListener {
            action()
        }
    }

    private fun action() {
        //设置默认动画
        lottie_view_animation.imageAssetsFolder = "component/swipe_up/images"
        lottie_view_animation.setAnimation("component/swipe_up/data.json")
        lottie_view_animation.repeatCount = ValueAnimator.INFINITE
        lottie_view_animation.playAnimation()
    }

}