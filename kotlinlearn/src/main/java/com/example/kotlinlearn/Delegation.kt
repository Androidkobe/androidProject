package com.example.kotlinlearn

import java.util.*
import javax.xml.crypto.Data

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */

fun main() {
    print(foo)
}

inline fun <reified T> jisuan(){
    var p = 2
    if(p !is T){
        println("p is not the type = "+p)
    }else{
        println("p is the type = "+p)
    }
}
val foo: Foo
    inline get() = Foo()

var bar: Bar
    get() = ……
inline set(v) { …… }

inline var bar: Bar
    get() = ……
set(v) { …… }