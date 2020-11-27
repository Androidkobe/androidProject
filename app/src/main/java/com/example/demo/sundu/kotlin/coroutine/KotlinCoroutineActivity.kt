package com.example.demo.sundu.kotlin.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.demo.R
import kotlinx.android.synthetic.main.kotlin_coroutine_activity_layout.*
import kotlinx.coroutines.*

class KotlinCoroutineActivity : AppCompatActivity() {

    var coroutineString: StringBuffer = StringBuffer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kotlin_coroutine_activity_layout)
        startCoroutineActionA()
        startCoroutineActionB()
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
