package com.example.kotlinlearn.coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import java.lang.Thread.sleep

fun main() {
    channelTest()
    channelTest2()
    sleep(1000000)
}

fun channelTest(){
    GlobalScope.launch {
        // 1. 创建 Channel
        val channel = Channel<Int>()

        // 2. Channel 发送数据
        launch {
            for (i in 1..4) {
                delay(100)
                channel.send(i)//发送
            }
            channel.close()//关闭Channel，发送结束
        }

        // 3. Channel 接收数据
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
            for (value in channel){
                println("接收 ： $value")
            }
        }
    }


}