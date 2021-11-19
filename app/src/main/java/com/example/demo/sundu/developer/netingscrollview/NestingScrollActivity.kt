package com.example.demo.sundu.developer.netingscrollview

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.demo.R
import com.example.demo.sundu.developer.netingscrollview.one.EventDispatchPlanActivity
import com.example.demo.sundu.developer.netingscrollview.three.CoordinatorLayoutActivity
import com.example.demo.sundu.developer.netingscrollview.two.NestingScrollActivity
import java.util.*

class NestingScrollActivity : AppCompatActivity() {
    private var mToolbar: Toolbar? = null
    private var mListView: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nesting_scrollerview_layout)
        mToolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(mToolbar)
        val mData = ArrayList<String>()
        mData.add("原始事件分发方案")
        mData.add("使用NestScrollParent与NestScrollChild接口")
        mData.add("使用CoordinatorLayout")
        mListView = findViewById<View>(R.id.listView) as ListView
        mListView!!.adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, mData)
        mListView!!.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                var intent: Intent? = null
                if (position == 0) {
                    intent =
                        Intent(this@NestingScrollActivity, EventDispatchPlanActivity::class.java)
                } else if (position == 1) {
                    intent = Intent(this@NestingScrollActivity, NestingScrollActivity::class.java)
                } else if (position == 2) {
                    intent =
                        Intent(this@NestingScrollActivity, CoordinatorLayoutActivity::class.java)
                }
                if (intent != null) {
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_still)
                }
            }
    }
}
