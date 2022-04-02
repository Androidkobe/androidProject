package com.example.demo.sundu.lifecycle

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.demo.databinding.ActivityLifecycleLayoutBinding

class LifecycleActivity : AppCompatActivity() {

    val bindView by lazy {
        ActivityLifecycleLayoutBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindView.root)
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                Log.e("sundu", "current state = ${event.targetState}")
            }
        })

        var button: ViewAddLifeCycle? = null


        bindView.addView.setOnClickListener {
            button = ViewAddLifeCycle(this)
            button?.layoutParams = LinearLayout.LayoutParams(100, 100)
            button?.text = "我是自定义添加的 实现lifecycle"
            button?.addListenerLifecycleCall(object : LifecycleEventObserver {
                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                    Log.e("sundu", "view current state = ${event.targetState}")
                }
            })
            bindView.root.addView(button)
        }
        bindView.removeView.setOnClickListener {
            bindView.root.removeView(button)
            button = null
        }
    }

}