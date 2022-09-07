package com.example.demo.sundu.developer.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import android.view.OrientationEventListener

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */
class SenSorHelper(var data: SenSorData, var resultData: SenSorData) : SensorEventListener {

    private var mOnShakeListener: ShakeListener? = null


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

    private var preRotate = -1
    private var preMaxX = 0f
    private var preMaxY = 0f
    private var preMaxZ = 0f

    private var listener: ShakeListener? = null

    fun setOnShakeListener(context: Context, sensorRotateListener: ShakeListener?) {
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
                SensorManager.SENSOR_DELAY_GAME
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
                saveMaxLine(gravity)
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
        lastDegreeZ = Math.toDegrees(values[0].toDouble())
        lastDegreeX = Math.toDegrees(values[1].toDouble())
        val currentDegreeY = Math.toDegrees(values[2].toDouble())
        lastDegreeY = currentDegreeY
        if (System.currentTimeMillis() - lastTime < 300) {
            return
        }
        lastTime = System.currentTimeMillis().toDouble()
        if (180 <= mAngle && mAngle <= 360) {
            mAngle = mAngle - 360
        }
        if (lastAngle != -1.0) {
            var isLeft = false
            lastAngle = mAngle
            isLeft = mAngle <= 0

            if (preRotate == -1) {
                preRotate = if (isLeft) 1 else 2
            } else {
                if (preRotate != (if (isLeft) 1 else 2)) {
                    isMaxBaseLine()
                    preRotate = (if (isLeft) 1 else 2)
                }
            }
        } else {
            lastAngle = mAngle
        }
    }

    fun saveMaxLine(floatData: FloatArray) {
        var x = floatData[0]
        var y = floatData[1]
        var z = floatData[2]
        x?.let {
            preMaxX = Math.max(Math.abs(x), preMaxX)
        }
    }

    var count = 0
    fun isMaxBaseLine() {
        Log.e("sundu", "$preMaxX")
        if (Math.abs(preMaxX) > 14) {
            preMaxX = 0f
            count++
            if (count >= 3) {
                if (listener != null) {
                    listener = null
                    mSensorManager!!.unregisterListener(this, magneticSensor)
                    mSensorManager!!.unregisterListener(this, accelerometerSensor)
                }
                Log.e("sundu", "晃动")
                count = 0
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

    var isWeightlessnessed = false

//    override fun onSensorChanged(event: SensorEvent) {
//        if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
//            geomagnetic = event.values
//        }
//        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
//            gravity = event.values
//            getOritation()
//        }
//        val type = event.sensor.type
//        if (type == Sensor.TYPE_ACCELEROMETER) {
//            if (data.x?.isNotEmpty() == true
//                || data.y?.isNotEmpty() == true
//                || data.z?.isNotEmpty() == true
//            ) {
//
//                var alpha = 0.8f
//                var gravity =arrayOfNulls<Float>(3)
//                gravity[0] = alpha * (gravity[0]?:0f) + (1 - alpha) * event.values[0]
//                gravity[1] = alpha * (gravity[1]?:0f) + (1 - alpha) * event.values[1]
//                gravity[2] = alpha *(gravity[2]?:0f) + (1 - alpha) * event.values[2]
//                var linear_acceleration = arrayOfNulls<Float>(3)
//                linear_acceleration[0] = event.values[0] - (gravity[0]?:0f)
//                linear_acceleration[1] = event.values[1] - (gravity[1]?:0f)
//                linear_acceleration[2] = event.values[2] -  (gravity[2]?:0f)
//                var lx = linear_acceleration[0]?:0f
//                var ly = linear_acceleration[1]?:0f
//                var lz = linear_acceleration[2]?:0f
//                var speed = Math.round(Math.sqrt(lx*lx + ly*ly.toDouble()+lz*lz)-8)
//                Log.e("sundu", "${lx}  ${speed}")
//                if(lx > 0 ){
//                    if(!isWeightlessnessed && speed >10){
//                        isWeightlessnessed = true
//                        Log.e("sundu", "震动")
//                    }
//                }else{
//                    isWeightlessnessed = false
//                }
//            }

//                currentUpdateTime = System.currentTimeMillis()
//                var timeInterval = currentUpdateTime - lastUpdateTime;
//                if (timeInterval < UPTATE_INTERVAL_TIME)
//                    return
//                lastUpdateTime = currentUpdateTime;
//                val values = event.values
//                val x = values[0]
//                val y = values[1]
//                val z = values[2]
//                // 获得x,y,z的变化值
//                var deltaX = x - lastX;
//                var deltaY = y - lastY;
//                var deltaZ = z - lastZ;
//
//                // 将现在的坐标变成last坐标
//                lastX = x;
//                lastY = y;
//                lastZ = z;
//                //sqrt 返回最近的双近似的平方根
//                var speed = Math.sqrt(deltaX.toDouble() * deltaX + deltaY * deltaY) / (timeInterval * 10000);
//                // 达到速度阀值，发出提示
//                Log.e("sundu",speed.toString())
//                if(speed > 17)
//                {
//                    if (mOnShakeListener != null) {
//                        Log.e("sundu", "开始震动")
//                        mOnShakeListener!!.onShake()
//                    }
//                }
//                var alpha = 0.8f
//                var gravity =arrayOfNulls<Float>(3)
//                gravity[0] = alpha * (gravity[0]?:0f) + (1 - alpha) * event.values[0]
//                gravity[1] = alpha * (gravity[1]?:0f) + (1 - alpha) * event.values[1]
//                gravity[2] = alpha *(gravity[2]?:0f) + (1 - alpha) * event.values[2]
//                var linear_acceleration = arrayOfNulls<Float>(3)
//                linear_acceleration[0] = event.values[0] - (gravity[0]?:0f)
//                linear_acceleration[1] = event.values[1] - (gravity[1]?:0f)
//                linear_acceleration[2] = event.values[2] -  (gravity[2]?:0f)
//                var lx = linear_acceleration[0]?:0f
//                Log.e("sundu", "$lx")
//                if (isShare(Math.abs(lx))) {
//                    preShareTime = System.currentTimeMillis()
////                    resultData.x = x.toString()
////                    resultData.y = y.toString()
////                    resultData.z = z.toString()
//                    Log.e("sundu", "震动")
//
////                    if (mSensorManager != null) {
////                        mSensorManager!!.unregisterListener(this)
////                    }
//                }
//            }
//        }
//    }

    /**
     * 获取手机旋转角度
     */
    fun getOritation() {
        // r从这里返回
        SensorManager.getRotationMatrix(r, null, gravity, geomagnetic)
        //values从这里返回
        SensorManager.getOrientation(r, values)
        //提取数据
        val degreeZ = Math.toDegrees(values!![0].toDouble())
        val degreeX = Math.toDegrees(values!![1].toDouble())
        val degreeY = Math.toDegrees(values!![2].toDouble())

        Log.e("sundu", " x = $degreeX  y = $degreeY  z = $degreeZ")
    }

    var shareBaseLine = 17
    var shareStart = false
    var shareTraction = -1
    var queueLine = 10
    var xQueue: LimitQueue<Float> = LimitQueue(queueLine)
    var preShareTime = System.currentTimeMillis()

    fun isShare(x: Float): Boolean {
        xQueue.offer(x)
        when (shareTraction) {
            1 -> {
                if (shareStart && x < shareBaseLine) {
                    shareStart = false
                    shareTraction = -1
                    xQueue.clear()
                    return true
                }
            }
            -1 -> {
                if (!shareStart) {
                    if (x > shareBaseLine && checkShare(x)) {
                        Log.e("sundu", "x开始晃动 " + Thread.currentThread().name)
                        shareStart = true
                        shareTraction = 1
                    }
                }
            }
        }
        return false
    }

    fun checkShare(x: Float): Boolean {
        return xQueue.size() > 1 && isSame(x, xQueue)
    }

    fun isSame(v: Float, limitQueue: LimitQueue<Float>): Boolean {
        var count = 0
        for (i in 1..limitQueue.size() - 1) {
            var pre = limitQueue.get(i - 1)
            var cur = limitQueue.get(i)
//            Log.e("sundu","${limitQueue.get(i)}  ${limitQueue.get(i-1)}")
            if (cur > pre) {
                count++
            }

        }
        return (count > 8)
    }


    interface ShakeListener {
        fun onShake()
    }
}
