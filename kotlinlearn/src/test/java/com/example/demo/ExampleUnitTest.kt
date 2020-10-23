package com.example.demo

import com.example.kotlinlearn.Motorbike
import com.example.kotlinlearn.VaribaleVsValue
import com.example.kotlinlearn.what
import org.junit.Test


/**
 * Example local unit demo, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testWhat() {
        what()
    }

    @Test
    fun testDriveMotorbike(){
        val motorbike = Motorbike()
        motorbike.drive()
    }
    @Test
    fun testVar(){
        val test : VaribaleVsValue
        test = VaribaleVsValue()
//        demo.declarVal()
//        demo.declareVar()
        var a :Any = "this is"
        test.getLength(a)
    }
    @Test
    fun curryAdd(){
        val test : VaribaleVsValue
        test = VaribaleVsValue()
        println(test.curryAdd(3)(4))
    }

}