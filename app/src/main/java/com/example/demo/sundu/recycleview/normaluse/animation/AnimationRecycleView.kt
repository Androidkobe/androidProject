package com.example.demo.sundu.recycleview.normaluse.animation

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import cc.xiaobaicz.recyclerview.extend.config
import com.example.demo.R
import com.example.demo.databinding.RecyclerviewAnimationViewBinding

class AnimationRecycleView:AppCompatActivity() {

    val viewModel by lazy {
        ViewModelProvider(this)[AnimationViewModel::class.java]
    }

    val viewBind by lazy {
        RecyclerviewAnimationViewBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBind.root)
        viewBind.recyclerview.setBackgroundColor(Color.parseColor("#002299"))
        viewBind.recyclerview.config(viewModel.listData){
            addType<AnimationData,AnimationDataViewHolder>(R.layout.common_recycle_text_item_layout){
                itemdata,holdview,postion,proload->

                with(holdview.bind){
                    textView.text = itemdata.name
                    holdview.bind.root.setOnClickListener {
                        Toast.makeText(textView.context,itemdata.name,Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }

        viewModel.loadData()

        viewModel.liveData.observe(this) {
            viewBind.recyclerview.adapter?.notifyDataSetChanged()
        }

        viewBind.insert.setOnClickListener {
            viewModel.addData()
            viewBind.recyclerview.adapter?.notifyItemInserted(viewModel.listData.size-1)
        }

        viewBind.delete.setOnClickListener {
            viewModel.remove()
            viewBind.recyclerview.adapter?.notifyItemRemoved(viewModel.listData.size)
        }

        viewBind.radiogroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.radio1->{
                    viewBind.recyclerview.itemAnimator?.addDuration = 2000
                }
                R.id.radio2->{

                }
                R.id.radio3->{

                }
            }
        }
    }
}