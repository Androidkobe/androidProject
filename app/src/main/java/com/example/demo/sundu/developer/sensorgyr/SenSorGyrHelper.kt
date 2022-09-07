package com.example.demo.sundu.developer.sensorgyr

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import android.view.OrientationEventListener
import java.lang.Math.toDegrees


class SenSorGyrHelper : SensorEventListener {

    private var gravity = FloatArray(3) //用来保存加速度传感器的值

    private val r = FloatArray(9)
    private var geomagnetic = FloatArray(3) //用来保存地磁传感器的值

    private val values = FloatArray(3) //用来保存最终的结果


    private var mSensorManager: SensorManager? = null
    private var magneticSensor: Sensor? = null
    private var accelerometerSensor: Sensor? = null

    private var lastDegreeZ = 0.0
    private var lastDegreeX = 0.0
    private var lastDegreeY = 0.0
    private var lastTime = 0.0
    private var mAngle = 0.0
    private var lastAngle = -1.0

    private val rotateSpace = 60

    private var listener: SensorRotateListener? = null

    fun registerListener(context: Context, sensorRotateListener: SensorRotateListener?) {
        val systemService = context.getSystemService(Context.SENSOR_SERVICE)
        if (systemService != null) {
            mSensorManager = systemService as SensorManager
            magneticSensor = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            accelerometerSensor = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            magneticSensor = mSensorManager!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
            mSensorManager!!.registerListener(
                this,
                magneticSensor,
                SensorManager.SENSOR_DELAY_FASTEST
            )
            mSensorManager!!.registerListener(
                this,
                accelerometerSensor,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
        val orientationEventListener: OrientationEventListener =
            object : OrientationEventListener(context) {
                override fun onOrientationChanged(orientation: Int) {
                    if (orientation == ORIENTATION_UNKNOWN) return
                    mAngle = orientation.toDouble()
                }
            }
        if (orientationEventListener.canDetectOrientation()) {
            orientationEventListener.enable()
        } else {
            orientationEventListener.disable() //注销
        }
        listener = sensorRotateListener
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
                geomagnetic = event.values
            }
            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                gravity = event.values
                handleNewSensorAverageValue()
            }
        }
    }

    private fun handleNewSensorAverageValue() {
        // r从这里返回
        SensorManager.getRotationMatrix(r, null, gravity, geomagnetic)
        //values从这里返回
        SensorManager.getOrientation(r, values)
        //提取数据
        lastDegreeZ = toDegrees(values[0].toDouble())
        lastDegreeX = toDegrees(values[1].toDouble())
        val currentDegreeY = toDegrees(values[2].toDouble())
        lastDegreeY = currentDegreeY
//        if (System.currentTimeMillis() - lastTime < 300) {
//            return
//        }
        lastTime = System.currentTimeMillis().toDouble()
        if (180 <= mAngle && mAngle <= 360) {
            mAngle = mAngle - 360
        }
        if (lastAngle != -1.0) {
            var isLeft = false
            lastAngle = mAngle
            isLeft = if (mAngle > 0) {
                false
            } else {
                true
            }
//            if (Math.abs(lastAngle - mAngle) < rotateSpace && Math.abs(lastDegreeY) > 30 && Math.abs(
//                    lastDegreeY
//                ) < 120 && (mAngle > 0 && mAngle > rotateSpace || mAngle < 0 && mAngle < -rotateSpace)
//            ) {
            Log.d(
                "sundu",
                "旋转 = " + (if (isLeft) "左" else "右") + " 角度 = " + mAngle + " x =  " + lastDegreeX + " y = " + lastDegreeY
            )
            if (listener != null) {
                listener!!.rotate(isLeft)
                listener = null
//                    mSensorManager!!.unregisterListener(this, magneticSensor)
//                    mSensorManager!!.unregisterListener(this, accelerometerSensor)
            }
//            }
        } else {
            lastAngle = mAngle
        }
    }

    fun cancel() {
        listener = null
        try {
            mSensorManager!!.unregisterListener(this, magneticSensor)
            mSensorManager!!.unregisterListener(this, accelerometerSensor)
        } catch (e: Exception) {
            Log.i("sensor", "sensor rotate error unregister")
        }
    }

    interface SensorRotateListener {
        fun rotate(left: Boolean)
    }
}
