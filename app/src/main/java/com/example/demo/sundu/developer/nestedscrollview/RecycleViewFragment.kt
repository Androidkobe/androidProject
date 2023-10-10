package com.example.demo.sundu.developer.nestedscrollview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_nestedscrollview_fragment_recycleview.mRecycleView
import kotlinx.android.synthetic.main.activity_nestedscrollview_fragment_recycleview.textView

class RecycleViewFragment:Fragment() {

    var mPosition = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_nestedscrollview_fragment_recycleview,container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView.text = mPosition.toString()
        mRecycleView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
        mRecycleView.adapter = object : RecyclerView.Adapter<TextViewHolder>(){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
                var view = TextView(context)
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

    fun setPosition(i:Int){
        mPosition = i
    }
    class TextViewHolder(view: TextView) : RecyclerView.ViewHolder(view){
        var text = view
        fun bind(positon:Int){
            text.text = positon.toString()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.e("sundu","init show $mPosition $hidden")
    }

    override fun onStart() {
        super.onStart()
        Log.e("sundu","onstart $mPosition ")
    }

    override fun onResume() {
        super.onResume()
        Log.e("sundu","onResume $mPosition ")
    }

    override fun onPause() {
        super.onPause()
        Log.e("sundu","onPause $mPosition ")
    }
}