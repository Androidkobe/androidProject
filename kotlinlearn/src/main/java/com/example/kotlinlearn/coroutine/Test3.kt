package com.example.kotlinlearn.coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.selects.select


fun main() {
    testSelect5()
}

//从zxing 获取二维码信息
suspend fun getQrcodeInfoFromZxing(): String {
    println("zxing 获取二维码")
    //模拟耗时
    delay(2000)
    return "z xing 获取二维码 fish"
}

//从zbar 获取二维码信息
suspend fun getQrcodeInfoFromZbar(): String {
    println("zbar 获取二维码")
    delay(1000)
    return "zbar 获取二维码 fish"
}

fun testSelect1() {
    var starTime = System.currentTimeMillis()
    var deferredZxing = GlobalScope.async {
        getQrcodeInfoFromZxing()
    }

    var deferredZbar = GlobalScope.async {
        getQrcodeInfoFromZbar()
    }

    runBlocking {
        println("z xing await")
        //挂起等待识别结果
        var qrcoe1 = deferredZxing.await()
        delay(100000)
        println("z bar await")
        //挂起等待识别结果
        var qrcode2 = deferredZbar.await()
        println("qrcode1=$qrcoe1 qrcode2=$qrcode2 useTime:${System.currentTimeMillis() - starTime} ms")
    }
}

fun testSelect5() {
    runBlocking {
        println("start")
        var starTime = System.currentTimeMillis()
        var receiveChannelZxing = produce {
            //发送数据
            send("I'm fish")
        }

        //确保channel 数据已经send
//        delay(1000)
        var result = select<String> {
            //监听是否有数据发送过来
            receiveChannelZxing.onReceive { value ->
                "zxing result $value"
            }
            Thread.sleep(5000)
        }

        var result2 = select<String> {
            //监听是否有数据发送过来
            receiveChannelZxing.onReceiveCatching {
                val value = it.getOrNull()
                if (value != null) {
                    "a.ssss"
                } else {
                    "关闭了"
                }
            }
        }
        println("result from $result $result2 useTime:${System.currentTimeMillis() - starTime}")
    }
}

fun testSequence() {
    fun sequence(): Sequence<Int> = sequence {
        for (i in 1..3) {
            Thread.sleep(1000)
            yield(1)
        }
    }
    sequence().forEach {
        println("value = $it")
    }
}

fun testFlow() {
    GlobalScope.launch {
        var channel = Channel<String>()
    }
}
