package com.example.demo.sundu.recycleview.normaluse.animation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.databinding.CommonRecycleTextItemLayoutBinding

data class AnimationData(val name:String)

class AnimationDataViewHolder(view : View): RecyclerView.ViewHolder(view) {
    val bind by lazy {
        CommonRecycleTextItemLayoutBinding.bind(view)
    }
}
