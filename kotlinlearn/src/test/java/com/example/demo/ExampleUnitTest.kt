package com.example.demo

import com.example.kotlinlearn.VaribaleVsValue
import org.junit.Test


/**
 * Example local unit demo, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testWhat() {
    }

    @Test
    fun testDriveMotorbike(){
    }
    @Test
    fun testVar(){
    }
    @Test
    fun curryAdd(){
        val test : VaribaleVsValue
        test = VaribaleVsValue()
        println(test.curryAdd(3)(4))
    }

}