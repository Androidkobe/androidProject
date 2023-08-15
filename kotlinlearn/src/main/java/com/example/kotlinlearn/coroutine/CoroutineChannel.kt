package com.example.kotlinlearn.coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import java.lang.Thread.sleep

fun main() {
//    channelTest()
//    channelTest2()
//    channelTest3()
    channelTest5()
    sleep(1000000)
}



fun channelTest() {

    val sendChannel: SendChannel<Int> = GlobalScope.actor<Int> {
        while (true) {
            val element = receive()
        }
    }

    GlobalScope.launch {
        // 1. 创建 Channel
        val channel = Channel<Int>()
        // 2. Channel 发送数据
//        val job1 = launch {
//            for (i in 1..4) {
//                println("发送 ${i}")
//                delay(100)
//                channel.send(i)//发送
//            }
//            channel.close()//关闭Channel，发送结束
//        }
//        job1.cancel()
//        println("取消")

        val job1 = async(Dispatchers.IO) {
            for (i in 1..4) {
                println("发送 ${i}")
                delay(100)
                channel.send(i)//发送
            }
            channel.close()//关闭Channel，发送结束
        }
//        job1.await()//阻塞 开始等待


//        // 3. Channel 接收数据
        launch {
            repeat(3) {
                val receive = channel.receive()//接收
                println("接收 $receive")
            }
        }
    }
}

fun channelTest2(){
    CoroutineScope(Dispatchers.IO).launch{
        val channel = produce<Int> {
            for (i in 1 .. 20){
                delay(500)
                channel.send(i)
            }
        }

        launch {
            for (value in channel) {
                println("接收 ： $value")
            }
        }
    }
}

fun channelTest3() {
    CoroutineScope(Dispatchers.IO).launch {
        val channel = Channel<Int>(Channel.RENDEZVOUS, BufferOverflow.DROP_OLDEST)
        var i = 0
        launch(Dispatchers.IO) {
            while (true) {
                channel.send(i)
                delay(100)
                i++
            }
        }

        launch(Dispatchers.IO) {
            while (true) {
                var res = channel.receive()
                println("---$res")
                delay(500)
            }
        }
    }
}

fun channelTest4() {
    CoroutineScope(Dispatchers.IO).launch {
        val channel = Channel<Int>(Channel.CONFLATED)
        var i = 0
        launch(Dispatchers.IO) {
            while (channel.isClosedForSend) {
                println("--->$i")
                channel.send(i)
                delay(100)
                i++
            }
        }

        launch(Dispatchers.IO) {
            var itor = channel.iterator()
            while (itor.hasNext()) {
                var res = channel.receive()
                println("---------->$res")
                delay(1000)
            }
        }

        launch(Dispatchers.IO) {
            while (true) {
                delay(20000)
                println("---关闭")
                channel.close()
            }
        }
    }
}

fun channelTest5() {
    CoroutineScope(Dispatchers.IO).launch {
        val channel = Channel<Int>(Channel.UNLIMITED)
        var i = 0
        launch(Dispatchers.IO) {
            while (true) {
                channel.send(i)
                delay(10)
                i++
            }
        }

        launch(Dispatchers.IO) {
            var itor = channel.iterator()
            while (itor.hasNext()) {
                delay(100)
                var res = channel.receive()
                println("---------->$res")
            }
        }
    }
}