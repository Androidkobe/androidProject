package com.example.kotlinlearn

import kotlinx.coroutines.*
import java.lang.Thread.sleep

fun main() {
//    GlobalScopeTest().runTest()
//    RunBlockingTest().runTest()
//    RunBlockingTest().runTest2()

}


class GlobalScopeTest{
    /**
     * 不会阻塞主线程
     */
    fun runTest(){
        println("主线程 开始 id = "+Thread.currentThread().id)
        test()
        println("主线程 结束 id = "+Thread.currentThread().id)
        sleep(1000000L)
    }
    private fun test(){
        GlobalScope.launch (Dispatchers.IO){
            repeat(5){
                delay(1000L)
                println("协程 name = "+Thread.currentThread().name +" id = "+Thread.currentThread().id)
            }
        }
    }
}


class RunBlockingTest{

    /**
     *  阻塞主线程  使用当前线程
     */
    fun runTest(){
        println("主线程 开始 id = "+Thread.currentThread().id)
        test()
        println("主线程 结束 id = "+Thread.currentThread().id)
        sleep(1000000L)
    }

    private fun test(){
        runBlocking{
            launch {
                repeat(5){
                    delay(1000L)
                    println("协程 name = "+Thread.currentThread().name +" id = "+Thread.currentThread().id)
                }
            }
        }
    }

    /**
     *  阻塞主线程  使用 Default 线程 或者 main 线程  可以自由切换
     */
    fun runTest2(){
        println("主线程 开始 id = "+Thread.currentThread().id)
        test2()
        println("主线程 结束 id = "+Thread.currentThread().id)
        sleep(1000000L)
    }

    private fun test2(){
        runBlocking{
            launch ( context = Dispatchers.Default ,start = CoroutineStart.DEFAULT){
                repeat(5){
                    delay(1000L)
                    println("协程 name = "+Thread.currentThread().name +" id = "+Thread.currentThread().id)
                }
            }
        }
    }
}