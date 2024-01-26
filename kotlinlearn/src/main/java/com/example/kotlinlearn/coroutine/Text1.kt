package com.example.kotlinlearn.coroutine

import kotlinx.coroutines.delay
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.intrinsics.createCoroutineUnintercepted
import kotlin.coroutines.resume

fun main() {
    println("start")
    launch3 {
        println("before")
        delay(1_000)
        println("middle")
        delay(1_000)
        println("after")
    }
    println("end")
    Thread.sleep(3_000)
}

fun <T> launch3(block: suspend () -> T) {
    // 1、传入代码块block，使用block创建协程，
    // 2、同时自行创建一个续体，「resumeWith」最终会被调用
    val coroutine = block.createCoroutineUnintercepted(object : Continuation<T> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<T>) {
            println("resumeWith=$result")
        }
    })
    // 3、执行block协程
    coroutine.resume(Unit)
}