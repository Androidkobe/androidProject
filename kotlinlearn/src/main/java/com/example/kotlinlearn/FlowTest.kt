package com.example.kotlinlearn

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.sync.Mutex


fun main() {
//    println("开始  ${getThreadInfo()}")
//    FlowTest().test()
//    testSelectChannel()
    FlowTest().testSync()
    Thread.sleep(100000)
}




class FlowTest {

    private fun getThreadInfo(): String {
        return "thread name = " + Thread.currentThread().name + " thread id = " + Thread.currentThread().id
    }

    private fun simple(): Flow<Int> = flow {
        // The WRONG way to change context for CPU-consuming code in flow builder

        for (i in 1..3) {
            Thread.sleep(100)
            println("atcion   ${getThreadInfo()}")// pretend we are computing it in CPU-consuming way
            emit(i) // emit next value
        }

    }.flowOn(Dispatchers.Default)


    fun test() {
        CoroutineScope(Dispatchers.IO).launch {
            simple().collect { value ->
                println("接收  ${com.example.kotlinlearn.getThreadInfo()}")
                println(value)
            }
        }
    }

    fun testSync() {
        CoroutineScope(Dispatchers.IO).launch {
            var i = async<String> {
                println("async runing1")
                "async retrun this"
            }
            var mutex = Mutex()
            mutex.lock()
            mutex.unlock()
            var j = async<String> {
                delay(1000)
                println("async runing2")
                "async retrun this2"
            }
            launch(start = CoroutineStart.ATOMIC) { }

            var k = async<String> {
                println("async runing3")
                "async retrun this3"
            }
            println(i.await() + j.await())
        }
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


}


fun testSelectChannel() = runBlocking {
    val slowChannel = Channel<Int>(capacity = 1, onBufferOverflow = BufferOverflow.SUSPEND)
    val fastChannel = Channel<Int>(capacity = 3, onBufferOverflow = BufferOverflow.SUSPEND)
    //生产者协程
    launch(Dispatchers.IO) {
        for (i in 1..5) {
            if (!isActive) break
            //选择表达式不需要返回值
            select<Unit> {
                //需要发送的数据
                slowChannel.onSend(i) { channel ->
                    //lamda的参数是当前选中的channel
                    println("slow channel selected $channel send $i")
                    delay(50)
                }
                fastChannel.onSend(i) { channel ->
                    println("fast channel selected $channel send $i")
                }
            }
        }
        delay(300)
        //注意要关闭通道
        slowChannel.close()
        fastChannel.close()
    }

    //消费者协程
    launch {
        while (isActive && !slowChannel.isClosedForSend && !fastChannel.isClosedForSend) {
            //以ChannelResult携带的值作为选择表达式的值
            val result = select<Int> {
                slowChannel.onReceiveCatching {
                    println("slowChannel is receive ${it.getOrNull()}")
                    delay(100)
                    it.getOrNull() ?: -1
                }
                fastChannel.onReceiveCatching {
                    println("fastChannel is receive ${it.getOrNull()}")
                    it.getOrNull() ?: -1
                }
            }
            println("receive result : $result")
        }
    }
    delay(500)
}