package com.example.demo.sundu.developer.viewpager4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R

class ViewPager4Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.developer_view_pager4)
        var fragmentManager = supportFragmentManager;
        fragmentManager.beginTransaction().replace(R.id.fragmentLayout, Collection4DemoFragment())
            .commit()
    }
}