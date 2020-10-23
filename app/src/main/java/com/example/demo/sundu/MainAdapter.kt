package com.example.demo.sundu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import kotlinx.android.synthetic.main.main_layout_holder_item.view.*

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */

class MainAdapter(private val mainDatas:List<String>) : RecyclerView.Adapter<MainAdapter.MainHolderView>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolderView {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.main_layout_holder_item,parent,false)
        return MainHolderView(itemView)
    }

    override fun getItemCount() = mainDatas.size

    override fun onBindViewHolder(holder: MainHolderView, position: Int) {
        holder.apply {
            textView.text = mainDatas[position]
        }
    }

    class MainHolderView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView:TextView = itemView.main_holder_item_text
    }
}