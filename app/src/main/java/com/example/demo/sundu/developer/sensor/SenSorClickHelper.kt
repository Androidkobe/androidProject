package com.example.demo.sundu.developer.sensor

import android.app.Activity
import android.app.Service
import android.os.Vibrator
import android.widget.Toast


class SenSorClickHelper(var activity: Activity, val data: SenSorData, val resultData: SenSorData) {

    fun reset() {
        resultData.x = "0"
        resultData.y = "0"
        resultData.z = "0"
        Toast.makeText(activity, "已重置数据", Toast.LENGTH_SHORT).show()
        senSorHelper.setOnShakeListener(activity, shareListener)
    }

    fun start() {
        resultData.x = "0"
        resultData.y = "0"
        resultData.z = "0"
        Toast.makeText(activity, "可以开始", Toast.LENGTH_SHORT).show()
        senSorHelper.setOnShakeListener(activity, shareListener)
    }

    val senSorHelper = SenSorHelper(data, resultData)

    val shareListener = object : SenSorHelper.ShakeListener {
        override fun onShake() {
//            Log.e("sundu", "震动")
            //   vibrate(2000)
        }

    }

    fun vibrate(milliseconds: Long) {
        val vib = activity.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
        vib.vibrate(milliseconds)
    }
}