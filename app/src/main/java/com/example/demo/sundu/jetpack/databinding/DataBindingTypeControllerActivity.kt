package com.example.demo.sundu.jetpack.databinding

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayMap
import com.example.demo.R
import com.example.demo.databinding.JetPackDataBindingTypeController

class DataBindingTypeControllerActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBinding = DataBindingUtil.setContentView<JetPackDataBindingTypeController>(
            this,
            R.layout.activity_data_binding
        )
        var types = ObservableArrayMap<Int, String>()
        dataBinding.types = TypeInfo(types)
        dataBinding.observeFile.postDelayed({
            //开始单向榜定
            types.put(0, "单向绑定")
            types.put(1, "双向绑定")
        }, 1000)

        dataBinding.clickHelp = TypeClickHelp(this)
    }
}