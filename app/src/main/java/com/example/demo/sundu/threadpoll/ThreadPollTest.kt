package com.example.demo.sundu.threadpoll

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.databinding.ActivityThreadPollLayoutBinding
import java.util.concurrent.*

class ThreadPollTest : AppCompatActivity() {

    private val CPU_COUNT = Runtime.getRuntime().availableProcessors()

    private val MINIMUM_CPU_COUNT = if (CPU_COUNT < 4) 4 else CPU_COUNT

    private val CORE_POOL_SIZE = MINIMUM_CPU_COUNT + 1

    private val MAXIMUM_POOL_SIZE = MINIMUM_CPU_COUNT * 2 + 1

    class TryPush : RejectedExecutionHandler {
        override fun rejectedExecution(r: Runnable, e: ThreadPoolExecutor) {
            e.queue.poll()
            e.execute(r)
            Log.e("sundu", "重新加入")
        }
    }

    val BINDER_CACHED_EXECUTOR: ThreadPoolExecutor = ThreadPoolExecutor(
        2,
        2, 1, TimeUnit.SECONDS, LinkedBlockingQueue(10), TryPush()
    )

    var item = 0

    val bindeView by lazy {
        ActivityThreadPollLayoutBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindeView.root)
        bindeView.addone.setOnClickListener {
            addone()
        }
        bindeView.addall.setOnClickListener {
            addAll()
        }
        bindeView.addtimeout.setOnClickListener {
            addTimeout()
        }

        bindeView.cleanhead.setOnClickListener {
            cleanhead()
        }

        bindeView.clean.setOnClickListener {
            clean()
        }
    }

    private fun addTimeout() {

        BINDER_CACHED_EXECUTOR.execute(CustomTimeOutRunable(itemadd()))
        logInfo()
    }

    fun addAll() {
        for (i in 0 until 12) {
            BINDER_CACHED_EXECUTOR.execute(getRunnable(itemadd()))
        }
        logInfo()
    }

    fun addone() {
        BINDER_CACHED_EXECUTOR.execute(getRunnable(itemadd()))
        logInfo()
    }

    fun cleanhead() {
        BINDER_CACHED_EXECUTOR.queue.poll()
        logInfo()
    }

    fun clean() {
        BINDER_CACHED_EXECUTOR.queue.clear()
        logInfo()
    }

    fun logInfo() {
        Log.e(
            "sundu", "active = ${BINDER_CACHED_EXECUTOR.activeCount} " +
                    "queue = ${BINDER_CACHED_EXECUTOR.queue.size} " +
                    "completed ${BINDER_CACHED_EXECUTOR.completedTaskCount}" +
                    "taskCount = ${BINDER_CACHED_EXECUTOR.taskCount}"
        )
    }

    fun itemadd(): Int {
        item += 1
        return item
    }

    //阻塞任务
    fun getRunnable(falg: Int): Runnable {
        return CustomRunable(falg)
    }

    class CustomRunable(falg: Int) : Runnable {
        var test = 0

        init {
            test = falg
        }

        override fun run() {
            Thread.sleep(5000)
            Log.e("sundu", "current runable = $test")
        }

    }

    class CustomTimeOutRunable(falg: Int) : Runnable {
        var test = 0

        init {
            test = falg
        }

        override fun run() {
            try {
                var future = FutureTask {
                    Log.e("sundu", "current runable = $test 开始执行了")
                    Thread.sleep(5000)
                    Log.e("sundu", "current runable = $test 结束了")
                }
                future.run()
                future.get(1, TimeUnit.MILLISECONDS)
            } catch (e: Exception) {
                Log.e("sundu", "current runable = $test 超时了")
            }
        }

    }
}