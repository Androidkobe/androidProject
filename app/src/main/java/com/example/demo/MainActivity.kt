package com.example.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.demo.sundu.developer.DeveloperActivity
import com.example.demo.sundu.jetpack.JetpackActivity
import com.example.demo.sundu.kotlin.KotlinActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecycleView.setHasFixedSize(true)
        mRecycleView.layoutManager = GridLayoutManager(this,3)
        mRecycleView.adapter =
            MainAdapter(createData(), itemViewClick)
    }

    private val itemViewClick: ItemViewClick = object :
        ItemViewClick {
        override fun onItemViewClick(position: Int, view: View) {
            when (position) {
                0 -> startActivity(
                    Intent(
                        this@MainActivity,
                        KotlinActivity::class.java
                    )
                )
                1 -> startActivity(
                    Intent(
                        this@MainActivity,
                        JetpackActivity::class.java
                    )
                )
                2 -> startActivity(
                    Intent(
                        this@MainActivity,
                        DeveloperActivity::class.java
                    )
                )
            }
        }
    }

    private fun createData() : List<String>{
        val stringArray : MutableList<String> = mutableListOf()
        stringArray.add("kotlin")
        stringArray.add("jetPack")
        stringArray.add("Developer")
        return stringArray
    }
}