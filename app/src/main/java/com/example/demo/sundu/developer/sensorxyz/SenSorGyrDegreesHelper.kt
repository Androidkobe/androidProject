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

    private var firstXOrientation = 0.0

    private var firstYOrientation = 0.0

    private var firstZOrientation = 0.0

    private var LOAD_FIRST_ORIENTATION = false

    private var ANGLE = 70

    private var intervalX: Array<Int>? = null
    private var intervalY: Array<Int>? = null
    private var intervalZ: Array<Int>? = null

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
                SensorManager.SENSOR_DELAY_UI
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
            )
            //values从这里返回
            SensorManager.getOrientation(r, values)

            if (!LOAD_FIRST_ORIENTATION) {
                firstXOrientation = getxXOrientation(values[1].toDouble(), values[0].toDouble())
                firstYOrientation = getyOrientation(values[2].toDouble())
                firstZOrientation = getzOrientation(values[0].toDouble())
                intervalX = setInterval(firstXOrientation.toInt(), angle = ANGLE)
                intervalY = setInterval(firstYOrientation.toInt(), angle = ANGLE)
                intervalZ = setInterval(firstZOrientation.toInt(), angle = ANGLE)
                LOAD_FIRST_ORIENTATION = true
                return
            }
            var currentXOrientation =
                getxXOrientation(values[1].toDouble(), values[0].toDouble()).toInt()
            var currentYOrientation = getyOrientation(values[2].toDouble()).toInt()
            var currentZOrientation = getzOrientation(values[0].toDouble()).toInt()

//            var xExt = exceedSetThreshold(currentXOrientation, intervalX)
            // var yExt = exceedSetThreshold(currentZOrientation,intervalY)
            var zExt = exceedSetThreshold(
                currentZOrientation,
                intervalZ,
                getReverseCheck(firstZOrientation.toInt())
            )

//            intervalX?.let {
//                Log.e("sundu", "X $currentXOrientation [${it[0]}-${it[1]}] $xExt")
//            }
//            intervalY?.let {
//                Log.e("sundu","Y $currentYOrientation [${it[0]}-${it[1]}] $yExt")
//            }
//
            intervalZ?.let {
                Log.e("sundu", "Y $currentZOrientation [${it[0]}-${it[1]}] $zExt")
            }
//            if (xExt) {
//                Log.d("sundu", "x 超过$ANGLE 度")
//            }

//            if (yExt){
//                Log.d("sundu", "y 超过$ANGLE 度")
//            }
//
//            if (zExt){
//                Log.d("sundu", "z 超过$ANGLE 度")
//            }

//            Log.d("sundu", "a = ${currentXOrientation} , b = ${currentYOrientation} ,z = ${currentZOrientation}")
            mViewModel?.sendData(currentXOrientation, currentYOrientation, currentZOrientation)
        }
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

    fun setInterval(firstData: Int, angle: Int): Array<Int> {
        var threshold = angle

        if (threshold >= 180) {
            threshold = 180 - 1
        }
        var left = 0
        var right = 360

        //正常
        if (firstData - threshold >= 0 && firstData + threshold <= 360) {
            left = firstData - threshold
            right = firstData + threshold
        }

        //临界 eg 45
        if (firstData - threshold < 0 && firstData + threshold <= 360) {
            left = firstData + threshold
            right = firstData - threshold + 360
        }

        //临界 eg 355
        if (firstData - threshold >= 0 && firstData + threshold >= 360) {
            left = firstData + threshold - 360
            right = firstData - threshold
        }


        return arrayOf(left, right)
    }


    private fun getReverseCheck(orientation: Int): Boolean {
        if (orientation > 90 && orientation < 270) {
            return true
        }
        return false
    }

    private fun exceedSetThreshold(
        orientation: Int,
        interval: Array<Int>?,
        isReverseCheck: Boolean
    ): Boolean {
        interval?.let {
            if (isReverseCheck) {
                if ((orientation <= interval[0] && orientation >= 0) || (orientation >= interval[1] && orientation <= 360)) {
                    return true
                }
            } else {
                if (orientation >= interval[0] && orientation <= interval[1]) {
                    return true
                }
            }
            return false
        } ?: let {
            return false
        }
    }

    interface SensorRotateListener {
        fun rotate(left: Boolean)
    }

}
