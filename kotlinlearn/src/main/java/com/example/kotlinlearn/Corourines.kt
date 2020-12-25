package com.example.kotlinlearn

import kotlinx.coroutines.*
import java.lang.Thread.sleep

fun main() {
    println("主线程 开始 id = "+Thread.currentThread().id)
    test1()
    println("主线程 结束 id = "+Thread.currentThread().id)
    sleep(1000000L)
}

private fun test1(){
    GlobalScope.launch (Dispatchers.IO){
        repeat(5){
            delay(1000L)
            println("协程 name = "+Thread.currentThread().name +" id = "+Thread.currentThread().id)
        }
    }
}