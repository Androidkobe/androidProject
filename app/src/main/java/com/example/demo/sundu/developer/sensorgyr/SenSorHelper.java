package com.example.demo.sundu.developer.sensorgyr;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class SenSorHelper implements SensorEventListener {

    public static final float MaxValue = 1.0f;
    private static final int SensorBufferCount = 2;
    private static final int SensorAccValueDimens = 3;
    private float[][] mAccValuesForAverage = new float[SensorBufferCount][];
    private int mAccValuesForAverageIndex = 0;
    private float[] mAccValues;
    private float mSensorLastAngle = 0;
    private long mSensorLastChangedTime = Long.MAX_VALUE;
    private float[] gravity = new float[3];//用来保存加速度传感器的值
    private float[] r =new float[9];
    private float[] geomagnetic = new float[3];//用来保存地磁传感器的值
    private float[] values = new float[3];//用来保存最终的结果

    private SensorManager mSensorManager;
    private Sensor magneticSensor;
    private Sensor accelerometerSensor;

    private double lastDegreeZ = 0;
    private double lastDegreeX = 0;
    private double lastDegreeY = 0;

    public void start(Context context){
        Object systemService = context.getSystemService(Context.SENSOR_SERVICE);
        if (systemService != null) {
            mSensorManager = (SensorManager) systemService;
            magneticSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            magneticSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            mSensorManager.registerListener(this,magneticSensor, SensorManager.SENSOR_DELAY_NORMAL);
            mSensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event != null) {
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                geomagnetic = event.values;
            }
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                gravity = event.values;
                float[] fArr = event.values;
                mAccValuesForAverage[mAccValuesForAverageIndex] = fArr;
                mAccValuesForAverageIndex++;
                if (mAccValuesForAverageIndex == SensorBufferCount) {
                    mAccValuesForAverageIndex = 0;
                    float[][] v = mAccValuesForAverage;
                    float[] result = new float[SensorAccValueDimens];
                    for (int i = 0; i < SensorAccValueDimens; i++) {
                        for (int j = 0; j < SensorBufferCount; j++) {
                            result[i] += v[j][i];
                        }
                    }
                    for (int i = 0; i < SensorAccValueDimens; i++) {
                        result[i] /= SensorBufferCount;
                    }
                    handleNewSensorAverageValue(result);
                }
            }
        }
    }

    private void handleNewSensorAverageValue(float[] fArr) {
        this.mAccValues = fArr;
        float accX = fArr[0] / 10.0f;
        float[] fArr2 = this.mAccValues;
        float accY = fArr2[1] / 10.0f;
        float[] fArr3 = this.mAccValues;
        float accZ = fArr3[2] / 10.0f;
        float angle = toAngle(-((float) Math.atan2((double) (-accY), (double) (-accX))));
        if (angle < 0) {
            angle += (float) 360;
        }
        mSensorLastAngle = angle;
        // r从这里返回
        SensorManager.getRotationMatrix(r, null, gravity, geomagnetic);
        //values从这里返回
        SensorManager.getOrientation(r, values);
        //提取数据
        lastDegreeZ = Math.toDegrees(values[0]);
        lastDegreeX =  Math.toDegrees(values[1]);
        lastDegreeY=  Math.toDegrees(values[2]);

        Log.e("sundu","角度 = "+mSensorLastAngle +" x = "+lastDegreeX +" y = "+lastDegreeY +" z = "+lastDegreeZ);
    }



    private boolean isSensorNotChangedForAWhile() {
        return System.currentTimeMillis() - mSensorLastChangedTime >500;
    }


    private final float toAngle(float angle) {
        double d = (double) (((float) 180) * angle);
        Double.isNaN(d);
        return (float) (d / 3.141592653589793d);
    }
}
