package com.example.demo.sundu.recycleview.imtalk

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R

class DevelopRecycleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val title: TextView = view.findViewById(R.id.developer_recyclerview_holder_item_title)

    val img: ImageView = view.findViewById(R.id.developer_recyclerview_holder_item_img)
}