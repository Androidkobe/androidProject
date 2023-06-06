package com.example.demo.sundu.jetpack.databinding.bidirectional

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.demo.R
import com.example.demo.databinding.ActivityDataBindingShuangxiangBinding
import java.text.SimpleDateFormat
import java.util.*

class BidirectionalActivity : Activity() {
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityDataBindingShuangxiangBinding>(
            this,
            R.layout.activity_data_binding_shuangxiang
        )

        binding.timeModel

        binding.timeModel = TimeModel().apply {
            timeObservable.set(format.format(Date()))
        }

        binding.btnChangeTime.setOnClickListener {
            binding.customTimeView.time = format.format(Date())
        }
    }
}