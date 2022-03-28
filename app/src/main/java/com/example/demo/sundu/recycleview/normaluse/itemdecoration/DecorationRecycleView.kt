package com.example.demo.sundu.recycleview.normaluse.itemdecoration

import android.graphics.Color
import android.graphics.ColorSpace
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import cc.xiaobaicz.recyclerview.extend.config
import com.example.demo.R
import com.example.demo.databinding.CommonRecycleViewBinding

class DecorationRecycleView:AppCompatActivity() {

    val viewModel by lazy {
        ViewModelProvider(this)[DecorationViewModel::class.java]
    }

    val viewBind by lazy {
        CommonRecycleViewBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBind.root)
        viewBind.recyclerview.setBackgroundColor(Color.parseColor("#002299"))
        viewBind.recyclerview.config(viewModel.listData){
            addType<DecorationData,DecorationDataViewHolder>(R.layout.common_recycle_text_item_layout){
                itemdata,holdview,postion,proload->

                with(holdview.bind){
                    textView.text = itemdata.name
                }

            }
        }
        viewBind.recyclerview.addItemDecoration(MyDecoration())
        viewModel.loadData()
        viewModel.liveData.observe(this) {
            viewBind.recyclerview.adapter?.notifyDataSetChanged()
        }
    }
}