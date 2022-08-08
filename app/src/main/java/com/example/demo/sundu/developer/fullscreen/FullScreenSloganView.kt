package com.example.demo.sundu.developer.fullscreen

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.demo.R
import kotlinx.android.synthetic.main.view_fullscreen_splash_slogan.view.*

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */

class FullScreenSloganView : FrameLayout {

    constructor(context: Context) : super(context) {
        initView()
    }


    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        initView()
    }


    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.view_fullscreen_splash_slogan, this, true)
        view_app_name.text = "UUM"
        val icon: Drawable? = getAppIconDrawable(context, context.packageName)
        icon?.let {
            view_slogan_image.setImageDrawable(icon)
        }
    }


    fun getAppIconDrawable(context: Context, packageName: String?): Drawable? {
        try {
            return context.packageManager.getApplicationIcon(packageName!!)
        } catch (e: PackageManager.NameNotFoundException) {
        }
        return null
    }


}