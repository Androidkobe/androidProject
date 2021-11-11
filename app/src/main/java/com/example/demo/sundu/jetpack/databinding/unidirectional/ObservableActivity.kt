package com.example.demo.sundu.jetpack.databinding.unidirectional

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.demo.R
import com.example.demo.databinding.ObservableDataBindController

/**
 * observable 实现单向绑定
 */
class ObservableActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ObservableDataBindController>(
            this,
            R.layout.activity_data_binding_observable
        )
        val observableUser = ObserableUser()
        observableUser.name = "ccm"
        observableUser.age = 21
        binding.obserableUser = observableUser
        binding.clickHelper = ClickHelper()
    }
}