package com.example.demo.sundu.jetpack.databinding

import android.content.Context
import android.content.Intent
import android.view.View
import com.example.demo.sundu.jetpack.databinding.bidirectional.BidirectionalActivity
import com.example.demo.sundu.jetpack.databinding.unidirectional.ObservableActivity

class TypeClickHelp(context: Context) {

    val mContext = context;

    fun clickTypeToGo(view: View, type: Int) {
        when (type) {
            0 -> {
                mContext.startActivity(Intent(mContext, ObservableActivity::class.java))
            }
            1 -> {
                mContext.startActivity(Intent(mContext, BidirectionalActivity::class.java))
            }
        }
    }
}