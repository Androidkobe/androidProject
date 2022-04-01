package com.example.demo.sundu.recycleview.customlayoutone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import cc.xiaobaicz.recyclerview.extend.config
import com.example.demo.R
import com.example.demo.databinding.CommonRecycleViewBinding

class CustomLayoutOneRecycleView : AppCompatActivity() {

    val viewModel by lazy {
        ViewModelProvider(this)[CustomLayoutOneViewModel::class.java]
    }

    val viewBind by lazy {
        CommonRecycleViewBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBind.root)
        viewBind.recyclerview.config(viewModel.listData, lm = CustomLayoutManagerTest()) {
            addType<CustomLayoutOneData, CustomLayoutOneDataViewHolder>(R.layout.common_recycle_text_item_layout) { itemdata, holdview, postion, proload ->

                with(holdview.bind) {
                    textView.text = itemdata.name
                }

            }
        }
        viewModel.loadData()
        viewModel.liveData.observe(this) {
            viewBind.recyclerview.adapter?.notifyDataSetChanged()
        }
    }
}