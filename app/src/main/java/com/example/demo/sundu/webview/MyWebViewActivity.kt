package com.example.demo.sundu.webview

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.example.demo.R
import com.tencent.smtt.sdk.WebView
import kotlinx.android.synthetic.main.activity_webview_layout.*


/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */

class MyWebViewActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview_layout)
        addwebview.setOnClickListener{
            windowManagerAddView()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
    }
    private fun activityAddWebView(){
        val rootView = findViewById<LinearLayout>(R.id.rootview)
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
        var webview = WebView(applicationContext)
        rootView.addView(webview,layoutParams)
        webview.settings.loadWithOverviewMode = true
        webview.settings.useWideViewPort = true
        webview.settings.domStorageEnabled = true
        webview.settings.javaScriptEnabled = true
        webview.settings.javaScriptCanOpenWindowsAutomatically = true
        webview.loadUrl("https://act.e.mi.com/gs4-03/index.html")
    }

    private fun windowManagerAddView(){
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        var windowManagerLayoutParams  = WindowManager.LayoutParams()
        windowManagerLayoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        windowManagerLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT

        var root = RelativeLayout(applicationContext)
        var webview = WebView(root.context)
        webview.settings.loadWithOverviewMode = true
        webview.settings.useWideViewPort = true
        webview.settings.domStorageEnabled = true
        webview.settings.javaScriptEnabled = true
        webview.settings.javaScriptCanOpenWindowsAutomatically = true
        webview.loadUrl("https://act.e.mi.com/gs4-03/index.html")
        val button = Button(baseContext)

        button.setOnClickListener {
            windowManager.removeView(root)
        }

        root.addView(webview,RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT))
        root.addView(button,RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT))
        windowManager.addView(root,windowManagerLayoutParams)
    }
}