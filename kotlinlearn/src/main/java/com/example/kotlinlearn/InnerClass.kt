package com.example.kotlinlearn

fun main() {

}

class Outer {
    private val bar: Int = 1

    class Nested {
        fun foo() = 2
    }
}

val demo = Outer.Nested().foo() // == 2


/**
 * 内部类
 */
class Outer2 {
    private val bar: Int = 1

    inner class Inner {
        fun foo() = bar
    }
}

val demo2 = Outer2().Inner().foo() // == 1


//匿名内部类

//window.addMouseListener(object : MouseAdapter() {
//
//    override fun mouseClicked(e: MouseEvent) { …… }
//
//    override fun mouseEntered(e: MouseEvent) { …… }
//})