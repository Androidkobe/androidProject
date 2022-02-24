package com.example.kotlinlearn.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.NullPointerException
import java.lang.Thread.sleep

fun main() {
//    flowTest()
//    flowCatch()//异常捕获
    flowCancel()
    sleep(100000)
}

fun flowTest() {
    val flow = flow {
        for (i in 1..3) {
            delay(200)
            emit(i)//从流中发出值
        }
    }.flowOn(Dispatchers.IO)//决定执行线程

    CoroutineScope(Dispatchers.IO).launch {
        flow.onCompletion {//接收执行完成
            println("接收 flow 数据 完成")
        }.collect {//注册接收数据
            println("接收 flow 数据 $it")
        }
    }
}

fun flowCatch() {
    val flow = flow {
        for (i in 1..3) {
            delay(200)
            if (i == 2){
                throw NullPointerException()
            }
            emit(i)//从流中发出值
        }
    }.flowOn(Dispatchers.IO)

    CoroutineScope(Dispatchers.IO).launch {
        flow.onCompletion {
            println("接收 flow 数据 完成")
        }.catch {  e->//异常捕获
            println("caught error : $e")
        }.collect {
            println("接收 flow 数据 $it")
        }
    }
}

fun flowCancel(){
    CoroutineScope(Dispatchers.IO).launch {
        //1.创建一个子协程
        val job = launch {
            //2.创建flow
            val intFlow = flow {
                (1..5).forEach {
                    delay(1000)
                    //3.发送数据
                    emit(it)
                }
            }

            //4.收集数据
            intFlow.collect {//收集
                println(it)
            }
        }

        //5.在3.5秒后取消协程
        delay(3500)
        println("协程取消")
        job.cancelAndJoin()
    }
}