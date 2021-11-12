package com.example.demo.sundu.developer.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */
class SenSorHelper(var data: SenSorData, var resultData: SenSorData) : SensorEventListener {

    private var mSensorManager: SensorManager? = null
    private var mSensor: Sensor? = null
    private var mOnShakeListener: ShakeListener? = null

    fun setOnShakeListener(context: Context, onShakeListener: ShakeListener?): Boolean {
        mSensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if (mSensorManager != null) {
            mSensor = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            if (mSensor != null) {
                mOnShakeListener = onShakeListener
                mSensorManager!!.unregisterListener(this)
                mSensorManager!!.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME)
                return true
            }
        }
        return false
    }

    fun removeListener() {
        mOnShakeListener = null
    }

    override fun onSensorChanged(event: SensorEvent) {
        val type = event.sensor.type
        if (type == Sensor.TYPE_ACCELEROMETER) {
            val values = event.values
            val x = values[0]
            val y = values[1]
            val z = values[2]
            if (data.x?.isNotEmpty() == true
                || data.y?.isNotEmpty() == true
                || data.z?.isNotEmpty() == true
            ) {
                if (Math.abs(x) > data.x?.toInt() ?: 17
                    || Math.abs(y) > data.y?.toInt() ?: 17
                    || Math.abs(z) > data.z?.toInt() ?: 17
                ) {
                    Log.e("sundu", "x = $x  y = $y  z= $z")
                    resultData.x = x.toString()
                    resultData.y = y.toString()
                    resultData.z = z.toString()

                    if (mOnShakeListener != null) {
                        mOnShakeListener!!.onShake()
                    }
                    if (mSensorManager != null) {
                        mSensorManager!!.unregisterListener(this)
                    }
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }

    interface ShakeListener {
        fun onShake()
    }
}
