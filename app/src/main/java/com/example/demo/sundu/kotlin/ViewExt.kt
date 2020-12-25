package com.example.demo.sundu.kotlin

import android.widget.TextView

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */
 
fun TextView.isBold() = this.apply {
    paint.isFakeBoldText = true
}

val <T> List<T>.lastIndex: Int get() = size - 1