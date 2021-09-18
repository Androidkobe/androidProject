package com.example.demo.sundu.jetpack.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWroker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.e("sundu", "MyWroker dowork")
        return Result.success()
    }
}