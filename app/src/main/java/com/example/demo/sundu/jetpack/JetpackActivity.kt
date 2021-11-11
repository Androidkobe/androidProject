package com.example.demo.sundu.jetpack

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import com.example.demo.sundu.jetpack.databinding.DataBindingTypeControllerActivity
import com.example.demo.sundu.jetpack.datastore.JetPackDataStoreActivity
import com.example.demo.sundu.jetpack.livedata.JetPackLiveDataActivity
import com.example.demo.sundu.jetpack.navigatin.JetPackNavigationActivity
import com.example.demo.sundu.jetpack.workmanager.JetPackWorkManagerActivity
import kotlinx.android.synthetic.main.jetpack_main_activity_layout.*

class JetPackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.jetpack_main_activity_layout)
        mJectpack_WorkManager.setOnClickListener{
            startActivity(Intent(this,JetPackWorkManagerActivity::class.java))
        }
        mJectpack_LiveData.setOnClickListener {
            startActivity(Intent(this, JetPackLiveDataActivity::class.java))
        }
        mJectpack_DataStore.setOnClickListener {
            startActivity(Intent(this, JetPackDataStoreActivity::class.java))
        }
        mJectpack_Navigation.setOnClickListener {
            startActivity(Intent(this@JetPackActivity, JetPackNavigationActivity::class.java))
        }
        mJectpack_Data_binding.setOnClickListener {
            startActivity(
                Intent(
                    this@JetPackActivity,
                    DataBindingTypeControllerActivity::class.java
                )
            )
        }
    }
}