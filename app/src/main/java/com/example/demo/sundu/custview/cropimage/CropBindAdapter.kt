package com.example.demo.sundu.custview.cropimage

import androidx.databinding.BindingAdapter

object CropBindAdapter {

    @JvmStatic
    @BindingAdapter("angleChange")
    fun updateAngle(cropImageView: CropImageView, angle: Float) {
        cropImageView.setAngle(angle)
    }
}