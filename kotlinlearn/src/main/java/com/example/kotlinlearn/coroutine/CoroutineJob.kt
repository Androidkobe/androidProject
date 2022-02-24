package com.example.kotlinlearn.coroutine

import kotlinx.coroutines.*

/**
 * `Job` 还可以有层级关系，一个`Job`可以包含多个子`Job`，
 * 当父`Job`被取消后，所有的子`Job`也会被自动取消；
 * 当子`Job`被取消或者出现异常后父`Job`也会被取消。
 * 具有多个子 `Job` 的父`Job` 会等待所有子`Job`完成(或者取消)后，自己才会执行完成。
 *
 * join()`是一个挂起函数，它需要等待协程的执行，如果协程尚未完成，`join()`立即挂起，直到协程完成；
 * 如果协程已经完成，`join()`不会挂起，而是立即返回
 */
fun main(){
    test()
}

/**
 * join 会阻塞线程
 */
fun test() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch (Dispatchers.IO){
        var nestPrintTime = startTime
        var i = 0
        while (isActive){//job 是活跃的 那就继续执行
            if(System.currentTimeMillis() >= nestPrintTime){
                println("job : I'm sleeping ${i++}")
                nestPrintTime +=500
            }
        }
    }
    delay(1200)//延迟1.2s
    println("delay 1.2 s after")
    job.cancel()
    //线程取消后会回调
    job.invokeOnCompletion {
        println("------${it.toString()}")
    }
}