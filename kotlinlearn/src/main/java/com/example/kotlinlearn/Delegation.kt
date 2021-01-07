package com.example.kotlinlearn

import java.util.*
import javax.xml.crypto.Data

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */

fun main() {
}

inline fun <reified T> jisuan(){
    var p = 2
    if(p !is T){
        println("p is not the type = "+p)
    }else{
        println("p is the type = "+p)
    }
}