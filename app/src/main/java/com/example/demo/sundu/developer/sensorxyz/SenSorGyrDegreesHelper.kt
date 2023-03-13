package com.example.demo.sundu.developer.sensorxyz

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import java.lang.Math.toDegrees

class SenSorGyrDegreesHelper : SensorEventListener {

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

        //xyz 转轴
        const val TWIST_AXIS_XYZ = 4
    }

    private var delayTime = 500
    private var startMonitorTime = -1L

    private var listener: SenSorTwistListener? = null

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

    private var angleThreshold = 35

    private var mOrientationType = TWIST_AXIS_Z

    private var notifyTwist = false

    private var intervalX: Array<Int>? = null
    private var intervalY: Array<Int>? = null
    private var intervalZ: Array<Int>? = null

    fun registerListener(
        context: Context,
        sensorRotateListener: SenSorTwistListener?,
        orientationType: Int,
        angleValue: Int
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
        listener = sensorRotateListener
        angleThreshold = angleValue
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
                firstXOrientation = getxOrientation(values[1].toDouble())
                firstYOrientation = getyOrientation(values[2].toDouble())
                firstZOrientation = getzOrientation(values[0].toDouble())
                intervalX = setIntervalX(firstXOrientation.toInt(), angle = angleThreshold)
                intervalY = setInterval(firstYOrientation.toInt(), angle = angleThreshold)
                intervalZ = setInterval(firstZOrientation.toInt(), angle = angleThreshold)
                LOAD_FIRST_ORIENTATION = true
                return
            }
            var currentXOrientation = getxOrientation(values[1].toDouble()).toInt()
            var currentYOrientation = getyOrientation(values[2].toDouble()).toInt()
            var currentZOrientation = getzOrientation(values[0].toDouble()).toInt()

            var xExt = exceedSetThreshold(currentXOrientation, intervalX)
            var yExt = exceedSetThreshold(currentYOrientation, intervalY)
            var zExt = exceedSetThreshold(currentZOrientation, intervalZ)

            var detialX = ""
            intervalX?.let {
                detialX =
                    if (it[2] == 0) "[0-${it[0]}] | [${it[1]}-180]" else "[${it[0]} - ${it[1]}]"
            }

            var detialY = ""
            intervalY?.let {
                detialY =
                    if (it[2] == 0) "[0-${it[0]}] | [${it[1]}-360]" else "[${it[0]} - ${it[1]}]"
            }

            var detialZ = ""
            intervalZ?.let {
                detialZ =
                    if (it[2] == 0) "[0-${it[0]}] | [${it[1]}-360]" else "[${it[0]} - ${it[1]}]"
            }

            when (mOrientationType) {
                TWIST_AXIS_X -> {
                    if (xExt) {
                        Log.e(
                            TAG,
                            "orientation : X $xExt  first: $firstXOrientation  curr :$currentXOrientation  check : $detialX"
                        )
                        notifyTwist(TWIST_AXIS_X, ORIENTATION_X)
                    }
                }
                TWIST_AXIS_Y -> {
                    if (yExt) {
                        Log.e(
                            TAG,
                            "orientation : Y $yExt  first: $firstYOrientation  curr :$currentYOrientation  check : $detialY"
                        )
                        notifyTwist(TWIST_AXIS_Y, ORIENTATION_Y)
                    }
                }
                TWIST_AXIS_Z -> {
                    if (zExt) {
                        Log.e(
                            TAG,
                            "orientation : Z $zExt  first: $firstZOrientation  curr :$currentZOrientation  check : $detialZ"
                        )
                        notifyTwist(TWIST_AXIS_Z, ORIENTATION_Z)
                    }
                }
                TWIST_AXIS_XYZ -> {
                    if (xExt || yExt || zExt) {
                        var o =
                            if (xExt) ORIENTATION_X else if (yExt) ORIENTATION_Y else ORIENTATION_Z
                        Log.e(
                            TAG,
                            "orientation : X $xExt  first: $firstXOrientation  curr :$currentXOrientation  check : $detialX \n" +
                                    "orientation : Y $yExt  first: $firstYOrientation  curr :$currentYOrientation  check : $detialY \n" +
                                    "orientation : Z $zExt  first: $firstZOrientation  curr :$currentZOrientation  check : $detialZ \n"
                        )
                        notifyTwist(TWIST_AXIS_XYZ, o)
                    }
                }
            }
        }
    }


    /*0 ~ 180 */
    private fun getxOrientation(angradx: Double): Double {
        var anglex = toDegrees(angradx)
        return 90 + anglex
    }

    /*0 ~ 360 */
    private fun getyOrientation(angrad: Double): Double {
        var angle = toDegrees(angrad)
        if (angle > 0) {
            return angle
        }
        return angle + 360
    }

    /*0 ~ 360 */
    private fun getzOrientation(angrad: Double): Double {
        var angle = toDegrees(angrad)
        if (angle > 0) {
            return angle
        }
        return angle + 360
    }

    private fun setIntervalX(firstData: Int, angle: Int): Array<Int> {
        var threshold = angle
        if (threshold >= 180) {
            threshold = 180 - 1
        }
        var left = 0
        var right = 180
        var type = 0 //计算方式

        if (firstData - angle < 0) {
            left = firstData + angle
            right = 180
            type = 1
        }

        if (firstData + angle > 180) {
            left = 0
            right = firstData - angle
            type = 1
        }

        if (firstData - angle > 0 && firstData + angle < 180) {
            left = firstData - angle
            right = firstData + angle
            type = 0
        }

        return arrayOf(left, right, type)
    }

    private fun setInterval(firstData: Int, angle: Int): Array<Int> {
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


    private fun notifyTwist(orientation: Int, value: Int) {
        if (!notifyTwist) {
            Log.d(TAG, "notifyTwist = $orientation $value")
            notifyTwist = true
            listener?.twistSuccess(orientation, value)
            removeListener()
        }
    }

    fun removeListener() {
        if (sensor != null) {
            mSensorManager?.let {
                it.unregisterListener(this, sensor)
                sensor = null
            }
        }
    }

    interface SenSorTwistListener {
        fun twistSuccess(orientationType: Int, orientation: Int)
    }

}
