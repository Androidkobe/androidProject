package com.example.demo.sundu.jetpack.workmanager

import android.app.IntentService
import android.content.Intent
import android.util.Log

class WakeupService(name: String? = "WakeupService") : IntentService(name) {

    companion object {
        const val KEY_COMMAND: String = "command"
    }


    override fun onHandleIntent(intent: Intent?) {
        Log.e("SUNDU-WORK", "ation do wakeup service")
    }
}