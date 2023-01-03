package com.example.demo.sundu.developer.sensorgyr;

import android.app.Service;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.R;

public class GyrActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developer_sensor_gyr);
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SenSorGyrHelper sen = new SenSorGyrHelper();
                sen.setThreshold(3000, 30, 50);
                sen.registerListener(GyrActivity.this, new SenSorGyrHelper.SensorRotateListener() {
                    @Override
                    public void rotate(boolean left) {
                        vibrate(2000);
                    }
                });
            }
        });
    }


    private void vibrate(long milliseconds) {
        Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }
}