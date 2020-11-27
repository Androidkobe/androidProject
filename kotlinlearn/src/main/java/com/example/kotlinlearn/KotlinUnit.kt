package com.example.kotlinlearn

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */
fun main(args: Array<String>) {
//    println(KotlinUnit().unitExample())
//    KotlinUnit().methodAction()
//    KotlinUnit().methodAction1()
    var test : String? = null
    test?.let{
        print(it)
    }
}


class KotlinUnit {
//    fun unitExample(){
//        println("test,Unit")
//        return kotlin.Unit
//    }

//    fun unitExample():kotlin.Unit{
//        println("test,Unit")
//        return
//    }

    fun unitExample() {
        println("test,Unit")
        return
    }

    fun methodAction() {

        //lambda 闭包写在函数体外部， 形参中的最后一个形参是函数参数  可以这么写
        action(1, 1) { i: Int, i1: Int ->
            println("函数 回调 -- 第1种传递写法 = $i ==$i1")
        }

        //lambda 闭包作为形参
        action(1, 2, { i: Int, i1: Int ->
            println("函数 回调 -- 第2种传递写法 = $i ==$i1")
        })


        //一步步的 显示调用方式
        val method: (second: Int, three: Int) -> Unit = { i: Int, i1: Int ->
            println("函数 回调 -- 第3种传递写法 = $i ==$i1")
        }
        action(1, 3, method)
    }


    private fun action(first: Int, second: Int, callBack: (first: Int, second: Int) -> Unit) {
        callBack(first, second)
    }

    fun methodAction1() {

        //lambda 闭包写在函数体外部， 形参中的最后一个形参是函数参数  可以这么写
        action1(1, 1) { i: Int, i1: Int ->
            println("函数 回调 -- 第1种传递写法 = $i ==$i1")
            return@action1 (i+i1)
        }

        //lambda 闭包作为形参
        action1(1, 2, { i: Int, i1: Int ->
            println("函数 回调 -- 第2种传递写法 = $i ==$i1")
            (i+i1)//没有return 最后一个就是闭包的返回值
        })


        //一步步的 显示调用方式
        val method: (second: Int, three: Int) -> Int = { i: Int, i1: Int ->
            println("函数 回调 -- 第3种传递写法 = $i ==$i1")
           i+i1
        }
        action1(1, 3, method)
    }


    private fun action1(first: Int, second: Int, callBack: (first: Int, second: Int) -> Int) {
        println("first + second = "+callBack(first, second))
    }
}