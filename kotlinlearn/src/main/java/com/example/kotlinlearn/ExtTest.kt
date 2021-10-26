package com.example.kotlinlearn


fun main() {
    printListSwap()
    printClassName(Rectangle())
}


//类功能扩展
fun MutableList<Int>.swap(index1: Int, index2: Int) {
    val tmp = this[index1] // “this”对应该列表
    this[index1] = this[index2]
    this[index2] = tmp
}

fun printListSwap() {
//    var array:Array(String){}
//    var list  = MutableList(5,String)()
//    list[0] = "1"
//    list[1] = "2"
//    list[2] = "3"
//    list[3] = "4"
//    list[4] = "5"
//    print(list.toString())
//    list.sw
}


//扩展
open class Shape

class Rectangle : Shape()

fun Shape.getName() = "Shape"

fun Rectangle.getName() = "Rectangle"

fun printClassName(s: Shape) {
    println(s.getName())
}


/**
 * 可接受空
 */
fun Any?.toString(): String {
    if (this == null) return "null"
    // 空检测之后，“this”会自动转换为非空类型，所以下面的 toString()
    // 解析为 Any 类的成员函数
    return toString()
}

/**
 * 支持属性扩展
 */

val <T> List<T>.lastIndex: Int
    get() = size - 1

fun extFiled() {
    var list = List<String>(5) {
        when (it) {
            0 -> "1"
            else -> "3"
        }
    }
    list.lastIndex
}


/**
 * 支持半生对象扩展
 */
class MyClass {
    companion object {}  // 将被称为 "Companion"
}

fun MyClass.Companion.printCompanion() {
    println("companion")
}

fun extCompanion() {
    MyClass.printCompanion()
}

/**
 * 支持作用域扩展
 */
fun List<String>.getLongestString() { /*……*/
}

fun extLongestString() {
    val list = listOf("red", "green", "blue")
    list.getLongestString()
}

/**
 * 扩展声明为成员
 */
class Host(val hostname: String) {
    fun printHostname() {
        print(hostname)
    }
}

class Connection(val host: Host, val port: Int) {
    fun printPort() {
        print(port)
    }

    fun Host.printConnectionString() {
        printHostname()   // 调用 Host.printHostname()
        print(":")
        printPort()   // 调用 Connection.printPort()
    }

    fun connect() {
        /*……*/
        host.printConnectionString()   // 调用扩展函数
    }
}

fun showConnection() {
    Connection(Host("kotl.in"), 443).connect()
    //Host("kotl.in").printConnectionString(443)  // 错误，该扩展函数在 Connection 外不可用
}


open class Base {}

class Derived : Base() {}

open class BaseCaller {
    open fun Base.printFunctionInfo() {
        println("Base extension function in BaseCaller")
    }

    open fun Derived.printFunctionInfo() {
        println("Derived extension function in BaseCaller")
    }

    fun call(b: Base) {
        b.printFunctionInfo()   // 调用扩展函数
    }
}

class DerivedCaller : BaseCaller() {

    override fun Base.printFunctionInfo() {
        println("Base extension function in DerivedCaller")
    }

    override fun Derived.printFunctionInfo() {
        println("Derived extension function in DerivedCaller")
    }
}

fun extShow() {
    BaseCaller().call(Base())   // “Base extension function in BaseCaller”
    DerivedCaller().call(Base())  // “Base extension function in DerivedCaller”——分发接收者虚拟解析
    DerivedCaller().call(Derived())  // “Base extension function in DerivedCaller”——扩展接收者静态解析
}