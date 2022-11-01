package com.example.demo.sundu.developer.viewpager2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.demo.R
import kotlinx.android.synthetic.main.developer_viewpage2_collection_demo.*
import kotlinx.android.synthetic.main.developer_viewpage2_collection_demo.view.*

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
//        val tabLayout = tab_layout
//        tabLayout.visibility = View.GONE
//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            tab.text = "OBJECT ${(position + 1)}"
//        }.attach()

        var radio = R.id.tb
        set.setOnClickListener {
            var list = ArrayList<Int>()
            if (color_main.isChecked) {
                list.add(0)
            }
            if (color_mute.isChecked) {
                list.add(1)
            }
            if (color_light.isChecked) {
                list.add(2)
            }
            if (color_dark.isChecked) {
                list.add(3)
            }
            if (color_va_light.isChecked) {
                list.add(4)
            }
            if (color_va_dark.isChecked) {
                list.add(5)
            }
            if (list.size < 2) {
                Toast.makeText(context, "必须选中两个颜色", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            demoCollectionPagerAdapter.changeData(list, radio)
        }

        radiogroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                radio = checkedId
            }
        })

        var list = ArrayList<Int>()
        list.add(0)
        list.add(1)

        demoCollectionPagerAdapter.changeData(list, radio)

        hideshow.setOnClickListener {
            check_layout.visibility =
                if (check_layout.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            set.visibility = if (set.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            hideshow.text = if (check_layout.visibility == View.VISIBLE) "隐藏" else "显示"
        }
        hideshow.text = if (check_layout.visibility == View.VISIBLE) "隐藏" else "显示"
    }
}
