package com.example.kotlinlearn

import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.Thread.sleep
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

fun main() {
//    GlobalScopeTest().runTest()
//    RunBlockingTest().runTest()
//    RunBlockingTest().runTest2()
    GlobalScopeAsync().runTest()
}


class GlobalScopeAsync {

    fun runTest() {
        println("主线程 开始  ${getThreadInfo()}")
        safeCall({
            sleep(10000)
            println(" call "+getThreadInfo())
        },{
            println(" back "+getThreadInfo())
        },{
            println(" error "+getThreadInfo())
        })
        println("主线程 结束  ${getThreadInfo()}")
        sleep(1000000L)
    }

    fun <R> safeCall(call: () -> R,callBack:((R) -> Unit)? = null,onError: ((Exception) -> Unit)? = null) :R?{

        GlobalScope.launch (Dispatchers.Unconfined){
            try {
                val callBackData = async (Dispatchers.IO+CoroutineName("MsaTracker")){ call() }.await()
                callBack?.invoke(callBackData)
            }catch (e :java.lang.Exception){
                onError?.invoke(e)
            }
        }
        return null
    }
    suspend fun <R>call(call:()->R):R = suspendCoroutine {
        try {
            it.resume(call())
        }catch (e : java.lang.Exception){
            it.resumeWithException(e)
        }
    }

    fun getThreadInfo():String{
        return "thread name = "+Thread.currentThread().name + " thread id = "+Thread.currentThread().id
    }

}

class GlobalScopeTest {
    /**
     * 不会阻塞主线程
     */
    fun runTest() {
        println("主线程 开始 id = " + Thread.currentThread().id)
        test()
        println("主线程 结束 id = " + Thread.currentThread().id)
        sleep(1000000L)
    }

    private fun test() {
        GlobalScope.launch(Dispatchers.IO) {
            repeat(5) {
                delay(1000L)
                println("协程 name = " + Thread.currentThread().name + " id = " + Thread.currentThread().id)
            }
        }
    }
}


class RunBlockingTest {

    /**
     *  阻塞主线程  使用当前线程
     */
    fun runTest() {
        println("主线程 开始 id = " + Thread.currentThread().id)
        test()
        println("主线程 结束 id = " + Thread.currentThread().id)
        sleep(1000000L)
    }

    private fun test() {
        runBlocking {
            launch {
                repeat(5) {
                    delay(1000L)
                    println("协程 name = " + Thread.currentThread().name + " id = " + Thread.currentThread().id)
                }
            }
        }
    }

    /**
     *  阻塞主线程  使用 Default 线程 或者 main 线程  可以自由切换
     */
    fun runTest2() {
        println("主线程 开始 id = " + Thread.currentThread().id)
        test2()
        println("主线程 结束 id = " + Thread.currentThread().id)
        sleep(1000000L)
    }

    private fun test2() {
        runBlocking {
            launch(context = Dispatchers.Default, start = CoroutineStart.DEFAULT) {
                repeat(5) {
                    delay(1000L)
                    println("协程 name = " + Thread.currentThread().name + " id = " + Thread.currentThread().id)
                }
            }
        }
    }
}