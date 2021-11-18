package com.example.demo.sundu.developer.stickyheadrecyclerview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R

class StickyRecyclerViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.title)
    val text :TextView = itemView.findViewById(R.id.text)
}