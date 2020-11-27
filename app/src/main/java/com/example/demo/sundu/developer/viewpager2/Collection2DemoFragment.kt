package com.example.demo.sundu.developer.viewpager2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.demo.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.developer_viewpage2_collection_demo.*

class Collection2DemoFragment : Fragment() {
    private lateinit var demoCollectionPagerAdapter: DemoCollection2PageAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.developer_viewpage2_collection_demo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager = pager
        viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        demoCollectionPagerAdapter = DemoCollection2PageAdapter(this)
        viewPager.adapter = demoCollectionPagerAdapter
        val tabLayout = tab_layout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()
    }
}
