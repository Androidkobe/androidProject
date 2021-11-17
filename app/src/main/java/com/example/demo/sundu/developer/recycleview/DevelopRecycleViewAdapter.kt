package com.example.demo.sundu.developer.recycleview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demo.R

class DevelopRecycleViewAdapter(val data: List<DevelopItemData>) :
    RecyclerView.Adapter<DevelopRecycleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevelopRecycleViewHolder {
        return DevelopRecycleViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_develop_recycleview_item_holder, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DevelopRecycleViewHolder, position: Int) {
        holder.title.text = data[position].title
        Glide.with(holder.img.context).load(data[position].url).into(holder.img)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}