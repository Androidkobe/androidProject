package com.example.demo.sundu.developer.sensorgyr

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import java.lang.Math.toDegrees
import java.util.*
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

    private var checkTime = 100

    private var lastCheckTime = 0L

    private var ORI_QUEUE_SIZE = 10

    private var orientationCheckQueue = LinkedBlockingDeque<Boolean>()
    private var orientationQueue = LinkedBlockingDeque<Boolean>(ORI_QUEUE_SIZE)

    private var isOrientationStartShake = false

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

//        var timer = Timer()
//        timer.schedule(object :TimerTask(){
//            override fun run() {
//                Log.e("sundu",orientationQueue.toString())
//               removeListener()
//            }
//
//        },3*1000)
    }


    fun removeListener() {
        mSensorManager?.unregisterListener(this, sensor)
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

            if (lastXOrientation == 0 && lastYOrientation == 0 && lastZOrientation == 0) {
                lastXOrientation = currentXOrientation
                lastYOrientation = currentYOrientation
                lastZOrientation = currentZOrientation
                return
            }
            var xSpace = abs(currentXOrientation - lastXOrientation)
            var ySpace = abs(currentYOrientation - lastYOrientation)
            var zSpace = abs(currentZOrientation - lastZOrientation)

            lastXOrientation = currentXOrientation
            lastYOrientation = currentYOrientation
            lastZOrientation = currentZOrientation

            var isShake = zSpace >= 4 || xSpace >= 4 || ySpace >= 4

            if (orientationQueue.size < ORI_QUEUE_SIZE) {
                orientationQueue.add(isShake)
            } else {
                orientationQueue.removeFirst()
                orientationQueue.add(isShake)
                var shake = isShakeOrientation(orientationQueue)
                if (shake && !isOrientationStartShake) {
                    Log.e("sundu", "开始晃动")
                    isOrientationStartShake = true
                    var timer = Timer()
                    timer.schedule(object : TimerTask() {
                        override fun run() {
                            isOrientationStartShake = false
                            removeListener()
                            var isShake = checkTimeShake(orientationCheckQueue)
                            orientationCheckQueue.clear()
                            when (isShake) {
                                true -> {
                                    Log.e("sundu", "晃动了")
                                }
                                false -> {
                                    Log.e("sundu", "没有晃动")
                                }
                            }
                        }

                    }, 3 * 1000)
                }
                if (isOrientationStartShake) {
                    orientationCheckQueue.add(shake)
                }
            }
        }
    }

    private fun checkTimeShake(orientationCheckQueue: LinkedBlockingDeque<Boolean>): Boolean {
        var count = 0.0f
        var size = orientationCheckQueue.size
        var throld = size * 0.8f
        orientationCheckQueue.forEach {
            if (it) count += 1
        }
        return count >= throld
    }

    private fun isShakeOrientation(orientationQueue: LinkedBlockingDeque<Boolean>): Boolean {
        var count = 0.0f
        var size = orientationQueue.size
        var throld = size * 0.5f
        orientationQueue.forEach {
            if (it) count += 1
        }
        return count >= throld
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
