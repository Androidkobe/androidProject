package com.example.demo.sundu.developer.sensorxyz

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.lang.Math.toDegrees
import java.util.*

class SenSorGyrDegreesHelper : SensorEventListener {

    //存储旋转矩阵
    private val r = FloatArray(9)

    //用来保存最终的结果
    private val values = FloatArray(3)

    //传感器控制类
    private var mSensorManager: SensorManager? = null

    //方向传感器
    private var sensor: Sensor? = null

    private var listener: SensorRotateListener? = null

    private var mViewModel: SensorViewModel? = null

    private var mContext: Context? = null

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun registerListener(
        context: Context,
        sensorRotateListener: SensorRotateListener?,
        viewModel: SensorViewModel
    ) {
        val systemService = context.getSystemService(Context.SENSOR_SERVICE)
        mContext = context
        mViewModel = viewModel
        if (systemService != null) {
            mSensorManager = systemService as SensorManager
            sensor = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
            mSensorManager!!.registerListener(
                this,
                sensor,
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
            handXiaomiCompass(event)
        }
    }

    fun handXiaomiCompass(event: SensorEvent?) {
        if (event != null && mSensorManager != null) {
            SensorManager.getRotationMatrixFromVector(
                r, event.values
            );
            //values从这里返回
            SensorManager.getOrientation(r, values)
            var x = toDegrees(values[1].toDouble())
            var y = toDegrees(values[2].toDouble())
            var z = toDegrees(values[0].toDouble())
            Log.d("sundu", "a = ${x} , b = ${y} ,z = ${z}")
            mViewModel?.sendData(x, y, z)
        }
    }

    interface SensorRotateListener {
        fun rotate(left: Boolean)
    }

}
