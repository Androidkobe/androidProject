package com.example.demo.sundu.kotlin.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.demo.R
import kotlinx.android.synthetic.main.kotlin_coroutine_activity_layout.*
import kotlinx.coroutines.*

class KotlinCoroutineActivity : AppCompatActivity() {

    var coroutineString: StringBuffer = StringBuffer()

    var scope : CoroutineScope = MainScope()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kotlin_coroutine_activity_layout)
            scope.launch(CoroutineExceptionHandler{ _, throwable ->
                Log.e("sundu", "throwable - ${throwable.toString()}")
            }) {
                   when(3){
                       1->{action()}
                       2->{
                           try {
                               action2()
                           }catch (e:java.lang.Exception){
                                println("----------${e.toString()}")
                           }
                       }
                       3->{action3()}
                       4->{
                            action4()
                       }
                       5->{
                           action5()
                       }
                   }
            }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.e("sundu","onDetachedFromWindow")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("sundu","destory")
        scope.cancel()
    }

    suspend fun action() {
        coroutineScope(){
            launch {
                for (i in 0..10) {
                    delay(1000)
                    Log.e("sundu","一 ${Thread.currentThread().name} $i")
                }
            }
            launch {
                for (i in 0..10) {
                    delay(1000)
                    Log.e("sundu","子一 ${Thread.currentThread().name} $i")
                }
            }
            launch {
                    for (i in 0..10) {
                        delay(1000)
                        Log.e("sundu", "二 ${Thread.currentThread().name} $i")
                        if (i == 5) {
                            throw Exception("哈哈1")
                        }
                    }
            }
        }
    }
    suspend fun action2() = coroutineScope{
            launch {
                for (i in 0..10) {
                    delay(1000)
                    Log.e("sundu","一 ${Thread.currentThread().name} $i")
                }
            }
            launch {
                for (i in 0..10) {
                    delay(1000)
                    Log.e("sundu","子一 ${Thread.currentThread().name} $i")
                }
            }
            launch {

                    for (i in 0..10) {
                        delay(1000)
                        Log.e("sundu", "二 ${Thread.currentThread().name} $i")
                        if (i == 5) {
                            throw Exception("哈哈2")
                        }
                    }

            }
        }

    suspend fun action3() = supervisorScope{
        launch {
            for (i in 0..10) {
                delay(1000)
                Log.e("sundu","一 ${Thread.currentThread().name} $i")
            }
        }
        launch {
            for (i in 0..10) {
                delay(1000)
                Log.e("sundu","子一 ${Thread.currentThread().name} $i")
            }
        }
        launch {

                for (i in 0..10) {
                    delay(1000)
                    Log.e("sundu", "二 ${Thread.currentThread().name} $i")
                    if (i == 5) {
                        throw Exception("哈哈3")
                    }
                }
        }
    }

    suspend fun action4() = supervisorScope{
        val a = async {
            for (i in 0..10) {
                delay(1000)
                Log.e("sundu","一 ${Thread.currentThread().name} $i")
            }
        }
        val b = async {
            for (i in 0..10) {
                delay(1000)
                Log.e("sundu","子一 ${Thread.currentThread().name} $i")
            }
        }
        val c = async {
            try {
                for (i in 0..10) {
                    delay(1000)
                    Log.e("sundu", "二 ${Thread.currentThread().name} $i")
                    if (i == 5) {
                        throw Exception("哈哈")
                    }
                }
            }catch (e:java.lang.Exception){

            }
        }
        a.await()
        b.await()
        try {
            c.await()
        } catch (e: java.lang.Exception) {

        }
    }

    suspend fun action5() = coroutineScope{
        val a = async {
            for (i in 0..10) {
                delay(1000)
                Log.e("sundu","一 ${Thread.currentThread().name} $i")
            }
        }
        val b = async {
            for (i in 0..10) {
                delay(1000)
                Log.e("sundu","子一 ${Thread.currentThread().name} $i")
            }
        }
        val c = async {
            try {
                for (i in 0..10) {
                    delay(1000)
                    Log.e("sundu", "二 ${Thread.currentThread().name} $i")
                    if (i == 5) {
                        throw Exception("哈哈")
                    }
                }
            }catch (e:java.lang.Exception){

            }
        }
        a.await()
        b.await()
        try {
            c.await()
        } catch (e: java.lang.Exception) {

        }
    }
    /**
     * 第一种方式
     */
    private fun startCoroutineActionA() {
        start_coroutine_a.setOnClickListener {
            coroutineString.append(Thread.currentThread().name + "  ===> click\n")
            coroutineActionAGetData()
        }
    }

    private fun coroutineActionAGetData() {
        GlobalScope.launch(Dispatchers.IO) {
            coroutineString.append(Thread.currentThread().name + "  ===> getdata\n")
            val string = fetchDataA()
            withContext(Dispatchers.Main) {
                coroutineString.append(Thread.currentThread().name + "  ===> setText : $string \n")
                end_coroutine_text_a.text = coroutineString
            }
        }
    }

    private suspend fun fetchDataA(): String {
        coroutineString.append(Thread.currentThread().name + "  ===> this is getString\n")
        delay(2000)
        return "this is getString Data "
    }


    /**
     * 第二种方式
     */
    private fun startCoroutineActionB() {
        val mainScope = MainScope()
        start_coroutine_b.setOnClickListener {
            coroutineString.append(Thread.currentThread().name + "  B===> click\n")
            mainScope.launch {
                coroutineString.append(Thread.currentThread().name + "  B===> lunch\n")
                end_coroutine_text_b.text = async(Dispatchers.IO, CoroutineStart.DEFAULT){
                    coroutineString.append(Thread.currentThread().name + "  B===> async action\n")
                    delay(2000)
                    return@async coroutineString.append(Thread.currentThread().name + "  B===> async action return \n")
                }.await()
            }
        }
    }

}
