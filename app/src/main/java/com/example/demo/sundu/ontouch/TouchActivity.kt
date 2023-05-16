package com.example.demo.sundu.ontouch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_touch.*

class TouchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch)
        button1.setOnClickListener{
            Log.e("sundu","第一层 按钮 button1")
        }
        button2.setOnClickListener{
            Log.e("sundu","第二层 按钮 button2")
        }
        button3.setOnClickListener{
            Log.e("sundu","第三层 按钮 button3")
        }
        button4.setOnClickListener{
            Log.e("sundu","第四层 按钮 button4")
        }
    }
}