package com.example.kotlinlearn.coroutine

import kotlinx.coroutines.*
import java.lang.Thread.sleep
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

fun main() {
    //阻塞线程
//    RunBlocking()
    //非阻塞线程
//    GlobalScope_Launch() //全局作用域
//    CoroutineScope_Launch()//局部作用域
//    CoroutineScopeLaunchTest()//局部作用域
//    coroutineScope()//coroutineScope 一个子协程 失败 整个作用域停止
    supervisorScope()//supervisorScope 一个子协程 失败 不影响其它作用域
    sleep(100000L)
}


private fun RunBlocking() {
    runBlocking {
        launch {
            repeat(5) {
                delay(1000L)
                log("协程 name = " + Thread.currentThread().name + " id = " + Thread.currentThread().id)
            }
        }
    }
}

/**
 * 非阻塞线程
 * 全局作用域
 */
private fun GlobalScope_Launch() {
    log("start")
    //创建一个全局作用域协程，不会阻塞当前线程，生命周期与应用程序一致
    GlobalScope.launch {
        //在这1000毫秒内该协程所处的线程不会阻塞
        //协程将线程的执行权交出去，该线程继续干它要干的事情，到时间后会恢复至此继续向下执行
        delay(1000)//1秒无阻塞延迟（默认单位为毫秒）
        log("GlobalScope.launch")
    }
    log("end")//主线程继续，而协程被延迟
}

/**
 * 非阻塞线程
 * 局部i作用域
 * 需要传入上下文 CoroutineContext
 * Dispatchers.IO 底层实现了CoroutineContext
 * 生命周期由Dispatchers.IO 控制
 */
private fun CoroutineScope_Launch() {
    log("start")
    //开启一个IO模式的协程，通过协程上下文创建一个CoroutineScope对象,需要一个类型为CoroutineContext的参数
    val job = CoroutineScope(Dispatchers.IO).launch {
        delay(1000)//1秒无阻塞延迟（默认单位为毫秒）
        log("CoroutineScope.launch")
    }
    log("end")//主线程继续，而协程被延迟
}


/**
 *  非阻塞线程
 */
private  fun CoroutineScopeLaunchTest() {
    log("主线程 开始 id = " + Thread.currentThread().id)
    // 创建协程作用域 与当前线程 生命周期绑定  线程结束 协程包含子协程 一起结束
    var job = CoroutineScope(Dispatchers.IO).launch {
        delay(1000)
        log("Coroutine . launch ")

        var job2 =  launch {
            repeat(10){
                delay(100)
                log("job2 ${getThreadInfo()} -- $it")
            }
        }

        withContext(Dispatchers.Default){
            repeat(10){
                delay(100)
                log("job3 ${getThreadInfo()} -- $it")
            }
        }
        var job1 =  launch(Dispatchers.IO) {
            repeat(10){
                delay(1000)
                log("job1 ${getThreadInfo()} -- $it")
            }
        }
        //协程阻塞 等待执行完成
        job1.join()
    }
    log("主线程 结束 id = " + Thread.currentThread().id)
//    sleep(5700)
    log("end")
}

fun coroutineScope(){
    CoroutineScope(Dispatchers.Default).launch{
        launch {
            delay(1000)
            log("child launch 1")
        }
        launch {
            delay(2000)
            log("child launch 2")
        }
        launch {
            delay(3000)
            log("child launch 3")
        }
        launch {
            delay(4000)
            log("child launch 4 -----")
        }
        launch {
            delay(5000)
            log("child launch 5 -----")
        }
        launch {
            delay(6000)
            log("child launch 6 -----")
        }
        coroutineScope{
            launch {
                delay(1000)
                log("coroutineScope child launch 1")
            }
            launch {
                delay(2000)
                log("coroutineScope child launch 2")
            }
            launch {
                delay(3000)
                log("coroutineScope child launch 3")
            }

            launch {
                delay(3000)
                log("coroutineScope child launch error")
                throw java.lang.Exception()
            }

            launch {
                delay(4000)
                log("coroutineScope child launch 4")
            }

            launch {
                delay(5000)
                log("coroutineScope child launch 5")
            }
            launch {
                delay(6000)
                log("coroutineScope child launch 6")
            }
        }
    }
}

fun supervisorScope(){
    CoroutineScope(Dispatchers.Default).launch{
        launch {
            delay(1000)
            log("child launch 1 ")
        }
        launch {
            delay(2000)
            log("child launch 2")
        }
        launch {
            delay(3000)
            log("child launch 3")
        }
        launch {
            delay(4000)
            log("child launch 4 -----")
        }
        launch {
            delay(5000)
            log("child launch 5 -----")
        }
        launch {
            delay(6000)
            log("child launch 6 -----")
        }
        supervisorScope{
            launch {
                delay(1000)
                log("coroutineScope child launch 1")
            }
            launch {
                delay(2000)
                log("coroutineScope child launch 2")
            }
            launch {
                delay(3000)
                log("coroutineScope child launch 3")
            }

            launch {
                delay(3000)
                log("coroutineScope child launch error")
                throw java.lang.Exception()
            }

            launch {
                delay(4000)
                log("coroutineScope child launch 4")
            }

            launch {
                delay(5000)
                log("coroutineScope child launch 5")
            }
            launch {
                delay(6000)
                log("coroutineScope child launch 6")
            }
        }
    }
}

fun log(msg:Any?){
    println(msg.toString()+ getThreadInfo())
}


suspend fun getToken(): String {
    delay(3000)
    log("getToken 开始执行，时间:  ${System.currentTimeMillis()}")
    return "ask"
}

suspend fun getResponse(token: String): String {
    delay(1000)
    log("getResponse 开始执行，时间:  ${System.currentTimeMillis()}")
    return "response"
}

fun setText(response: String) {
    log("setText 执行，时间:  ${System.currentTimeMillis()}")
}


fun getThreadInfo(): String {
    return "thread name = " + Thread.currentThread().name + " thread id = " + Thread.currentThread().id
}


class GlobalScopeAsync {

    fun runTest() {
        log("主线程 开始  ${getThreadInfo()}")
        safeCall({
            sleep(10000)
            log(" call " + getThreadInfo())
        }, {
            log(" back " + getThreadInfo())
        },{
            log(" error "+getThreadInfo())
        })
        log("主线程 结束  ${getThreadInfo()}")
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
        log("主线程 开始 id = " + Thread.currentThread().id)
        test()
        log("主线程 结束 id = " + Thread.currentThread().id)
        sleep(1000000L)
    }

    private fun test() {
        GlobalScope.launch(Dispatchers.IO) {
            repeat(5) {
                delay(1000L)
                log("协程 name = " + Thread.currentThread().name + " id = " + Thread.currentThread().id)
            }
        }
    }
}

