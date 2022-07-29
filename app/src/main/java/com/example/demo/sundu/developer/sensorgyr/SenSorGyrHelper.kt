package com.example.demo.sundu.developer.sensorgyr

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import java.lang.Math.toDegrees


class SenSorGyrHelper : SensorEventListener {

    private var mSensorManager: SensorManager? = null

    private var magneticSensor: Sensor? = null

    private var accelerometerSensor: Sensor? = null

    var gravity :FloatArray? = null
    var r :FloatArray? = null
    var geomagnetic :FloatArray? = null
    var values :FloatArray? = null
    fun start(context: Context): Boolean {
        /**
         * 初始化传感器
         * */
        mSensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //获取Sensor
        //获取Sensor
        magneticSensor = mSensorManager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        accelerometerSensor = mSensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        //初始化数组
        gravity = FloatArray(3) //用来保存加速度传感器的值
        r = FloatArray(9)
        geomagnetic = FloatArray(3) //用来保存地磁传感器的值
        values = FloatArray(3) //用来保存最终的结果
        mSensorManager?.registerListener(this,magneticSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager?.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        return false
    }
    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagnetic = event.values
        }
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            gravity = event.values
            getOritation()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    /**
     * 获取手机旋转角度
     */
    fun getOritation() {
        // r从这里返回
        SensorManager.getRotationMatrix(r, null, gravity, geomagnetic)
        //values从这里返回
        SensorManager.getOrientation(r, values)
        //提取数据
        val degreeZ = toDegrees(values!![0].toDouble())
        val degreeX = toDegrees(values!![1].toDouble())
        val degreeY = toDegrees(values!![2].toDouble())

        Log.e("sundu"," x = $degreeX  y = $degreeY  z = $degreeZ")
    }
}
