package com.example.kotlinlearn


fun main() {
    arry()
}

/**
 * == 对比数值 相当与equals
 * === 对比内存地址
 */
fun test1() {
    var eqTest1 = EqTest("12", "a")
    var eqTest2 = EqTest("12", "a")
    println(eqTest1 == eqTest2)
    println(eqTest1 === eqTest2)
}

class EqTest {
    var mArge = ""
    var mName = ""

    constructor(arge: String, name: String) {
        mArge = arge;
        mName = name;
    }

//    override fun equals(other: Any?): Boolean {
//        return mArge == (other as EqTest).mArge
//    }
}

/**
 * Byte 1字节
 * Short 2 字节
 * Int 4字节
 * Long 8字节
 *
 * Float 4字节
 * Double 8 字节
 */
fun numType() {
    val one = 1 // Int
    val threeBillion = 3000000000 // Long
    val oneLong = 1L // Long
    val oneByte: Byte = 1

    val pi = 3.14 // Double
    val e = 2.7182818284 // Double
    val eFloat = 2.7182818284f // Float，实际值为 2.7182817
}


/**
 * 1.1 起 可以使用_ 间隔 更易读
 */
fun num_Show() {
    val oneMillion = 1_000_000
    val creditCardNumber = 1234_5678_9012_3456L
    val socialSecurityNumber = 999_99_9999L
    val hexBytes = 0xFF_EC_DE_5E
    val bytes = 0b11010010_01101001_10010100_10010010
}

/**
 * 为空 应用会装箱
 */
fun num_Show2() {
    val a: Int = 100 //128 内会进行复用地址
    val boxedA: Int? = a//自动装箱为Integer
    val anotherBoxedA: Int? = a //自动装箱为Integer

    val b: Int = 128// 128 内会进行自动装箱复用
    val boxedB: Int? = b
    val anotherBoxedB: Int? = b

    println(boxedA === anotherBoxedA) // true
    println(boxedB === anotherBoxedB) // false
}

/**
 *shl(bits) – 有符号左移
 *shr(bits) – 有符号右移
 *ushr(bits) – 无符号右移
 *and(bits) – 位与
 *or(bits) – 位或
 *xor(bits) – 位异或
 *inv() – 位非
 */
fun weiyunsuan() {

}


/**
 * 数组
 */
fun arry() {
// 创建一个 Array<String> 初始化为 ["0", "1", "4", "9", "16"]
//    val asc = Array(5) { i -> (i * i).toString() }
//    asc.forEach { println(it) }

    val asc = Array(5) {
        when (it) {
            0 -> "aaa"
            1 -> "222"
            else -> {
            }
        }
    }
    asc.forEach { println(it) }

    //原生数组
    // 大小为 5、值为 [0, 0, 0, 0, 0] 的整型数组
    val arr1 = IntArray(5)

    // 例如：用常量初始化数组中的值
    // 大小为 5、值为 [42, 42, 42, 42, 42] 的整型数组
    val arr2 = IntArray(5) { 42 }

    // 例如：使用 lambda 表达式初始化数组中的值
    // 大小为 5、值为 [0, 1, 2, 3, 4] 的整型数组（值初始化为其索引值）
    var arr3 = IntArray(5) { it * 1 }
}












