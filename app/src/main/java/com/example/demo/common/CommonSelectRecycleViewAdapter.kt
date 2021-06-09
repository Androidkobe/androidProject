package com.example.demo.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import kotlinx.android.synthetic.main.common_select_recycleview_layout_holder_item.view.*
import kotlinx.android.synthetic.main.develop_main_layout_holder_item.view.*

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */

class CommonSelectRecycleViewAdapter(private val mainDatas:List<String>, private val itemViewClick: ItemViewClick) : RecyclerView.Adapter<CommonSelectRecycleViewAdapter.MainHolderView>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolderView {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.common_select_recycleview_layout_holder_item,parent,false)
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
        var textView:TextView = itemView.holder_item_text
    }

}