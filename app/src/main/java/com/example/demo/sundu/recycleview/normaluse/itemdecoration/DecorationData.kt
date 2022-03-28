package com.example.demo.sundu.recycleview.normaluse.itemdecoration

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.databinding.CommonRecycleTextItemLayoutBinding

data class DecorationData(val name:String)

class DecorationDataViewHolder(view : View): RecyclerView.ViewHolder(view) {
    val bind by lazy {
        CommonRecycleTextItemLayoutBinding.bind(view)
    }
}
