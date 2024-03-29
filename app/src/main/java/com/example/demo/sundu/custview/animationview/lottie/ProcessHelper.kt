package com.example.demo.sundu.custview.animationview.lottie

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import java.lang.Math.toDegrees

class ProcessHelper : SensorEventListener {

    var TAG = "SenSorGyrDegreesHelper"

    companion object {
        const val ORIENTATION_X = 1
        const val ORIENTATION_Y = 2
        const val ORIENTATION_Z = 3

        //x 转轴
        const val TWIST_AXIS_X = 1

        //y 转轴
        const val TWIST_AXIS_Y = 2

        //z 转轴
        const val TWIST_AXIS_Z = 3
    }

    private var delayTime = 500

    private var startMonitorTime = -1L

    private var listener: ProcessListener? = null

    private var mContext: Context? = null

    //传感器控制类
    private var mSensorManager: SensorManager? = null

    //方向传感器
    private var sensor: Sensor? = null

    //存储旋转矩阵
    private val rotationMatrix = FloatArray(9)

    //用来保存最终的结果
    private val values = FloatArray(3)

    private var firstXOrientation = 0.0

    private var firstYOrientation = 0.0

    private var firstZOrientation = 0.0

    private var LOAD_FIRST_ORIENTATION = false


    private var mOrientationType = TWIST_AXIS_Z


    fun registerListener(
        context: Context,
        processListener: ProcessListener?,
        orientationType: Int
    ) {
        mContext = context
        val systemService = context.getSystemService(Context.SENSOR_SERVICE)
        systemService?.let {
            mSensorManager = systemService as SensorManager
            mSensorManager?.let {
                sensor = it.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
                it.registerListener(
                    this,
                    sensor,
                    SensorManager.SENSOR_DELAY_FASTEST
                )
            }
        }
        listener = processListener
        mOrientationType = orientationType
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}


    override fun onSensorChanged(event: SensorEvent?) {
        if (startMonitorTime == -1L) {
            startMonitorTime = System.currentTimeMillis() + delayTime
        }
        if (System.currentTimeMillis() > startMonitorTime) {
            if (event != null) {
                if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
                    event?.values?.let {
                        if (it[0] != 0.0f && it[1] != 0.0f && it[2] != 0.0f) {
                            handOrientation(event)
                        }
                    }
                }
            }
        }
    }

    private fun handOrientation(event: SensorEvent?) {
        if (event != null && mSensorManager != null) {
            SensorManager.getRotationMatrixFromVector(
                rotationMatrix, event.values
            )
            //values从这里返回
            SensorManager.getOrientation(rotationMatrix, values)

            if (!LOAD_FIRST_ORIENTATION) {
                firstXOrientation = getXDegrees(values[1].toDouble())
                firstYOrientation = getYZDegrees(values[2].toDouble())
                firstZOrientation = getYZDegrees(values[0].toDouble())
                LOAD_FIRST_ORIENTATION = true
                return
            }
            var currentXOrientation = getXDegrees(values[1].toDouble())
            var currentYOrientation = getYZDegrees(values[2].toDouble())
            var currentZOrientation = getYZDegrees(values[0].toDouble())
            Log.e("xxx", "${toDegrees(values[1].toDouble()).toInt()}")
            when (mOrientationType) {
                ORIENTATION_X -> {
                    listener?.process(Math.abs(firstXOrientation - currentXOrientation))
                }
                ORIENTATION_Y -> {
                    listener?.process(getYZRotateAngle(firstYOrientation, currentYOrientation))
                }
                ORIENTATION_Z -> {
                    listener?.process(getYZRotateAngle(firstZOrientation, currentZOrientation))
                }
            }
        }
    }


    private fun getXDegrees(angradx: Double): Double {
        var anglex = toDegrees(angradx)
        return (90 + anglex)
    }

    private fun getYZDegrees(angradx: Double): Double {
        var anglex = toDegrees(angradx)
        return anglex
    }

    private fun getYZRotateAngle(start: Double, end: Double): Double {
        if (start >= 0 && end >= 0) {
            return Math.abs(end - start)
        } else if (start <= 0 && end <= 0) {
            return Math.abs(end - start)
        } else {
            if (Math.abs(end - start) > 180) {
                return (180 - Math.abs(start)) + (180 - Math.abs(end))
            } else {
                return Math.abs(end - start)
            }
        }
    }


    interface ProcessListener {
        fun process(angle: Double)
    }

}
