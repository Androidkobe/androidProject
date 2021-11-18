package com.example.demo.sundu.developer.stickyheadrecyclerview

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_develop_recycleview.*

class StickyHeadRecyclerViewActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sticky_recyclerview)
        mRecycleView.layoutManager = LinearLayoutManager(baseContext)
        var data = createData()
        mRecycleView.adapter = StickyRecyclerViewAdapter(data)
        var decoration = StickyDecoration(baseContext,object :DecorationCallback{
            override fun getGroupString(position: Int): String {
                return data.get(position).tite;
            }
        })
        mRecycleView.addItemDecoration(decoration)
        mRecycleView.adapter?.notifyDataSetChanged()
    }

    fun createData() : MutableList<StickInfo>{
        var list = mutableListOf<StickInfo>()
        list.add(StickInfo("a","a1"))
        list.add(StickInfo("a","a2"))
        list.add(StickInfo("a","a3"))
        list.add(StickInfo("a","a4"))
        list.add(StickInfo("b","b1"))
        list.add(StickInfo("b","b1"))
        list.add(StickInfo("b","b2"))
        list.add(StickInfo("b","b3"))
        list.add(StickInfo("b","b4"))
        list.add(StickInfo("c","c1"))
        list.add(StickInfo("c","c2"))
        list.add(StickInfo("c","c3"))
        list.add(StickInfo("c","c4"))
        list.add(StickInfo("d","d1"))
        list.add(StickInfo("d","d2"))
        list.add(StickInfo("d","d3"))
        list.add(StickInfo("d","d4"))
        list.add(StickInfo("e","e1"))
        list.add(StickInfo("e","e2"))
        list.add(StickInfo("e","e3"))
        list.add(StickInfo("e","e4"))
        list.add(StickInfo("f","f1"))
        list.add(StickInfo("f","f2"))
        list.add(StickInfo("f","f3"))
        list.add(StickInfo("f","f4"))
        return list
    }
}

interface DecorationCallback {
    fun getGroupString(position: Int): String
}