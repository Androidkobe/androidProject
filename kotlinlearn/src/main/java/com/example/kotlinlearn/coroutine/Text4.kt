package com.example.kotlinlearn.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun main() {
    GlobalScope.launch {
        println("----------main start-------${Thread.currentThread().name}")
        checkThread()
        println("----------main start-------${Thread.currentThread().name}")
    }
    Thread.sleep(10000000)
}

suspend fun checkThread() {
    println("----------checkThread start-------${Thread.currentThread().name}")
    withContext(Dispatchers.IO) {
        for (i in 1..10) {
            println("----$i------for-------${Thread.currentThread().name}")
        }
    }
    println("----------checkThread end-------${Thread.currentThread().name}")
}