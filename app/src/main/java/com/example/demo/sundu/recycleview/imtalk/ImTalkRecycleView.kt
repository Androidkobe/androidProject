package com.example.demo.sundu.recycleview.imtalk

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo.R
import com.example.demo.sundu.ImgUrls
import kotlinx.android.synthetic.main.activity_develop_recycleview.*

class ImTalkRecycleView : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_develop_recycleview)
        mRecycleView.layoutManager = LinearLayoutManager(baseContext)
        mRecycleView.adapter = DevelopRecycleViewAdapter(createData())
        mRecycleView.addItemDecoration(LineItemDecoration())
        mRecycleView.itemAnimator = DefaultItemAnimator()
        mRecycleView.itemAnimator?.addDuration = 10000
        mRecycleView.itemAnimator?.removeDuration = 10000
        mRecycleView.adapter?.notifyDataSetChanged()
    }

    fun createData():MutableList<DevelopItemData>{
        val list = mutableListOf<DevelopItemData>()
        for ((i, url) in ImgUrls.urls.withIndex()) {
            if (i < 4) {
                list.add(DevelopItemData("$i", url))
            }
        }
        return list
    }

    fun itemChange(view:View){
        mRecycleView.adapter?.notifyItemChanged(2)
    }

    fun allChange(view:View){
        mRecycleView.adapter?.notifyDataSetChanged()
    }

    fun itemAdd(view:View){
        mRecycleView.adapter?.let {
            if(it is DevelopRecycleViewAdapter){
                it.data.add(DevelopItemData("${it.data.size}",ImgUrls.urls[0]))
                it.notifyItemChanged(it.data.size-1)
            }

        }
    }

    fun itemRemove(view:View){
        mRecycleView.adapter?.let {
            if(it is DevelopRecycleViewAdapter){
                it.data.removeLast()
                it.notifyItemRemoved(it.data.size)
            }

        }
    }
}