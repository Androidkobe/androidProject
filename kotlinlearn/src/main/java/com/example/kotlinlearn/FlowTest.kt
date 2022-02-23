package com.example.kotlinlearn

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking

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

    fun main() = runBlocking<Unit> {
        println("开始  ${getThreadInfo()}")
        simple().collect { value ->
            println("接收  ${getThreadInfo()}")
            println(value)
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