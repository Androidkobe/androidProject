package com.example.demo.sundu.custview.cropimage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.demo.R


class CropImageActivity : AppCompatActivity() {

    lateinit var dataBinding: CropImageActivityDataBinding

    val viewModel by lazy {
        ViewModelProvider(this)[AngleViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_crop_imageview)
        dataBinding.lifecycleOwner = this
        dataBinding.angleViewModel = viewModel
        viewModel.startAction()
    }
}