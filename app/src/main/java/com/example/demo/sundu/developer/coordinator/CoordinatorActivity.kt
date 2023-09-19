package com.example.demo.sundu.developer.coordinator

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.databinding.ActivityCoordinatorLayoutBinding
import com.example.demo.utils.*
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener


class CoordinatorActivity : AppCompatActivity() {

    lateinit var viewDataBinding: ActivityCoordinatorLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersiveStatusBar() // 沉浸式状态栏
        immersiveNavigationBar() // 沉浸式导航栏
        setStatusBarColor(Color.TRANSPARENT) // 设置状态栏背景色
        setNavigationBarColor(Color.TRANSPARENT) // 设置导航栏背景色
        setLightStatusBar(true) // 设置浅色状态栏背景（文字为深色）
        setLightNavigationBar(true) // 设置浅色导航栏背景（文字为深色）
        viewDataBinding = ActivityCoordinatorLayoutBinding.inflate(layoutInflater)
        setContentView(viewDataBinding.root)

        var slideMaxHeight = 200f

        viewDataBinding.appBar.addOnOffsetChangedListener(OnOffsetChangedListener { v, verticalOffset ->
            Log.e("sundu", "app bar - v $verticalOffset")
            if (verticalOffset < 0) {
                setLightStatusBar(true)
                val slideHeight: Int = kotlin.math.abs(verticalOffset)
                if (slideHeight < slideMaxHeight) {
                    val proportion = ((slideHeight / slideMaxHeight) * 255).toInt()
                    val color = Color.argb(proportion, 255, 0, 0)
                    viewDataBinding.toolbar.setBackgroundColor(color)
                } else {
                    viewDataBinding.toolbar.setBackgroundColor(Color.RED)
                }
                viewDataBinding.tevTitle.setTextColor(Color.parseColor("#99000000"))
                viewDataBinding.imvBack.setColorFilter(Color.parseColor("#99000000"))
            } else {
                setLightStatusBar(true)
                viewDataBinding.toolbar.setBackgroundColor(Color.TRANSPARENT)
                viewDataBinding.tevTitle.setTextColor(Color.parseColor("#FF00ff00"))
                viewDataBinding.imvBack.setColorFilter(Color.parseColor("#FF00ff00"))
            }
        })

    }
}