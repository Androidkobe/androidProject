package com.example.demo.sundu.recycleview.customlayoutone

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.databinding.CommonRecycleTextItemLayoutBinding

data class CustomLayoutOneData(val name: String)

class CustomLayoutOneDataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val bind by lazy {
        CommonRecycleTextItemLayoutBinding.bind(view)
    }
}
