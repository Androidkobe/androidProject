package com.example.demo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.common.ItemViewClick
import kotlinx.android.synthetic.main.activity_main_layout_holder_item.view.*
import java.io.InputStream
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */

class MainAdapter(private val mainDatas:List<String>,private val itemViewClick: ItemViewClick) : RecyclerView.Adapter<MainAdapter.MainHolderView>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolderView {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_main_layout_holder_item,parent,false)
        return MainHolderView(itemView)
    }

    override fun getItemCount() = mainDatas.size

    override fun onBindViewHolder(holder: MainHolderView, position: Int) {
        holder.apply {
            textView.text = mainDatas[position]
            textView.setBackgroundColor(getColor().toInt())
            holder.itemView.setOnClickListener {
                itemViewClick.onItemViewClick(position,it)
            }
        }
    }

    fun getColor():Long{
        var random = java.util.Random()
        return (0xff000000 or random.nextInt(0x00ffffff).toLong())
    }


    class MainHolderView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.main_holder_item_text
    }

    fun getMd5FromFilePath(filePath: String): String {
        val file = java.io.File(filePath)
        val inputStream: InputStream = file.inputStream()
        val buffer = ByteArray(1024)
        var digest: MessageDigest? = null
        try {
            digest = MessageDigest.getInstance("MD5")
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }

        var numRead = 0
        try {
            while (numRead != -1) {
                numRead = inputStream.read(buffer)
                if (numRead > 0)
                    digest!!.update(buffer, 0, numRead)
            }
            val md5Bytes = digest!!.digest()
            val bigInt = BigInteger(1, md5Bytes)
            return bigInt.toString(16)
        } catch (e: Exception) {
            return ""
        }

    }
}