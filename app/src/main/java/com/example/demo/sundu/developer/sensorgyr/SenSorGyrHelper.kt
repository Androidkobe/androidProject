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

    //摇晃持续时间
    private var shakeContinuedTime = 3 * 1000

    //加速度阈值
    private var accelerationThreshold = 40

    //转动角度阈值
    private var rotateThreshold = 45

    //用来保存加速度传感器的值
    private var gravity = FloatArray(3)

    private val r = FloatArray(9)

    //用来保存地磁传感器的值
    private var geomagnetic = FloatArray(3)

    //用来保存最终的结果
    private val values = FloatArray(3)

    //传感器控制类
    private var mSensorManager: SensorManager? = null
    private var magneticSensor: Sensor? = null
    private var accelerometerSensor: Sensor? = null

    //上次角度
    private var lastDegreeX = 0.0
    private var lastDegreeY = 0.0
    private var lastDegreeZ = 0.0

    //上次加速度
    private var lastX = 0.0
    private var lastY = 0.0
    private var lastZ = 0.0

    //采样传感器间隔时间
    private var checkTime = 100
    private var startCheckShake = 0
    private var shakeIng = false

    private var lastTime = 0.0
    private var spaceGetData = true
    private var QUEUE_SIZE = 5
    private var shakeQueue = LinkedBlockingDeque<Boolean>(QUEUE_SIZE)

    private var isShake = false

    //晃动过程中 超过角度阈值
    private var moreThanDegree = false

    //晃动过程中 超过加速度度阈值
    private var moreThanAcceleration = false

    //持续时间计时器
    private var time = Timer()
    private var timerTask: TimerTask? = null

    private var listener: SensorRotateListener? = null

    /**
     * 设置摇动持续时间，加速度 与 旋转角度 阈值
     */
    fun setThreshold(shakeTime: Int, acceleration: Int, rotate: Int) {
        shakeContinuedTime = shakeTime
        accelerationThreshold = acceleration
        rotateThreshold = rotate
    }

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
        listener = sensorRotateListener
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
                geomagnetic = event.values
            }
            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                gravity = event.values
                handleNewSensorAverageValue2()
            }
        }
    }

    private fun handleNewSensorAverageValue2() {
        // r从这里返回
        SensorManager.getRotationMatrix(r, null, gravity, geomagnetic)
        //values从这里返回
        SensorManager.getOrientation(r, values)

        if (System.currentTimeMillis() - lastTime < checkTime) {
            if (spaceGetData) {
                //采样间隔时间内只需要获取一次数据，多次获取会导致数据区别很小
                lastX = gravity[0].toDouble()
                lastY = gravity[1].toDouble()
                lastZ = gravity[2].toDouble()

                lastDegreeX = toDegrees(values[1].toDouble())
                lastDegreeY = toDegrees(values[2].toDouble())
                lastDegreeZ = toDegrees(values[0].toDouble())
                spaceGetData = false
            }
            return
        }
        spaceGetData = true

        lastTime = System.currentTimeMillis().toDouble()

        //当前加速度
        var currentX = gravity[0]
        var currentY = gravity[1]
        var currentZ = gravity[2]

        //当前角度
        var currentDegreeX = toDegrees(values[1].toDouble())
        var currentDegreeY = toDegrees(values[2].toDouble())
        var currentDegreeZ = toDegrees(values[0].toDouble())

        //加速度
        var acceleration =
            sqrt((currentX * currentX + currentY * currentY + currentZ * currentZ).toDouble())


//        var xCase = Math.abs(currentX) >= 2 && lastX*currentX <= 0
//        var yCase = Math.abs(currentY) >= 2 && lastY*currentY <= 0
//        var gravityDegreesOne = xCase || yCase

        //当前旋转角度
        var degreesX = abs(currentDegreeX - lastDegreeX)
        var degreesY = abs(currentDegreeY - lastDegreeY)

        //用于判断是否持续滑动
        var gravityDegreesTwo = degreesX >= 10 || degreesY >= 10

        //传入队列，如果最近几次有一半以上是晃动纠正当前为晃动中
        if (shakeQueue.size > 0 && shakeQueue.size >= QUEUE_SIZE) {
            shakeQueue.removeFirst()
            shakeQueue.add(gravityDegreesTwo)
        } else {
            shakeQueue.add(gravityDegreesTwo)
        }

        if (!gravityDegreesTwo) {
            var i = 0
            shakeQueue.forEach {
                if (it) i++
            }
            if (i > QUEUE_SIZE / 2) {
                gravityDegreesTwo = true
            }
        }

        //连续5次为晃动中 认为手机开始晃动
        if (gravityDegreesTwo) {
            startCheckShake++
            if (startCheckShake == QUEUE_SIZE) {
                startShake()
            }
        } else {
            startCheckShake = 0
            stopShake()
        }

        //加速度超过阈值
        //晃动过程中 只要有一次超过阈值 就可以认为是触发了晃动条件
        var case1 = (acceleration >= accelerationThreshold || moreThanAcceleration)
        //旋转角度超过阈值
        //晃动过程中 只要有一次超过阈值 就可以认为是触发了晃动条件
        var case2 = ((degreesX >= rotateThreshold || degreesY >= rotateThreshold) || moreThanDegree)

        if (shakeIng) {
            //晃动 = 持续晃动 + 超过角度阈值 || 超过加速度阈值
            isShake = gravityDegreesTwo && (case1 || case2)
        }

//        Log.d(
//            "sundu",
//            "晃动 =   : $gravityDegreesTwo  $degreesX  $degreesY  $case1  $case2")
    }

    private fun startShake() {
        Log.d("sundu", "开始晃动 ==================================================")
        shakeIng = true
        timerTask = getTimerTask()
        timerTask?.let {
            time.schedule(it, (shakeContinuedTime - (QUEUE_SIZE * checkTime)).toLong())
        }
    }

    private fun stopShake() {
        Log.d("sundu", "*********************停止晃动*******************************")
        moreThanDegree = false
        moreThanAcceleration = false
        shakeIng = false
        timerTask?.cancel()
    }

    private fun getTimerTask(): TimerTask {
        return object : TimerTask() {
            override fun run() {
                Log.d("sundu", "============== 晃动了 =========================")
            }
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
