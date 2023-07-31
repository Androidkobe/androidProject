package com.example.demo

import androidx.multidex.MultiDexApplication
import leakcanary.LeakCanary

class MyApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
    }
}