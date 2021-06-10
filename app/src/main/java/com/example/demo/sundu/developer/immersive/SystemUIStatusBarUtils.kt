package com.example.demo.sundu.developer.immersive

import android.app.ActionBar
import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */

object SystemUIStatusBarUtils {

    /**
     * 向后倾斜模式 全屏
     * 触摸屏幕 状态栏就会出现
     */
    fun setFullScreenGoBackModel(activity: Activity){
        var uiOption = activity.window.decorView.systemUiVisibility
        uiOption = uiOption or View.SYSTEM_UI_FLAG_FULLSCREEN
        uiOption = uiOption or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        activity.window.decorView.systemUiVisibility = uiOption
    }

    /**
     * 沉浸模式 全屏
     * 滑动状态栏边缘 状态栏会展示
     */
    fun setFullScreenImmersiveModel(activity: Activity){
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
    fun setFullScreenStickImmersiveModel(activity: Activity){
        var uiOption = activity.window.decorView.systemUiVisibility
        uiOption = uiOption or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        uiOption = uiOption or View.SYSTEM_UI_FLAG_FULLSCREEN
        uiOption = uiOption or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        activity.window.decorView.systemUiVisibility = uiOption
    }
}