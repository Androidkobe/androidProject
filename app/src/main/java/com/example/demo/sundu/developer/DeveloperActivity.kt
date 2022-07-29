package com.example.demo.sundu.developer

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.demo.R
import com.example.demo.sundu.developer.immersive.ImmerSiveActivity
import com.example.demo.sundu.developer.netingscrollview.NestingScrollActivity
import com.example.demo.sundu.developer.nineimageload.NineImgLoadActivity
import com.example.demo.sundu.developer.permission.PermissionActivity
import com.example.demo.sundu.developer.sensor.SenSorActivity
import com.example.demo.sundu.developer.sensorgyr.GyrActivity
import com.example.demo.sundu.developer.viewapi.ViewScrollToApiActivity
import com.example.demo.sundu.developer.viewpager.ViewPager2Activity
import com.example.demo.sundu.developer.viewpager.ViewPagerActivity
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
        return stringArray
    }
}