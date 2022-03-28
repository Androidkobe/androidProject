package com.example.kotlinlearn.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

fun main() {
//    normal()
//    cancel()
    pressback2()
    Thread.sleep(10000)
}

fun normal() {
    GlobalScope.launch {
        //1.创建一个Flow
        flow<Int> {
            for (i in 1..3) {
                delay(200)
                //2.发出数据
                emit(i)
            }
        }.catch {
            println("异常 $it")
        }.onCompletion {
            println("完成 $it")
        }.collect {
            println("收集 $it")
        }
    }
}


fun cancel() {
    GlobalScope.launch {
        //1.创建一个Flow
        val job = launch {
            flow<Int> {
                for (i in 1..10) {
                    delay(1000)
                    //2.发出数据
                    emit(i)
                }
            }.catch {
                println("异常 $it")
            }.onCompletion {
                println("完成 $it")
            }.collect {
                println("收集 $it")
            }
        }
        delay(4000)
        job.cancel()
    }
}

/**
 * buffer 缓存处理  .buffer(capacity = 10,onBufferOverflow = BufferOverflow.SUSPEND
 *       SUSPEND,  溢出时挂起
 *       DROP_OLDEST, 溢出时放弃最旧的数据，保留最新的数据
 *       DROP_LATEST 溢出时放弃最新的数据 保留最就的数据
 *
 * conflate 保存最新值
 *
 * collectLatest 新值发送时，取消之前的
 */

fun pressback1() {
    var coroutineScope = CoroutineScope(Job())
    coroutineScope.launch {
        val time = measureTimeMillis {//计算耗时
            flow {
                for (i in 1..3) {
                    delay(100)//假设我们正在异步等待100毫秒
                    emit(i)//发出下一个值
                }
            }.buffer()//缓存排放，不要等待
                .collect { value ->
                    delay(300)//假设我们处理了300毫秒
                    print(value)
                }
        }

        print("收集耗时：$time ms")
    }
}

fun pressback2() {
    var coroutineScope = CoroutineScope(Job())
    coroutineScope.launch {
        val time = measureTimeMillis {
            flow {
                for (i in 1..5) {
                    delay(1000)//假设我们正在异步等待100毫秒
                    emit(i)//发出下一个值
                }
            }.collectLatest { value ->//取消并重新启动最新的值
                print("收集的值：$value")
                delay(2000)//假设我们处理了300毫秒
                print("完成：$value")
            }
        }

        print("收集耗时：$time ms")
    }
}