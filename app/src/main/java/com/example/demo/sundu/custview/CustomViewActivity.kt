package com.example.demo.sundu.custview

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.demo.R
import com.example.demo.common.CommonSelectRecycleViewAdapter
import com.example.demo.common.ItemViewClick
import com.example.demo.sundu.custview.beziercurve.dragpop.BezierCurverDragPopActivity
import com.example.demo.sundu.custview.bitmapshader.BitmapShader_TelescopeViewActivity
import com.example.demo.sundu.custview.downloadview.DownLoadViewActivity
import kotlinx.android.synthetic.main.activity_custom_view.*
import java.util.*

class CustomViewActivity : AppCompatActivity() {

    var dataSource = HashMap<String, Class<*>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view)
        createData()
        custom_view_recycle.setHasFixedSize(true)
        custom_view_recycle.layoutManager = GridLayoutManager(this, 3)
        custom_view_recycle.adapter = CommonSelectRecycleViewAdapter(getData(), itemViewClick)
    }

    private val itemViewClick: ItemViewClick = object :
        ItemViewClick {
        override fun onItemViewClick(position: Int, view: View) {
           val result =dataSource[getData()[position]]
            startActivity(Intent(this@CustomViewActivity,result))
        }
    }

    private fun createData(){
        dataSource["downloadView"] = DownLoadViewActivity::class.java
        dataSource["bezier-dragpop"] = BezierCurverDragPopActivity::class.java
        dataSource["bitmapShader-Telescope"] = BitmapShader_TelescopeViewActivity::class.java
    }

    private fun getData(): List<String> {
        val stringArray: MutableList<String> = mutableListOf()
        dataSource.forEach{
            stringArray.add(it.key)
        }
        return stringArray
    }
}