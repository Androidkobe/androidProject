package com.example.demo.sundu.developer.viewpager3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R

class ViewPager3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.developer_view_pager3)
        var fragmentManager = supportFragmentManager;
        fragmentManager.beginTransaction().replace(R.id.fragmentLayout, Collection3DemoFragment())
            .commit()
    }
}