package com.example.kotlinlearn

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Thread.sleep
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

//fun main() {
//    GlobalScopeTest().runTest()
//    RunBlockingTest().runTest()
//    RunBlockingTest().runTest2()
//    GlobalScopeAsync().runTest()
//    println("主线程 开始  ${getThreadInfo()}")
//
//    runBlocking{
//        println("launch 开始  ${getThreadInfo()}")
//        GlobalScope.launch {
//            println("launch 1 开始  ${getThreadInfo()}")
//            getToken()
//        }
//        GlobalScope.launch {
//            println("launch 2 开始  ${getThreadInfo()}")
//            getResponse("")
//        }
//        GlobalScope.launch {
//            println("launch 3 开始  ${getThreadInfo()}")
//            setText("---")
//        }
//        println("launch 结束  ${getThreadInfo()}")
//        delay(10000L)
//        println("主线程 等待了10 秒 开始执行我了  ${getThreadInfo()}")
//    }

//    println("主线程 结束  ${getThreadInfo()}")
//    sleep(1000000L)
//}

suspend fun getToken(): String {
    delay(3000)
    println("getToken 开始执行，时间:  ${System.currentTimeMillis()}")
    return "ask"
}

suspend fun getResponse(token: String): String {
    delay(1000)
    println("getResponse 开始执行，时间:  ${System.currentTimeMillis()}")
    return "response"
}

fun setText(response: String) {
    println("setText 执行，时间:  ${System.currentTimeMillis()}")
}


fun getThreadInfo(): String {
    return "thread name = " + Thread.currentThread().name + " thread id = " + Thread.currentThread().id
}


//suspend fun performRequest(request: Int): String {
//    delay(1000) // imitate long-running asynchronous work
//    return "response $request"
//}
//
//fun main() = runBlocking<Unit> {
//    (1..3).asFlow() // a flow of requests
//        .transform { request ->
//            emit(performRequest(request))
//        }
//        .collect { respone->
//            println(respone)
//        }
//}

fun simple(): Flow<Int> = flow {
    // The WRONG way to change context for CPU-consuming code in flow builder

    for (i in 1..3) {
        Thread.sleep(100)
        println("atcion   ${getThreadInfo()}")// pretend we are computing it in CPU-consuming way
        emit(i) // emit next value
    }

}.flowOn(Dispatchers.Default)

fun main() = runBlocking<Unit> {
    println("开始  ${getThreadInfo()}")
    simple().collect { value ->
        println("接收  ${getThreadInfo()}")
        println(value)
    }
}

class GlobalScopeAsync {

    fun runTest() {
        println("主线程 开始  ${getThreadInfo()}")
        safeCall({
            sleep(10000)
            println(" call " + getThreadInfo())
        }, {
            println(" back " + getThreadInfo())
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