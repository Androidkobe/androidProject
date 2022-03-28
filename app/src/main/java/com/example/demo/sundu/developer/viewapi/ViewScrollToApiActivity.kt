package com.example.demo.sundu.developer.viewapi

import android.app.Activity
import android.os.Bundle
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_developer_view_scrollto_api.*

class ViewScrollToApiActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer_view_scrollto_api)
        button.setOnClickListener {
            button.scrollBy(-10, -10)
        }
    }
}