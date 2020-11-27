package com.example.demo.sundu.developer.viewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.demo.R
import com.example.demo.sundu.developer.viewpager2.Collection2DemoFragment

class ViewPager2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.developer_view_pager)
        var fragmentManager = supportFragmentManager;
        fragmentManager.beginTransaction().replace(R.id.fragmentLayout, Collection2DemoFragment()).commit()
    }
}