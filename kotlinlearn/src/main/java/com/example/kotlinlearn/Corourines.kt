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
import kotlin.system.measureTimeMillis

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


fun simple(): Flow<Int> = flow {
    // The WRONG way to change context for CPU-consuming code in flow builder

    for (i in 1..3) {
        Thread.sleep(100)
        println("atcion   ${getThreadInfo()}")// pretend we are computing it in CPU-consuming way
        emit(i) // emit next value
    }

}.flowOn(Dispatchers.Default)


fun main() {
    // asyncTest1()//顺序执行 launch 是异步的 async 是同步的
    // asyncTest2()//并发执行
    //launch 返回job 执行无返回值  async 执行返回 Deferred 有返回值
    //jobTest()//线程控制  === Thread  job 控制协程中子线程的取消 开始
//    execptionTest()
    cancelTest()
    Thread.sleep(10000)
}

fun jobTest() = runBlocking {
    var context = CoroutineName("协程1") + Dispatchers.Main
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0

        while (isActive) {//当job是活跃状态继续执行
            if (System.currentTimeMillis() >= nextPrintTime) {//每秒钟打印两次消息
                print("job: I'm sleeping ${i++} ...")
                nextPrintTime += 500
            }
        }
    }

    delay(1200)//延迟1.2s
    print("等待1.2秒后")


    //job.join()
    //job.cancel()
    job.cancelAndJoin()//取消任务并等待任务完成
    print("协程被取消并等待完成")
}

//async await 会内部阻塞协程
fun asyncTest1() {
    println("start")
    GlobalScope.launch {
        val deferred: Deferred<String> = async {
            //协程将线程的执行权交出去，该线程继续干它要干的事情，到时间后会恢复至此继续向下执行
            delay(5000)//2秒无阻塞延迟（默认单位为毫秒）
            println("顺序执行1")
            "HelloWord"//这里返回值为HelloWord
        }

        val deferred2: Deferred<String> = async {
            //协程将线程的执行权交出去，该线程继续干它要干的事情，到时间后会恢复至此继续向下执行
            delay(1000)//2秒无阻塞延迟（默认单位为毫秒）
            println("顺序执行2")
            "HelloWord 2 "//这里返回值为HelloWord
        }
        //等待async执行完成获取返回值,此处并不会阻塞线程,而是挂起,将线程的执行权交出去
        //等到async的协程体执行完毕后,会恢复协程继续往下执行

        val result = deferred.await()
        println("result == $result")
        val result2 = deferred2.await()

        println("result2 == $result2")

    }
    println("end")
}
suspend fun fetchTwoDocs() =        // 在任何调度器上调用（任何线程包括主线程）
    coroutineScope {
        val deferreds = listOf(     // 同时获取两个文档
            async { //
                 },  // 异步返回第一个文档
            async {

            }   // 异步返回第二个文档
        )
        deferreds.awaitAll()        // 使用 awaitAll 等待两个网络请求返回
    }

//async 并发
fun asyncTest2() {
    println("start")
    GlobalScope.launch {
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


fun flowTest() = runBlocking<Unit> {
    println("开始  ${getThreadInfo()}")
    simple().collect { value ->
        println("接收  ${getThreadInfo()}")
        println(value)
    }
}

//coroutineScope 子协程 取消会导致 其他线程取消，
//supervisorScope 只会影响当前子协程 不影响其他子协程的执行
fun execptionTest() {
//    var scope = CoroutineScope(Job())
    var scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    scope.launch {
        try {
            supervisorScope {
                println("1")
                val job1 = launch {//第一个子协程
                    println("2")
                    throw  NullPointerException()//抛出空指针异常
                }
                val job2 = launch {//第二个子协程
                    delay(1000)
                    println("3")
                }
                try {//这里try…catch捕获CancellationException
                    job2.join()
                    println("4")//等待第二个子协程完成：
                } catch (e: Exception) {
                    println("5. $e")//捕获第二个协程的取消异常
                }
            }
        } catch (e: Exception) {//捕获父协程的取消异常
            println("6. $e")
        }
    }
}

fun cancelTest(){
    println("parent thread name = ${Thread.currentThread().name} ")
//    var scope = CoroutineScope(Dispatchers.IO)
//    var job = scope.launch(scope.coroutineContext,CoroutineStart.LAZY) {
//        println("this thread name = ${Thread.currentThread().name} 执行开始")
//        sleep(1000)
//        println("this thread name = ${Thread.currentThread().name} 执行完成")
//    }
//    println("停止 300 ms")
//    sleep(300)
//    job.cancel()
//    println("取消job")
    var scope = CoroutineScope(Dispatchers.IO)

    val job  = scope.launch {
//        launch {
//            repeat(1000) { i ->
//                println("job: I'm sleeping $i ...")
//                delay(500L)
//            }
//        }
//        println("this thread name = ${Thread.currentThread().name} 执行开始")
//        delay(5000)
//        println("this thread name = ${Thread.currentThread().name} 执行完成")
        try {
            withTimeout(1300L) {
                repeat(1000) { i ->
                    println("I'm sleeping $i ...")
                    delay(500L)
                }
            }
        }catch (e:Exception){
            println(e.toString())
        }
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
        }, {
            println(" error " + getThreadInfo())
        })
        println("主线程 结束  ${getThreadInfo()}")
        sleep(1000000L)
    }

    fun <R> safeCall(
        call: () -> R,
        callBack: ((R) -> Unit)? = null,
        onError: ((Exception) -> Unit)? = null
    ): R? {

        GlobalScope.launch(Dispatchers.Unconfined) {
            try {
                val callBackData =
                    async(Dispatchers.IO + CoroutineName("MsaTracker")) { call() }.await()
                callBack?.invoke(callBackData)
            } catch (e: java.lang.Exception) {
                onError?.invoke(e)
            }
        }
        return null
    }

    suspend fun <R> call(call: () -> R): R = suspendCoroutine {
        try {
            it.resume(call())
        } catch (e: java.lang.Exception) {
            it.resumeWithException(e)
        }
    }

    fun getThreadInfo(): String {
        return "thread name = " + Thread.currentThread().name + " thread id = " + Thread.currentThread().id
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