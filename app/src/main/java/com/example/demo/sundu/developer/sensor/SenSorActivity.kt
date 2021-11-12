package com.example.demo.sundu.developer.sensor

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.demo.R
import com.example.demo.databinding.ActivitySensorBinding

class SenSorActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBinding = DataBindingUtil.setContentView<ActivitySensorBinding>(
            this,
            R.layout.activity_sensor
        )
        var data = SenSorData()
        var result = SenSorData()
        var clickHelper = SenSorClickHelper(this, data, result)
        dataBinding.senSorData = data
        dataBinding.clickHelper = clickHelper
        dataBinding.resultData = result
        clickHelper.start()

    }
}