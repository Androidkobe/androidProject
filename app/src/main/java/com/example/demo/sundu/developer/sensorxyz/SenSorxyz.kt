package com.example.demo.sundu.developer.sensorxyz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.demo.R
import com.example.demo.databinding.SersorXyzLayoutBinding

class SenSorxyz : AppCompatActivity() {

    val viewModel by lazy {
        ViewModelProvider(this)[SensorViewModel::class.java]
    }

    lateinit var dataBinding: SersorXyzLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.sersor_xyz_layout)
        dataBinding.lifecycleOwner = this
        dataBinding.viewModel = viewModel
        loadData()
    }

    private fun loadData() {
        viewModel.createData()
    }
}