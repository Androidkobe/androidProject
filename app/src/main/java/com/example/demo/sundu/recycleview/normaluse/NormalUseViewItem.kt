package com.example.demo.sundu.recycleview.normaluse

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.databinding.CommonRecycleTextItemLayoutBinding

data class RecyclerViewItem(
    var name: String,
    var classname: Class<*>?
)

/**
 * 自定义支持ViewBinding的ViewHolder
 */
class RecyclerViewItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val bind by lazy {
        CommonRecycleTextItemLayoutBinding.bind(view)
    }
}
