package com.example.demo.sundu.developer.recycleview

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_develop_recycleview.*

class RecycleViewActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_develop_recycleview)
        mRecycleView.layoutManager = LinearLayoutManager(baseContext)
    }
}