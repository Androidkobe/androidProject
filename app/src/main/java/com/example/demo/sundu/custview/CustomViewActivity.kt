package com.example.demo.sundu.custview

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.demo.R
import com.example.demo.common.CommonSelectRecycleViewAdapter
import com.example.demo.common.ItemViewClick
import com.example.demo.sundu.custview.SliderView.SliderViewActivity
import com.example.demo.sundu.custview.animationview.lottie.ProcessLottieActivity
import com.example.demo.sundu.custview.beziercurve.dragpop.BezierCurverDragPopActivity
import com.example.demo.sundu.custview.bgcolorchange.BackgroundColorChangeActivity
import com.example.demo.sundu.custview.bitmapshader.BitmapShaderTelescopeViewActivity
import com.example.demo.sundu.custview.downloadview.DownLoadViewActivity
import com.example.demo.sundu.custview.path.PathDrawActivity
import com.example.demo.sundu.custview.region.RegionActivity
import com.example.demo.sundu.custview.ripple.RippleViewActivity
import com.example.demo.sundu.custview.scaleimage.ScaleImageActivity
import com.example.demo.sundu.custview.shakeview.ShakeCustomViewActivity
import com.example.demo.sundu.custview.shimmer.ShimmerActivity
import kotlinx.android.synthetic.main.activity_custom_view.*

class CustomViewActivity : AppCompatActivity() {

    var dataSource = HashMap<String, Class<*>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view)
        createData()
        custom_view_recycle.setHasFixedSize(true)
        custom_view_recycle.layoutManager = GridLayoutManager(this, 3)
        custom_view_recycle.adapter = CommonSelectRecycleViewAdapter(getData(), itemViewClick)
    }

    private val itemViewClick: ItemViewClick = object :
        ItemViewClick {
        override fun onItemViewClick(position: Int, view: View) {
           val result =dataSource[getData()[position]]
            startActivity(Intent(this@CustomViewActivity,result))
        }
    }

    private fun createData() {
        dataSource["downloadView"] = DownLoadViewActivity::class.java
        dataSource["bezier-dragpop"] = BezierCurverDragPopActivity::class.java
        dataSource["bitmapShader-Telescope"] = BitmapShaderTelescopeViewActivity::class.java
        dataSource["shimmer"] = ShimmerActivity::class.java
        dataSource["region"] = RegionActivity::class.java
        dataSource["SlideLockView"] = SliderViewActivity::class.java
        dataSource["ScaleImageView"] = ScaleImageActivity::class.java
        dataSource["摇一摇"] = ShakeCustomViewActivity::class.java
        dataSource["lottie"] = ProcessLottieActivity::class.java
        dataSource["PathDraw"] = PathDrawActivity::class.java
        dataSource["颜色渐变"] = BackgroundColorChangeActivity::class.java
        dataSource["波纹"] = RippleViewActivity::class.java
    }

    private fun getData(): List<String> {
        val stringArray: MutableList<String> = mutableListOf()
        dataSource.forEach{
            stringArray.add(it.key)
        }
        return stringArray
    }
}