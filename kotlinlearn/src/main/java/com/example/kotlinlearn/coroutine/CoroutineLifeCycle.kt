package com.example.kotlinlearn.coroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

fun main() {
    scopeTest()
}

fun scopeTest(){
    //创建一个根协程
    GlobalScope.launch {//父协程
        launch {//子协程
            println("GlobalScope的子协程")
        }
        launch {//第二个子协程
            println("GlobalScope的第二个子协程")
        }
    }

    //为UI组件创建主作用域
    val mainScope = MainScope()
    mainScope.launch {//启动协程
        println("main")
    }
}