package com.example.demo.sundu.developer.recycleview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demo.R

class DevelopRecycleViewAdapter(val data: MutableList<DevelopItemData>) :
    RecyclerView.Adapter<DevelopRecycleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevelopRecycleViewHolder {
        var holder = DevelopRecycleViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_develop_recycleview_item_holder, parent, false))
        Log.e("sundu","create view ${holder.adapterPosition}")
        return holder
    }

    override fun getItemViewType(position: Int): Int {
        data[position].id = position
        return super.getItemViewType(position)
    }
    override fun onBindViewHolder(holder: DevelopRecycleViewHolder, position: Int) {
        Log.e("sundu","bind view $position")
        holder.title.text = data[position].title
        Glide.with(holder.img.context).load(data[position].url).into(holder.img)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}