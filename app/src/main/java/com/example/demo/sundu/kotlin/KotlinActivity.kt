package com.example.demo.sundu.kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.demo.common.ItemViewClick
import com.example.demo.R
import com.example.demo.sundu.kotlin.coroutine.KotlinCoroutineActivity
import kotlinx.android.synthetic.main.kotlin_main_activity_layout.*

class KotlinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kotlin_main_activity_layout)
        mRecycleView.setHasFixedSize(true)
        mRecycleView.layoutManager = GridLayoutManager(this,3)
        mRecycleView.adapter = KotlinMainAdapter(createData(), itemViewClick)
    }

    private val itemViewClick: ItemViewClick = object :
        ItemViewClick {
        override fun onItemViewClick(position: Int, view: View) {
            when (position) {
                0 -> startActivity(
                    Intent(
                        this@KotlinActivity,
                        KotlinCoroutineActivity::class.java
                    )
                )
            }
        }
    }

    private fun createData() : List<String>{
        val stringArray : MutableList<String> = mutableListOf()
        stringArray.add("Coroutine")
        return stringArray
    }
}