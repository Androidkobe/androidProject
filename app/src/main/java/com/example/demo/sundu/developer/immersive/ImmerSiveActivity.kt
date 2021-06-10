package com.example.demo.sundu.developer.immersive

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_immer_sive.*

class ImmerSiveActivity : Activity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_immer_sive)
        button_set_immersive.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        view?.let { clickView ->

            when (clickView.id) {
                R.id.button_set_immersive -> {
                    setImmersive()
                }

                R.id.button_set_immersive_clear -> {

                }

                R.id.set_system_bar_color -> {

                }
            }
        }
    }

    fun setImmersive() {
        var uiOptions = window.decorView.systemUiVisibility
        if (checkbox_select_full_screen.isChecked)
            uiOptions = uiOptions or View.SYSTEM_UI_FLAG_FULLSCREEN
        if (checkbox_select_hide_navigation_bar.isChecked)
            uiOptions = uiOptions or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        if (checkbox_select_hide_status_bar.isChecked)
            uiOptions = uiOptions or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.decorView.systemUiVisibility = uiOptions
    }

    fun clearImmersive() {
        window.decorView.systemUiVisibility = 0
    }

}