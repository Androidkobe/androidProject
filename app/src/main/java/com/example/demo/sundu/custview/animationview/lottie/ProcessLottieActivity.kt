package com.example.demo.sundu.custview.animationview.lottie

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_process_lottie.*

class ProcessLottieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_process_lottie)

        //z轴动画部分
        lottieViewZ_Animation.setAnimation("twistz/animation/data.json")
        lottieViewZ_Animation.repeatCount = ValueAnimator.INFINITE
        lottieViewZ_Animation.playAnimation()

        //z轴交互部分
        lottieViewZ_Interact.imageAssetsFolder = "twistz/interact/images"
        lottieViewZ_Interact.setAnimation("twistz/interact/data.json")
        ProcessHelper().registerListener(this, object : ProcessHelper.ProcessListener {
            override fun process(first: Int, current: Int) {
                var space = Math.abs(current - first)
                if (space > 180) {
                    space = 180
                }
                val process = space * 1f / 180f
                lottieViewZ_Interact.progress = process
            }

        }, 3)

        lottieViewY_Animation.setAnimation("twisty/animation/data.json")
        lottieViewY_Animation.repeatCount = ValueAnimator.INFINITE
        lottieViewY_Animation.playAnimation()

        lottieViewY_Interact.imageAssetsFolder = "twisty/interact/images"
        lottieViewY_Interact.setAnimation("twisty/interact/data.json")
        ProcessHelper().registerListener(this, object : ProcessHelper.ProcessListener {
            override fun process(first: Int, current: Int) {
                var space = Math.abs(current - first)
                if (space > 180) {
                    space = 180
                }
                val process = space * 1f / 180f
                lottieViewY_Interact.progress = process
            }

        }, 3)




        lottieViewX_Animation.imageAssetsFolder = "twistx/animation/images"
        lottieViewX_Animation.setAnimation("twistx/animation/data.json")
        lottieViewX_Animation.repeatCount = ValueAnimator.INFINITE
        lottieViewX_Animation.playAnimation()

        lottieViewX_Interact.setAnimation("twistx/interact/data.json")
        ProcessHelper().registerListener(this, object : ProcessHelper.ProcessListener {
            override fun process(first: Int, current: Int) {
                var space = Math.abs(current - first)
                if (space > 180) {
                    space = 180
                }
                val process = space * 1f / 180f
                lottieViewX_Interact.progress = process
            }

        }, 3)
    }
}