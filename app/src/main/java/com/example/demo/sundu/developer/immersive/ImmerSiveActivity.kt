package com.example.demo.sundu.developer.immersive

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_immer_sive.*


class ImmerSiveActivity : FragmentActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_immer_sive)
        button_set_immersive.setOnClickListener(this)

        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this,true);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this)

        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this,0x55000000)
        }
    }

    override fun onClick(view: View?) {
        view?.let { clickView ->

            when (clickView.id) {
                R.id.button_set_immersive -> {
                    setImmersive()
                }

                R.id.button_set_immersive_clear -> {
                    clearImmersive()
                }

                R.id.set_system_bar_color -> {

                }
            }
        }
    }

    fun setImmersive() {
        if (checkbox_select_full_screen_lean_back.isChecked)
            SystemUIStatusBarUtils.setFullScreenGoBackModel(this)
        if (checkbox_select_full_screen_immersive.isChecked)
            SystemUIStatusBarUtils.setFullScreenImmersiveModel(this)
        if (checkbox_select_full_screen_stick_immersive.isChecked)
            SystemUIStatusBarUtils.setFullScreenStickImmersiveModel(this)
    }

    fun clearImmersive() {
        window.decorView.systemUiVisibility = 0
    }

}