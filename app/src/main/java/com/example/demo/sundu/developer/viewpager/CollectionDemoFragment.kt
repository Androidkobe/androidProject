package com.example.demo.sundu.developer.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.demo.R
import kotlinx.android.synthetic.main.developer_viewpage_collection_demo.*

class CollectionDemoFragment : Fragment() {
    private lateinit var demoCollectionPagerAdapter: DemoCollectionPagerAdapter
    private lateinit var viewPager: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.developer_viewpage_collection_demo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager = pager
        demoCollectionPagerAdapter = DemoCollectionPagerAdapter(childFragmentManager)
        val tabLayout = tab_layout
        tabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = demoCollectionPagerAdapter
    }
}
