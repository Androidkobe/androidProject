package com.example.kotlinlearn

fun main(args: Array<String>) {
    var data = Data()
    test(data)
    Thread.sleep(1000000000)
}

fun test(data: Data?): Boolean {
    println("start")
    data?.also {
        println(1)

        if (it.age == 1) {
            return true
        }

        println(2)
    }

    println("over")
    return false
}

class Data {
    var age = 1
}