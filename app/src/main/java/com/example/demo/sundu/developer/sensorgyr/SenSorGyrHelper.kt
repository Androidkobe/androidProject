package com.example.demo.sundu.developer.sensorgyr

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import java.lang.Math.toDegrees
import java.util.concurrent.LinkedBlockingDeque
import kotlin.math.abs
import kotlin.math.sqrt


class SenSorGyrHelper : SensorEventListener {
    //存储旋转矩阵
    private val r = FloatArray(9)

    //用来保存最终的结果
    private val values = FloatArray(3)

    private var ACC_QUEUE_SIZE = 5

    private var shakeQueue = LinkedBlockingDeque<Int>(ACC_QUEUE_SIZE)

    private var lastXOrientation = 0

    private var lastYOrientation = 0

    private var lastZOrientation = 0

    private var ORIENTATION_QUEUE_SIZE = 3

    private var checkTime = 300

    private var lastCheckTime = 0L

    private var orientationQueue = LinkedBlockingDeque<Boolean>(ORIENTATION_QUEUE_SIZE)

    //传感器控制类
    private var mSensorManager: SensorManager? = null

    //方向传感器
    private var sensor: Sensor? = null

    private var accelerometerSensor: Sensor? = null

    private var listener: SensorRotateListener? = null

    private var mContext: Context? = null


    fun registerListener(
        context: Context,
        sensorRotateListener: SensorRotateListener?,
    ) {
        val systemService = context.getSystemService(Context.SENSOR_SERVICE)
        mContext = context
        if (systemService != null) {
            mSensorManager = systemService as SensorManager
            accelerometerSensor = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            sensor = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
            mSensorManager!!.registerListener(
                this,
                sensor,
                SensorManager.SENSOR_DELAY_FASTEST
            )
            mSensorManager!!.registerListener(
                this,
                accelerometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
        listener = sensorRotateListener
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("sundu", "accuracy $accuracy")
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
                handXiaomiCompass(event)
            }
            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
//                handAccelerometer(event)
            }
        }
    }

    fun handAccelerometer(event: SensorEvent?) {
        event?.apply {
            var result = values
            var currentX = result[0]
            var currentY = result[1]
            var currentZ = result[2]
            //加速度
            var acceleration =
                sqrt((currentX * currentX + currentY * currentY + currentZ * currentZ).toDouble()) - 9.8
            if (shakeQueue.size < ACC_QUEUE_SIZE) {
                shakeQueue.add(acceleration.toInt())
            } else {
                if (isShakeAcc(shakeQueue)) {
                    Log.e("sundu", "晃动中")
                } else {
                    Log.d("sundu", "停止晃动")
                }
                shakeQueue.clear()
            }
        }
    }

    //是否在重力方向上 晃动
    private fun isShakeAcc(queue: LinkedBlockingDeque<Int>): Boolean {
        var min = Int.MAX_VALUE
        var max = Int.MIN_VALUE
        queue.forEach {
            if (it < min) {
                min = it
            }
            if (it > max) {
                max = it
            }
        }
        Log.e("sundu", "加速度最大值 ${max + 9.8}")
        if ((min != Int.MIN_VALUE && max != Int.MIN_VALUE) && max - min > 2) {
            return true
        }
        return false
    }


    fun handXiaomiCompass(event: SensorEvent?) {
        if (event != null && mSensorManager != null) {

            if (System.currentTimeMillis() - lastCheckTime < checkTime) {
                return
            }
            lastCheckTime = System.currentTimeMillis()

            SensorManager.getRotationMatrixFromVector(
                r, event.values
            )
            //values从这里返回
            SensorManager.getOrientation(r, values)
            var currentXOrientation =
                getxXOrientation(values[1].toDouble(), values[0].toDouble()).toInt()
            var currentYOrientation = getyOrientation(values[2].toDouble()).toInt()
            var currentZOrientation = getzOrientation(values[0].toDouble()).toInt()

            if ((lastXOrientation == 0 || lastYOrientation == 0 || lastZOrientation == 0)) {
                lastXOrientation = currentXOrientation
                lastYOrientation = currentYOrientation
                lastZOrientation = currentZOrientation
                return
            }

            var xTwist = false
            var yTwist = false
            var zTwist = false

            if (abs(lastXOrientation - currentXOrientation) < 180) {
                xTwist = isTwist(currentXOrientation, lastXOrientation)
            }

            if (abs(lastYOrientation - currentYOrientation) < 180) {
                yTwist = isTwist(currentYOrientation, lastYOrientation)
            }

            if (abs(lastZOrientation - currentZOrientation) < 180) {
                zTwist = isTwist(currentZOrientation, lastZOrientation)
            }

            lastXOrientation = currentXOrientation
            lastYOrientation = currentYOrientation
            lastZOrientation = currentZOrientation

            var twist = zTwist || xTwist || yTwist

            if (orientationQueue.size < ORIENTATION_QUEUE_SIZE) {
                orientationQueue.add(twist)
            } else {
                if (twistShake(orientationQueue)) {
                    Log.e("sundu", "晃动中")
                } else {
                    Log.d("sundu", "停止晃动")
                }
                orientationQueue.clear()
            }
        }
    }

    fun isTwist(last: Int, current: Int): Boolean {
        var s = abs(current - last)
        Log.d("sundu", "$last $current $s")
        if (abs(current - last) > 3) {
            return true
        }
        return false
    }

    fun twistShake(queue: LinkedBlockingDeque<Boolean>): Boolean {
        var i = 0
        queue.forEach {
            if (it) {
                i++
            }
        }
        return i >= 2
    }


    fun getxXOrientation(angradx: Double, angradz: Double): Double {
        var anglex = toDegrees(angradx)
        var anglez = toDegrees(angradz)
        if (anglex < 0 && anglez > 0) {
            return Math.abs(anglex)
        } else if (anglex < 0 && anglez < 0) {
            return 180 + anglex
        } else if (anglex > 0 && anglez < 0) {
            return 180 + anglex
        } else {
            return 360 - anglex
        }
    }

    fun getyOrientation(angrad: Double): Double {
        var angle = toDegrees(angrad)
        if (angle > 0) {
            return angle
        }
        return angle + 360
    }

    fun getzOrientation(angrad: Double): Double {
        var angle = toDegrees(angrad)
        if (angle > 0) {
            return angle
        }
        return angle + 360
    }

    interface SensorRotateListener {
        fun rotate(left: Boolean)
    }
}
