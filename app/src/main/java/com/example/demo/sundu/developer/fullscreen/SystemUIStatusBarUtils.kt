package com.example.demo.sundu.developer.fullscreen

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.*
import androidx.annotation.RequiresApi


/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */

object SystemUIStatusBarUtils {

    /**
     * 向后倾斜模式 全屏
     * 触摸屏幕 状态栏就会出现
     */
    fun setFullScreenGoBackModel(activity: Activity) {
        var uiOption = activity.window.decorView.systemUiVisibility
        uiOption = uiOption or View.SYSTEM_UI_FLAG_FULLSCREEN
        uiOption = uiOption or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        activity.window.decorView.systemUiVisibility = uiOption
    }

    /**
     * 沉浸模式 全屏
     * 滑动状态栏边缘 状态栏会展示
     */
    fun setFullScreenImmersiveModel(activity: Activity) {
        var uiOption = activity.window.decorView.systemUiVisibility
        uiOption = uiOption or View.SYSTEM_UI_FLAG_IMMERSIVE
        uiOption = uiOption or View.SYSTEM_UI_FLAG_FULLSCREEN
        uiOption = uiOption or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        activity.window.decorView.systemUiVisibility = uiOption
    }

    /**
     * 粘性沉浸式 模式 全屏
     * 触摸状态会展示 systemUI  过几秒后消失
     * 用于游戏居多
     */
    fun setFullScreenStickImmersiveModel(activity: Activity) {
        var uiOption = activity.window.decorView.systemUiVisibility
        uiOption = uiOption or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        uiOption = uiOption or View.SYSTEM_UI_FLAG_FULLSCREEN
        uiOption = uiOption or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        activity.window.decorView.systemUiVisibility = uiOption
    }

    fun changeFullScreen(activity: Activity?, isFullscreen: Boolean) {
        if (activity == null || activity.isFinishing || activity.isDestroyed) {
            return
        }
        val decorView = activity.window.decorView
        var flag = decorView.systemUiVisibility
        if (isFullscreen) {
            // 状态栏隐藏
            flag = flag or View.SYSTEM_UI_FLAG_FULLSCREEN
//            // 导航栏隐藏
//            flag = flag or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            // 布局延伸到导航栏
//            flag = flag or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//            // 布局延伸到状态栏
            flag = flag or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//            // 全屏时,增加沉浸式体验
            flag = flag or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            //  部分国产机型适用.不加会导致退出全屏时布局被状态栏遮挡
            // activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            // android P 以下的刘海屏,各厂商都有自己的适配方式,具体在manifest.xml中可以看到
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val pa = activity.window.attributes
                pa.layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
                activity.window.attributes = pa
            }
        } else {
            flag = flag and View.SYSTEM_UI_FLAG_FULLSCREEN.inv()
            flag = flag and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION.inv()
            flag = flag and View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN.inv()
            flag = flag and View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION.inv()
            //            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val pa = activity.window.attributes
                pa.layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT
                activity.window.attributes = pa
            }
        }
        decorView.systemUiVisibility = flag
    }

    /**
     * Set the navigation bar's color.
     *
     * @param window The window.
     * @param color  The navigation bar's color.
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setNavBarColor(window: Window, color: Int) {
        window.navigationBarColor = color;
    }

    /**
     * Return whether the navigation bar visible.
     *
     * @return `true`: yes<br></br>`false`: no
     */
    fun isSupportNavBar(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                ?: return false
            val display = wm.defaultDisplay
            val size = Point()
            val realSize = Point()
            display.getSize(size)
            display.getRealSize(realSize)
            return realSize.y !== size.y || realSize.x !== size.x
        }
        val menu = ViewConfiguration.get(context).hasPermanentMenuKey()
        val back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
        return !menu && !back
    }

}