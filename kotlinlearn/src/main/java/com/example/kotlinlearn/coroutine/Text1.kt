package com.example.kotlinlearn.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun main() {
    GlobalScope.launch {
        println("----------start 1--------------------")
        getUserInfo(1)
        println("1---------- ${Thread.currentThread().name}")

    }
    Thread.sleep(10000000)
}

suspend fun getUserInfo(tag: Int) {
    withContext(Dispatchers.IO) {
        for (i in 1..50) {
            println("$tag - $i")
        }
    }
}