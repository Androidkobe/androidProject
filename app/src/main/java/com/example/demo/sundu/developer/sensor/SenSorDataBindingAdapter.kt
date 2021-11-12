package com.example.demo.sundu.developer.sensor

import androidx.databinding.BindingConversion


object SenSorDataBindingAdapter {


    @BindingConversion
    @JvmStatic
    fun convertIntToString(num: Int): String {
        return num.toString()
    }
}