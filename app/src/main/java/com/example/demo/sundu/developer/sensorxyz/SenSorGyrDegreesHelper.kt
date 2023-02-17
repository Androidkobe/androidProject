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

    var TAG = "SenSorGyrDegreesHelper"

    //存储旋转矩阵
    private val rotationMatrix = FloatArray(9)

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

    private var angleThreshold = 35

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
                rotationMatrix, event.values
            )
            //values从这里返回
            SensorManager.getOrientation(rotationMatrix, values)

            if (!LOAD_FIRST_ORIENTATION) {
                firstXOrientation = getxOrientation(values[1].toDouble(), values[0].toDouble())
                firstYOrientation = getyOrientation(values[2].toDouble())
                firstZOrientation = getzOrientation(values[0].toDouble())
                intervalX = setInterval(firstXOrientation.toInt(), angle = angleThreshold)
                intervalY = setInterval(firstYOrientation.toInt(), angle = angleThreshold)
                intervalZ = setInterval(firstZOrientation.toInt(), angle = angleThreshold)
                LOAD_FIRST_ORIENTATION = true
                return
            }
            var currentXOrientation =
                getxOrientation(values[1].toDouble(), values[0].toDouble()).toInt()
            var currentYOrientation = getyOrientation(values[2].toDouble()).toInt()
            var currentZOrientation = getzOrientation(values[0].toDouble()).toInt()

            var xExt = exceedSetThreshold(currentXOrientation, intervalX)
            var yExt = exceedSetThreshold(currentYOrientation, intervalY)
            var zExt = exceedSetThreshold(currentZOrientation, intervalZ)
//
//            var detialX = ""
//            intervalX?.let {
//                detialX = if (isXReverseCheck) "[0-${it[0]}] | [${it[1]}-360]" else "[${it[0]} - ${it[1]}]"
//            }
//            Log.d(TAG,"orientation : X $xExt  first: $firstXOrientation curr :$currentXOrientation  check : $detialX")

//            var detialY = ""
//            intervalY?.let {
//                detialY = if (isYReverseCheck) "[0-${it[0]}] | [${it[1]}-360]" else "[${it[0]} - ${it[1]}]"
//            }
//            Log.d(TAG,"orientation : Y $yExt   first: $firstYOrientation curr : $currentYOrientation  check : $detialY")
//
            var detialZ = ""
            intervalZ?.let {
                detialZ = "[0-${it[0]}] | [${it[1]}-360]"
            }
            Log.d(
                TAG,
                "orientation : Z $zExt   first: $firstZOrientation curr : $currentZOrientation  check : $detialZ"
            )
//            if (xExt || zExt || yExt){
//                var o = if (xExt) 1 else if(yExt) 2 else 3
//                notifyShake(SHAKE_TYPE_ORI,o)
//            }
        }
    }


    fun getxOrientation(angradx: Double, angradz: Double): Double {
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
        var type = 0 //计算方式

        //正常
        if (firstData - threshold >= 0 && firstData + threshold <= 360) {
            left = firstData - threshold
            right = firstData + threshold
            type = 0
        }

        //临界 eg 45
        if (firstData - threshold <= 0 && firstData + threshold <= 360) {
            left = firstData + threshold
            right = firstData - threshold + 360
            type = 1
        }

        //临界 eg 355
        if (firstData - threshold >= 0 && firstData + threshold >= 360) {
            left = firstData + threshold - 360
            right = firstData - threshold
            type = 1
        }

        return arrayOf(left, right, type)
    }

    private fun exceedSetThreshold(
        orientation: Int,
        interval: Array<Int>?,
    ): Boolean {
        interval?.let {
            if (interval[2] == 0) {
                if ((orientation >= 0 && orientation <= interval[0]) || (orientation >= interval[1] && orientation <= 360)) {
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
