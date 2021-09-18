package com.example.kotlinlearn.random

import java.util.*


fun main() {
    for (i in 1..1000) {
        val rundam = Math.abs(Random(System.currentTimeMillis()).nextLong())
        System.out.println(rundam)
    }
}