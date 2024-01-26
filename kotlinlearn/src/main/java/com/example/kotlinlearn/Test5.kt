package com.example.kotlinlearn

import java.math.RoundingMode
import java.text.DecimalFormat

fun main() {
    val df = DecimalFormat("#.#")
    df.roundingMode = RoundingMode.DOWN
    val level_1 = df.format(3f / 2f).toFloat()
    val level_2 = df.format(16f / 9f).toFloat()

    var radio = df.format(16f / 9f).toFloat()

    when (radio) {
        in 0f..level_1 -> {
            println(1)
        }

        in level_1..level_2 -> {
            println(2)
        }

        in level_2..Float.MAX_VALUE -> {
            println(3)
        }
    }
}