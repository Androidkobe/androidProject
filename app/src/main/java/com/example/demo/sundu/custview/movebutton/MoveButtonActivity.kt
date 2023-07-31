package com.example.demo.sundu.custview.movebutton

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.demo.R

class MoveButtonActivity :Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move_button_layout)
        var brv = findViewById<BackRectView>(R.id.brv)
        brv.postDelayed({
                        brv.setProcess(1,0f)
        },1000)
        brv.postDelayed({
            brv.setProcess(1,0.5f)
        },2000)
        brv.postDelayed({
            brv.setProcess(2,0f)
        },3000)
        brv.postDelayed({
            brv.setProcess(2,0.5f)
        },4000)
        brv.postDelayed({
            brv.setProcess(2,1f)
        },5000)
    }
}