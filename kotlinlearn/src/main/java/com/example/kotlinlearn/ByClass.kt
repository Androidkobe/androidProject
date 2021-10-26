package com.example.kotlinlearn


/**
 * 委托模式已经证明是实现继承的一个很好的替代方式， 而 Kotlin 可以零样板代码地原生支持它。
 * Derived 类可以通过将其所有公有成员都委托给指定对象来实现一个接口 Base：
 */

//interface BaseA {
//    fun print()
//}
//
//class BaseImpl(val x: Int) : BaseA {
//    override fun print() { print(x) }
//}
//
//class Derive(b: BaseA) : BaseA by b
//
//
//fun main() {
//    val b = BaseImpl(10)
//    Derive(b).print()
//}

/**
 * 覆盖由委托实现的接口成员
覆盖符合预期：编译器会使用 override 覆盖的实现而不是委托对象中的。
如果将 override fun printMessage() { print("abc") } 添加到 Derived，
那么当调用 printMessage 时程序会输出“abc”而不是“10”：
 */
//interface Base {
//    fun printMessage()
//    fun printMessageLine()
//}
//
//class BaseImpl(val x: Int) : Base {
//    override fun printMessage() { print(x) }
//    override fun printMessageLine() { println(x) }
//}
//
//class Derived(b: Base) : Base by b {
//    override fun printMessage() { print("abc") }
//}
//
//fun main() {
//    val b = BaseImpl(10)
//    Derived(b).printMessage()
//    Derived(b).printMessageLine()
//}