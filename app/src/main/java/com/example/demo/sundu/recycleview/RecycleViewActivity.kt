package com.example.demo.sundu.recycleview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import cc.xiaobaicz.recyclerview.extend.config
import com.example.demo.R
import com.example.demo.databinding.ActivityRecycleViewBinding

class RecycleViewActivity : AppCompatActivity() {

    val binView by lazy {
        ActivityRecycleViewBinding.inflate(layoutInflater)
    }

    val viewDataModel by lazy {
        ViewModelProvider(this)[RecycleViewDataModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binView.root)

        binView.recyclerview.config(viewDataModel.datalist) {
            addType<RecyclerViewItem, RecyclerViewItemViewHolder>(
                R.layout.common_recycle_text_item_layout
            ) { itemData, holderView, position, payloads ->
                with(holderView.bind) {
                    textView.setText(itemData.name)
                    holderView.bind.root.setOnClickListener{
                        startActivity(Intent(this@RecycleViewActivity,itemData.classname))
                    }
                }
            }
        }

        viewDataModel.loadData()
        viewDataModel.liveData.observe(this) { list ->
            binView.recyclerview.adapter?.notifyDataSetChanged()
        }
    }
}