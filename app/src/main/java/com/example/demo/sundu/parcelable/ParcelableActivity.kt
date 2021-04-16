package com.example.demo.sundu.parcelable

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import com.example.demo.R

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */

class ParcelableActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parcelable_layout)
        findViewById<Button>(R.id.serialize).setOnClickListener {
            var book = Book(24,"kobe")

        }

    }
}