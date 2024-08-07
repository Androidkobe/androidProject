package com.example.demo.sundu.developer

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.demo.R
import com.example.demo.sundu.developer.andserver.FileaCutUploadActivity
import com.example.demo.sundu.developer.compass.CompassActivity
import com.example.demo.sundu.developer.coordinator.CoordinatorActivity
import com.example.demo.sundu.developer.fullscreen.FullScreenActivity
import com.example.demo.sundu.developer.immersive.ImmerSiveActivity
import com.example.demo.sundu.developer.nestedscrollview.NestedScrollActivity
import com.example.demo.sundu.developer.netingscrollview.NestingScrollActivity
import com.example.demo.sundu.developer.nineimageload.NineImgLoadActivity
import com.example.demo.sundu.developer.permission.PermissionActivity
import com.example.demo.sundu.developer.sensor.SenSorActivity
import com.example.demo.sundu.developer.sensorgyr.GyrActivity
import com.example.demo.sundu.developer.sensorxyz.SenSorxyz
import com.example.demo.sundu.developer.svgedit.SvgEditeActivity
import com.example.demo.sundu.developer.viewapi.ViewScrollToApiActivity
import com.example.demo.sundu.developer.viewpager.ViewPager2Activity
import com.example.demo.sundu.developer.viewpager.ViewPagerActivity
import com.example.demo.sundu.developer.viewpager3.ViewPager3Activity
import com.example.demo.sundu.developer.viewpager4.ViewPager4Activity
import kotlinx.android.synthetic.main.activity_main.*

class DeveloperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.developer_main_activity_layout)
        mRecycleView.setHasFixedSize(true)
        mRecycleView.layoutManager = GridLayoutManager(this,3)
        mRecycleView.adapter = DeveloperAdapter(createData(), itemViewClick)
    }

    private val itemViewClick: ItemViewClick = object :
        ItemViewClick {
        override fun onItemViewClick(position: Int, view: View) {
            when (position) {
                0 -> startActivity(
                    Intent(
                        this@DeveloperActivity,
                        ViewPagerActivity::class.java
                    )
                )
                1 -> startActivity(
                    Intent(
                        this@DeveloperActivity,
                        ViewPager2Activity::class.java
                    )
                )
                2 -> startActivity(
                    Intent(
                        this@DeveloperActivity,
                        NineImgLoadActivity::class.java
                    )
                )
                3 -> startActivity(
                    Intent(
                        this@DeveloperActivity,
                        ImmerSiveActivity::class.java
                    )
                )
                4 -> startActivity(
                    Intent(
                        this@DeveloperActivity,
                        SenSorActivity::class.java
                    )
                )
                5 -> startActivity(
                    Intent(
                        this@DeveloperActivity,
                        NestingScrollActivity::class.java
                    )
                )
                6 -> startActivity(
                    Intent(
                        this@DeveloperActivity,
                        ViewScrollToApiActivity::class.java
                    )
                )
                7 -> startActivity(
                    Intent(
                        this@DeveloperActivity,
                        GyrActivity::class.java
                    )
                )
                8 -> startActivity(
                    Intent(
                        this@DeveloperActivity,
                        PermissionActivity::class.java
                    )
                )
                9 -> startActivity(
                    Intent(
                        this@DeveloperActivity,
                        FullScreenActivity::class.java
                    )
                )
                10 -> startActivity(
                    Intent(
                        this@DeveloperActivity,
                        SvgEditeActivity::class.java
                    )
                )
                11 -> startActivity(
                    Intent(
                        this@DeveloperActivity,
                        ViewPager3Activity::class.java
                    )
                )
                12 -> startActivity(
                    Intent(
                        this@DeveloperActivity,
                        ViewPager4Activity::class.java
                    )
                )
                13 -> startActivity(
                    Intent(
                        this@DeveloperActivity,
                        SenSorxyz::class.java
                    )
                )
                14 -> startActivity(
                    Intent(
                        this@DeveloperActivity,
                        CompassActivity::class.java
                    )
                )
                15 -> startActivity(
                    Intent(
                        this@DeveloperActivity,
                        CoordinatorActivity::class.java
                    )
                )
                16 -> startActivity(
                    Intent(
                        this@DeveloperActivity,
                        NestedScrollActivity::class.java
                    )
                )
                17-> startActivity(
                    Intent(
                        this@DeveloperActivity,
                        FileaCutUploadActivity::class.java
                    )
                )
            }
        }
    }

    private fun createData() : List<String>{
        val stringArray : MutableList<String> = mutableListOf()
        stringArray.add("viewpage")
        stringArray.add("viewpage2")
        stringArray.add(".9imageLoad")
        stringArray.add("immersive")
        stringArray.add("SenSor")
        stringArray.add("NestingScrollActivity")
        stringArray.add("ViewScrollApi")
        stringArray.add("SenSor-陀螺仪")
        stringArray.add("Permission")
        stringArray.add("fullScreen")
        stringArray.add("svgEdite")
        stringArray.add("相近色-渐变")
        stringArray.add("相近色")
        stringArray.add("陀螺仪-xyz")
        stringArray.add("指南针")
        stringArray.add("协调布局")
        stringArray.add("NestedScrollView+recyclerView")
        stringArray.add("cut file")
        return stringArray
    }
}