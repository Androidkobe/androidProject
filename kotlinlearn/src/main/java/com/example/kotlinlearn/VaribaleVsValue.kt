package com.example.kotlinlearn

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */
 
class VaribaleVsValue{
    fun declareVar(){
        var a = 1
        a = 2
        println(a)
        println(a::class)
        println(a::class.java)
        var x = 5
        x+=1
        println("x = $x")
    }

    fun declarVal() {
        val b = "a"
        //b = "b"//编译器报错
        println(b)
        println(b::class)
        println(b::class.java)
        val c : Int  = 1 //立即赋值
        val d = 2//自动推断出Int类型
        val e:Int //如果没有初始值 不能省略类型
        e =3 //明确赋值
        println("c = $c d = $d e = $e")
    }

    fun Int2Long() {
        val x:Long = 10
        val y:Int = x.toInt()
    }

    fun getLength(obj: Any): Int? {
        var result = 0
        if (obj is String) {
            // `obj` 在该条件分支内自动转换成 `String`
            println(obj::class) //class java.lang.String
            result = obj.length
            println(result)
        }
        // 在离开类型检测分支后，`obj` 仍然是 `Any` 类型
        println(obj::class) // class java.lang.Object
        return result
    }

    /**
     * 函数编程
     * 柯里化写法
     */
    fun curryAdd(x: Int): (Int) -> Int {
        return { y -> x + y }
    }
}