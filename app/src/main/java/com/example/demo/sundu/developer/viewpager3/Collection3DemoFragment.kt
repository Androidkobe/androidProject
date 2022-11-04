package com.example.demo.sundu.developer.viewpager3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.demo.R
import kotlinx.android.synthetic.main.developer_viewpage2_collection_demo.*

class Collection3DemoFragment : Fragment() {
    private lateinit var demoCollectionPagerAdapter: DemoCollection3PageAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.developer_viewpage4_collection_demo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager = pager
        viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        demoCollectionPagerAdapter = DemoCollection3PageAdapter(this)
        viewPager.adapter = demoCollectionPagerAdapter
//        val tabLayout = tab_layout
//        tabLayout.visibility = View.GONE
//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            tab.text = "OBJECT ${(position + 1)}"
//        }.attach()

        var colortypeId = R.id.color_main
        set.setOnClickListener {
            demoCollectionPagerAdapter.changeData(colortypeId)
        }

        radiogroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                colortypeId = checkedId
            }
        })

        demoCollectionPagerAdapter.changeData(colortypeId)

        hideshow.setOnClickListener {
            check_layout.visibility =
                if (check_layout.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            set.visibility = if (set.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            hideshow.text = if (check_layout.visibility == View.VISIBLE) "隐藏" else "显示"
        }
        hideshow.text = if (check_layout.visibility == View.VISIBLE) "隐藏" else "显示"
    }
}
