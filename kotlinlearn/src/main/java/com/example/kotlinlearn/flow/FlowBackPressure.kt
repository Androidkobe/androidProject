package com.example.kotlinlearn.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

fun main() {
    flowBackPressureBuffer()//数据缓存
    flowBackPressureConflate()//合并处理
    flowBackPressureCollectLatest()//收集最新值
    Thread.sleep(100000)
}

//缓存数据
fun flowBackPressureBuffer() {
    CoroutineScope(Dispatchers.IO).launch {
        val time = measureTimeMillis {//计算耗时
            flow {
                for (i in 1..1000) {
                    delay(1)//假设我们正在异步等待100毫秒
                    emit(i)//发出下一个值
                }
                //设置缓冲区逻辑
            }.buffer(capacity = 10,onBufferOverflow = BufferOverflow.SUSPEND)//缓存排放，不要等待
                .collect { value ->
                    delay(100)//假设我们处理了300毫秒
                    println(value)
                }
        }

        println("收集耗时：$time ms")
    }
}

//合并处理
fun flowBackPressureConflate() {
    CoroutineScope(Dispatchers.IO).launch {
        val time = measureTimeMillis {//计算耗时
            flow {
                for (i in 1..3) {
                    delay(100)//假设我们正在异步等待100毫秒
                    emit(i)//发出下一个值
                }
            }.conflate()//合并排放，而不是逐个处理
                .collect { value ->
                    delay(300)//假设我们处理了300毫秒
                    println(value)
                }
        }

        println("收集耗时：$time ms")
    }
}

//重新启动
fun flowBackPressureCollectLatest() {
    CoroutineScope(Dispatchers.IO).launch {
        val time = measureTimeMillis {//计算耗时
            flow {
                for (i in 1..3) {
                    delay(100)//假设我们正在异步等待100毫秒
                    emit(i)//发出下一个值
                }
            }.collectLatest{ value ->//取消并重新启动最新的值
                    delay(300)//假设我们处理了300毫秒
                    println(value)
                }
        }

        println("收集耗时：$time ms")
    }
}