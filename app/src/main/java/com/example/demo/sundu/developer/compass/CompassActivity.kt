package com.example.demo.sundu.developer.compass

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener2
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import kotlinx.android.synthetic.main.compass_activity_main.*


class CompassActivity : AppCompatActivity(), Runnable {


    private var mTargetDirection: Float = 0.0f
    private var mStopDrawing: Boolean = false
    private var accelerometerValues = FloatArray(3)
    private var magneticFieldValues = FloatArray(3)
    private lateinit var mSensorManager: SensorManager
    private lateinit var mOrientationSensor: Sensor
    private lateinit var mMagneticSensor: Sensor
    private lateinit var mOrientationListener: MySensorEventListener
    private lateinit var mMagneticListener: MySensorEventListener
    private val mHandler = Handler()

    override fun run() {
        if (mDrawPointer != null && !mStopDrawing) {
            mDrawPointer.updateDirection(360 - mTargetDirection)
            calculateOrientation()
            mHandler.postDelayed(this, 20)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.compass_activity_main)
        initResources()
        initServices()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onResume() {
        super.onResume()
        mOrientationListener = MySensorEventListener()
        mMagneticListener = MySensorEventListener()
        mSensorManager.registerListener(
            mOrientationListener,
            mOrientationSensor,
            Sensor.TYPE_ACCELEROMETER
        )
        mSensorManager.registerListener(
            mMagneticListener,
            mMagneticSensor,
            Sensor.TYPE_MAGNETIC_FIELD
        )
        mStopDrawing = false
        mHandler.postDelayed(this, 20)
    }

    override fun onPause() {
        super.onPause()
        mStopDrawing = true
        mSensorManager.unregisterListener(mOrientationListener)
        mSensorManager.unregisterListener(mMagneticListener)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    fun initResources() {
        mStopDrawing = true
    }

    fun initServices() {
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mOrientationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) //加速度传感器
        mMagneticSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)   //地磁场传感器
    }

    fun calculateOrientation() {
        val values = FloatArray(3)
        val RValues = FloatArray(9)
        SensorManager.getRotationMatrix(RValues, null, accelerometerValues, magneticFieldValues)
        SensorManager.getOrientation(RValues, values)
        values[0] = Math.toDegrees(values[0].toDouble()).toFloat()


        val lp: ViewGroup.LayoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        mDirectionLayout.removeAllViews()
        mAngleLayout.removeAllViews()
        var east: ImageView? = null
        var west: ImageView? = null
        var south: ImageView? = null
        var north: ImageView? = null
        val direction = values[0]

        //根据角度计算方位
        if (direction >= 22.5f && direction < 157.5f) {
            //east
            east = ImageView(this)
            east.setImageResource(R.mipmap.e_cn)
            east.layoutParams = lp
        } else if (direction > -157.5f && direction < -22.5f) {
            //west
            west = ImageView(this)
            west.setImageResource(R.mipmap.w_cn)
            west.layoutParams = lp
        }
        if (direction > 122.5f || direction < -122.5f) {
            // south
            south = ImageView(this)
            south.setImageResource(R.mipmap.s_cn)
            south.layoutParams = lp
        } else if (direction < 67.5f && direction > -67.5f) {
            // north
            north = ImageView(this)
            north.setImageResource(R.mipmap.n_cn)
            north.layoutParams = lp
        }
        if (east != null) {
            mDirectionLayout.addView(east)
        }
        if (west != null) {
            mDirectionLayout.addView(west)
        }
        if (south != null) {
            mDirectionLayout.addView(south)
        }
        if (north != null) {
            mDirectionLayout.addView(north)
        }
        mTargetDirection = direction
        var direction2 = normalizeDegree(direction).toInt()
        var show = false
        if (direction2 >= 100) {
            mAngleLayout.addView(getNumberImage(direction2 / 100))
            direction2 %= 100
            show = true
        }
        if (direction2 >= 10 || show) {
            mAngleLayout.addView(getNumberImage(direction2 / 10))
            direction2 %= 10
        }
        mAngleLayout.addView(getNumberImage(direction2))

        val degreeImageView = ImageView(this)
        degreeImageView.setImageResource(R.mipmap.degree)
        degreeImageView.layoutParams = lp
        mAngleLayout.addView(degreeImageView)

        Log.d("mainActivity", direction.toString())
    }

    private fun getNumberImage(number: Int): ImageView {
        val image = ImageView(this)
        val lp = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        when (number) {
            0 -> image.setImageResource(R.mipmap.number_0)
            1 -> image.setImageResource(R.mipmap.number_1)
            2 -> image.setImageResource(R.mipmap.number_2)
            3 -> image.setImageResource(R.mipmap.number_3)
            4 -> image.setImageResource(R.mipmap.number_4)
            5 -> image.setImageResource(R.mipmap.number_5)
            6 -> image.setImageResource(R.mipmap.number_6)
            7 -> image.setImageResource(R.mipmap.number_7)
            8 -> image.setImageResource(R.mipmap.number_8)
            9 -> image.setImageResource(R.mipmap.number_9)
        }
        image.layoutParams = lp
        return image
    }

    private fun normalizeDegree(degree: Float): Float {
        return (degree + 720) % 360
    }


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    inner class MySensorEventListener : SensorEventListener2 {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }

        override fun onFlushCompleted(sensor: Sensor?) {

        }

        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                accelerometerValues = event.values
            }
            if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
                magneticFieldValues = event.values
            }
        }

    }
}
