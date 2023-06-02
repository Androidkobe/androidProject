package com.example.kotlinlearn.coroutine

import kotlinx.coroutines.*

suspend fun logLoop(controutine: CoroutineScope) {
    var count = 0
    while (controutine.isActive) {
        action()
        println("Logging: $count")
        count++
    }
}

suspend fun action() {
    Thread.sleep(500)
}

fun main() {

    val job = GlobalScope.launch {
        val job2 = launch {
            try {
                logLoop(this)
            } catch (e: CancellationException) {
                println("Coroutine canceled")
            }
        }
        Thread.sleep(2000)
        job2.cancel()
    }

    println("Done")
    Thread.sleep(2000000000000)
}
