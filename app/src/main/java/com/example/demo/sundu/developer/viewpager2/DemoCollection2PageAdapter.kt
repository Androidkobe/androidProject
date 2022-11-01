package com.example.demo.sundu.developer.viewpager2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.example.demo.R
import com.example.demo.getItem


class DemoCollection2PageAdapter(fm: Fragment) : FragmentStateAdapter(fm) {


    private val ARG_OBJECT = "object"
    private val COLORS = "colors"

    override fun getItemCount(): Int = 10

    var colorlist = ArrayList<Int>()

    var radio = R.id.tb

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        val fragment = DemoObject2Fragment()
        fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            putInt(ARG_OBJECT, position)
            putIntegerArrayList(COLORS, colorlist)
        }
        Log.e("sundu-viewpage2", position.toString());
        return fragment
    }

    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        var fragment = getItem(position) as DemoObject2Fragment
        fragment.bindData(position, radio, colorlist)
        Log.e("sundu-viewpage2", "bind - " + position.toString());
    }

    fun changeData(list: ArrayList<Int>, radio: Int) {
        colorlist = list
        this.radio = radio
        notifyDataSetChanged()
    }
}