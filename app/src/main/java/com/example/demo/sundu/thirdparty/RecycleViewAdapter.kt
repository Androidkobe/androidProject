package com.example.demo.sundu.thirdparty

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import kotlinx.android.synthetic.main.recycle_item_view_third_party_frame_holder.view.*

class RecycleViewAdapter(var data: List<String>, var itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<ThirdPartyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThirdPartyViewHolder {
        return ThirdPartyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycle_item_view_third_party_frame_holder, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ThirdPartyViewHolder, position: Int) {
        holder?.apply {
            textView.setText(data[position])
            textView.setBackgroundColor(getColor().toInt())
            textView.setOnClickListener {
                itemClickListener?.onItemClick(position)
            }
        }
    }

    override fun getItemCount() = data.size

    fun getColor(): Long {
        var random = java.util.Random()
        return (0xff000000 or random.nextInt(0x00ffffff).toLong())
    }
}

class ThirdPartyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView: TextView = itemView.recycle_third_party_frame_holder_text
}

interface OnItemClickListener {
    fun onItemClick(position: Int)
}

