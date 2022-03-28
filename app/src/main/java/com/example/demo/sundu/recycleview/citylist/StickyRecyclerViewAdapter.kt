package com.example.demo.sundu.recycleview.citylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R

class StickyRecyclerViewAdapter(val data:MutableList<StickInfo>): RecyclerView.Adapter<StickyRecyclerViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickyRecyclerViewHolder {
        return StickyRecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_sticky_recyclerview_item_holder,parent,false))
    }

    override fun onBindViewHolder(holder: StickyRecyclerViewHolder, position: Int) {
        holder.text.text = data[position].text
        holder.title.text = data[position].tite
    }

    override fun getItemCount(): Int {
        return data.size
    }
}