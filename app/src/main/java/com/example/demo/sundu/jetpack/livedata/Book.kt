package com.example.demo.sundu.jetpack.livedata

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */
 
data class Book(val name:String,val num:Int){
    val mName by lazy { name }
    val mNum:Int by lazy { num }
}