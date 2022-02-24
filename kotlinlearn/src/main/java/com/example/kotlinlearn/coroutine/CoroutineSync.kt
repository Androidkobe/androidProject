package com.example.kotlinlearn.coroutine

import kotlinx.coroutines.*
import java.lang.Thread.sleep
import kotlin.system.measureTimeMillis

fun main(){
    syncTest()//非阻塞返回
    asyncTest2()//并发
    asyncTest3()
    sleep(1000000L)
}

fun syncTest(){
    println("Sync Test start")
    CoroutineScope(Dispatchers.IO).launch{
        val deferred : Deferred<String> = async {
            delay(1000)
            "我是返回数据"
        }
        println(deferred.await())
    }
    println("Sync Test end")
}

//sync 并发
fun asyncTest2() {
    println("start")
    CoroutineScope(Dispatchers.IO).launch {
        val time = measureTimeMillis {//计算执行时间
            val deferredOne: Deferred<Int> = async {
                delay(2000)
                println("asyncOne")
                100//这里返回值为100
            }

            val deferredTwo: Deferred<Int> = async {
                delay(3000)
                println("asyncTwo")
                200//这里返回值为200
            }

            val deferredThr: Deferred<Int> = async {
                delay(4000)
                println("asyncThr")
                300//这里返回值为300
            }

            //等待所有需要结果的协程完成获取执行结果
            val result = deferredOne.await() + deferredTwo.await() + deferredThr.await()
            println("result == $result")
        }
        println("耗时 $time ms")
    }
    println("end")
}

//sync 不使用await 不会阻塞当前协程 awati 会阻塞当前协程 指导返回数据 才会接着执行
fun asyncTest3() {
    println("start")
    CoroutineScope(Dispatchers.IO).launch {
        val time = measureTimeMillis {//计算执行时间
            val deferredOne: Deferred<Int> = async (Dispatchers.Default){
                delay(2000)
                println("asyncOne")
                100//这里返回值为100
            }

            val deferredTwo: Deferred<Int> = async (Dispatchers.Default){
                delay(3000)
                println("asyncTwo")
                200//这里返回值为200
            }

            val deferredThr: Deferred<Int> = async {
                delay(4000)
                println("asyncThr")
                300//这里返回值为300
            }
//            deferredOne.await()
//            deferredTwo.await()
//            deferredThr.await()
            //等待所有需要结果的协程完成获取执行结果
            //val result = +  +
//            println("result == $result")
        }
        println("耗时 $time ms")
    }
    println("end")
}