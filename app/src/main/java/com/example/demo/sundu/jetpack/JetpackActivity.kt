package com.example.demo.sundu.jetpack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.demo.R
import com.example.demo.sundu.jetpack.datastore.JetPackDataStoreActivity
import com.example.demo.sundu.jetpack.livedata.JetPackLiveDataActivity
import com.example.demo.sundu.jetpack.workmanager.JetPackWorkManagerActivity
import kotlinx.android.synthetic.main.jetpack_main_activity_layout.*

class JetpackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.jetpack_main_activity_layout)
        mJectpack_WorkManager.setOnClickListener{
            startActivity(Intent(this,JetPackWorkManagerActivity::class.java))
        }
        mJectpack_LiveData.setOnClickListener{
            startActivity(Intent(this,JetPackLiveDataActivity::class.java))
        }
        mJectpack_LiveData.setOnClickListener{
            startActivity(Intent(this,JetPackDataStoreActivity::class.java))
        }
    }
}