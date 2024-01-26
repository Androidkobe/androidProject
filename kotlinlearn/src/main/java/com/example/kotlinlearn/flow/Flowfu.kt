package com.example.kotlinlearn.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun main() {
    flow {
        println("start: ${Thread.currentThread().name}")
        repeat(3) {
            delay(1000)
            this.emit(it)
        }
        println("end: ${Thread.currentThread().name}")
    }.flowOn(Dispatchers.Default)
        .onEach {
            println("collect: $it, ${Thread.currentThread().name}")
        }
        .launchIn(CoroutineScope(Dispatchers.IO))

    Thread.sleep(100000000000000)
}