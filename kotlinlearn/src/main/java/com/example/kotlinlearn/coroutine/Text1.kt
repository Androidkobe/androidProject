package com.example.kotlinlearn.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun main() {
    println("----------main start-------")
    GlobalScope.launch {
        println("----------start 1--------------------")
        println("----------getuser info start-------")
        for (i in 1..20) {
            println(" - $i")
        }
        println(Thread.currentThread().name)
        println("----------getuser info end-------")
        println("1---------- ${Thread.currentThread().name}")

    }
    println(Thread.currentThread().name)
    println("----------main end-------")
    Thread.sleep(10000000)
}

suspend fun getUserInfo(tag: Int) {
    println("----------getuser info start-------")
    withContext(Dispatchers.IO) {
        for (i in 1..50) {
            println("$tag - $i")
        }
    }
    println("----------getuser info end-------")
}