package com.example.demo.sundu.developer.nestedscrollview

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_nestedscrollview_recycleview.viewpager

class NestedScrollActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nestedscrollview_recycleview)
        viewpager.adapter = object :FragmentStateAdapter(){

        }

        mRecycleView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        mRecycleView.adapter = object :RecyclerView.Adapter<TextViewHolder>(){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
                var view = TextView(this@NestedScrollActivity)
                    view.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,200)
                return TextViewHolder(view)
            }

            override fun getItemCount(): Int {
                return 50
            }

            override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
                holder.bind(position)
                Log.e("sundu","bind $position")
            }

        }
        mRecycleView.adapter?.notifyDataSetChanged()
    }
    class TextViewHolder(view: TextView) : RecyclerView.ViewHolder(view){
        var text = view
        fun bind(positon:Int){
            text.text = positon.toString()
        }
    }
}