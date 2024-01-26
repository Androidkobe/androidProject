package com.example.kotlinlearn.coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import java.lang.Thread.sleep

fun main(){
    syncTest()//非阻塞返回
    sleep(1000000L)
}

fun syncTest() {
    println("Sync Test start")
    var channel = Channel<String>(Channel.UNLIMITED)
    channel.consumeAsFlow()
    channel.receiveAsFlow()
    CoroutineScope(Dispatchers.IO).launch {
        async {
            var a = de()
            "我是返回数据$a"
        }
        println("text -------------")
    }
    println("Sync Test end")
}

suspend fun de(): String {
    delay(1000)
    val t = ""
    _uiState.update {
        it.copy(t = t)
    }
    _uiState.run {

    }
    return "sususu"
}

data class A(val t: String)

private val _uiState = MutableStateFlow(A("a"))
val uiState: StateFlow<A> = _uiState.asStateFlow()
