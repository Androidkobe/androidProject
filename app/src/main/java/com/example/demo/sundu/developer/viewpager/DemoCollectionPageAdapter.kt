package com.example.demo.sundu.developer.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.demo.R


class DemoCollectionPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {


    private  val ARG_OBJECT = "object"

    override fun getCount(): Int  =4

    override fun getItem(i: Int): Fragment {
        val fragment = DemoObjectFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_OBJECT, i + 1)
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return "OBJECT ${(position + 1)}"
    }
}