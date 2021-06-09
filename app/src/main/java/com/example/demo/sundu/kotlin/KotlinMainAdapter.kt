package com.example.demo.sundu.kotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.common.ItemViewClick
import com.example.demo.R
import kotlinx.android.synthetic.main.kotlin_main_layout_holder_item.view.*

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */

class KotlinMainAdapter(private val mDatas:List<String>, private val itemViewClick: ItemViewClick): RecyclerView.Adapter<KotlinMainAdapter.KotlinHolderView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KotlinHolderView {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.kotlin_main_layout_holder_item,parent,false)
        return KotlinHolderView(itemView)
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    override fun onBindViewHolder(holder: KotlinHolderView, position: Int) {
        holder.mTextView.text = mDatas[position]
        holder.mTextView.setBackgroundColor(getColor().toInt())
        holder.itemView.setOnClickListener {
            itemViewClick.onItemViewClick(position,it)
        }
    }

    fun getColor():Long{
        val random = java.util.Random()
        return (0xff000000 or random.nextInt(0x00ffffff).toLong())
    }

    class KotlinHolderView(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var mTextView : TextView = itemView.kotlin_main_holder_item_text
    }
}