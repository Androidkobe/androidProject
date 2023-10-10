package com.example.demo.sundu.developer.nestedscrollview

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_nestedscrollview_recycleview.viewpager

class NestedScrollActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nestedscrollview_recycleview)
        viewpager.adapter = NestedFragmentStatedAdapter(this)
        viewpager.adapter?.notifyDataSetChanged()
    }

    class NestedFragmentStatedAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return 4
        }

        override fun createFragment(position: Int): Fragment {
            var fragment = RecycleViewFragment()
            fragment.setPosition(position)
            return fragment
        }
    }
}