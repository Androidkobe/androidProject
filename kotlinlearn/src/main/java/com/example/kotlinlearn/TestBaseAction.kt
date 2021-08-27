package com.example.kotlinlearn

fun main() {
    System.out.println(Test().par?.par1?.a ?: true)
}

class Test {
    val par: Paramter? = Paramter()

    class Paramter {
        val par1: Paramter1? = null

        init {
            System.out.println("paramter init")
        }
    }

    class Paramter1 {
        val a = false

        init {
            System.out.println("paramter1 init")
        }
    }
}