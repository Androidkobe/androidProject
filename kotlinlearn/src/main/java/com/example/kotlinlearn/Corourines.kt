package com.example.kotlinlearn

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Thread.sleep
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

fun main() {
    //阻塞线程
    RunBlocking()
    //非阻塞线程
    GlobalScope_Launch() //
    CoroutineScope_Launch()//
    CoroutineScopeLaunchTest()//
    sleep(100000L)
}


private fun RunBlocking() {
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
 * 非阻塞线程
 * 全局作用域
 */
private fun GlobalScope_Launch() {
    println("start")
    //创建一个全局作用域协程，不会阻塞当前线程，生命周期与应用程序一致
    GlobalScope.launch {
        //在这1000毫秒内该协程所处的线程不会阻塞
        //协程将线程的执行权交出去，该线程继续干它要干的事情，到时间后会恢复至此继续向下执行
        delay(1000)//1秒无阻塞延迟（默认单位为毫秒）
        println("GlobalScope.launch")
    }
    println("end")//主线程继续，而协程被延迟
}

/**
 * 非阻塞线程
 * 局部i作用域
 * 需要传入上下文 CoroutineContext
 * Dispatchers.IO 底层实现了CoroutineContext
 * 生命周期由Dispatchers.IO 控制
 */
private fun CoroutineScope_Launch() {
    println("start")
    //开启一个IO模式的协程，通过协程上下文创建一个CoroutineScope对象,需要一个类型为CoroutineContext的参数
    val job = CoroutineScope(Dispatchers.IO).launch {
        delay(1000)//1秒无阻塞延迟（默认单位为毫秒）
        println("CoroutineScope.launch")
    }
    println("end")//主线程继续，而协程被延迟
}


/**
 *  非阻塞线程
 */
private  fun CoroutineScopeLaunchTest() {
    println("主线程 开始 id = " + Thread.currentThread().id)
    // 创建协程作用域 与当前线程 生命周期绑定  线程结束 协程包含子协程 一起结束
    var job = CoroutineScope(Dispatchers.IO).launch {
        delay(1000)
        println("Coroutine . launch ")

        var job2 =  launch {
            repeat(10){
                delay(100)
                println("job2 ${getThreadInfo()} -- $it")
            }
        }

        withContext(Dispatchers.Default){
            repeat(10){
                delay(100)
                println("job3 ${getThreadInfo()} -- $it")
            }
        }
        var job1 =  launch(Dispatchers.IO) {
            repeat(10){
                delay(1000)
                println("job1 ${getThreadInfo()} -- $it")
            }
        }
        //协程阻塞 等待执行完成
        job1.join()
    }
    println("主线程 结束 id = " + Thread.currentThread().id)
//    sleep(5700)
    println("end")
}

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