package com.example.demo.sundu.developer.coordinator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.databinding.ActivityCoordinatorLayoutBinding

class CoordinatorActivity : AppCompatActivity() {

    lateinit var viewDataBinding: ActivityCoordinatorLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = ActivityCoordinatorLayoutBinding.inflate(layoutInflater)
        setContentView(viewDataBinding.root)
    }
}