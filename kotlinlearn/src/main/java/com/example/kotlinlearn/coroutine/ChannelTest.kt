package com.example.kotlinlearn.coroutine

//fun main() = runBlocking {
//    val flow = flow<Int> {
//        repeat(128) {
//            println("send $it")
//            emit(it)
//        }
//    }.buffer(10)
//
//    println("休息")
//    delay(1000)
//
//    val produceIn = flow.produceIn(this)
//    for (ele in produceIn) {
//        println("receive $ele")
//    }
//}

// 创建接口
private val phone: String by lazy { "123" }
private val name = "zhang san"

fun hello() {
    println(name)
    println(phone)
}