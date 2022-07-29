package com.example.demo.sundu.developer.sensorgyr;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.R;

public class GyrActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developer_sensor_gyr);
        findViewById(R.id.start).setOnClickListener(v -> new SenSorHelper().start(GyrActivity.this));
    }
}