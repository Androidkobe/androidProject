package com.example.demo.sundu.custview.SliderView

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_slider_view.*

class SliderViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slider_view)
        btn.setOnClickListener {
            sliderlockview.setBgGroundColor("#ff00ff00")
            sliderlockview.setText("#ffffffff")
            sliderlockview.setTextColor("#ffffffff")
            sliderlockview.setUnLokSpace(0.4f)
        }
        par.setOnClickListener {
            Log.e("sundu", "解锁控件点击啦")
        }

        text_relative.setOnClickListener{
            Log.e("sundu", "text rel 测试文案点击啦")
        }

        text.setOnClickListener{
            Log.e("sundu", "text 测试文案点击啦")
        }

        sliderlockview.setLockListener(object : SliderLockListener {
            override fun success() {
                Log.e("sundu", "横滑成功")
            }

            override fun unlocling() {
                Log.e("sundu", "横滑中")
            }

            override fun failed() {
                Log.e("sundu", "横滑失败")
            }

            override fun cancle() {
                Log.e("sundu", "取消滑动")
            }

        })

        swipeup.setGesturesAdapter(object : GesturesDispatchAdapter(this) {
            override fun getViews(): Array<View?>? {
                return arrayOf(null,null,text,sliderlockview,btn,text_relative)
            }
        })
        swipeup.setSwipeUpListener(object : SwipeUpListener {
            override fun success() {
                Log.e("sundu", "上滑成功")
            }

            override fun moveing() {
                Log.e("sundu", "上滑中")
            }

            override fun failed() {
                Log.e("sundu", "上滑失败")
            }

        })

    }
}