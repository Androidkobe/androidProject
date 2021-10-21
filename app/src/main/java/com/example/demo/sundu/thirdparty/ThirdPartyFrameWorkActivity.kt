package com.example.demo.sundu.thirdparty

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.demo.R
import com.example.demo.sundu.thirdparty.rxjava.Rxjava_Activity
import kotlinx.android.synthetic.main.activity_third_party_frame_work.*

class ThirdPartyFrameWorkActivity : AppCompatActivity() {

    var sourceData: MutableMap<String, Class<*>> = HashMap()


    init {
        sourceData["RxJava"] = Rxjava_Activity::class.java
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_party_frame_work)
        var data: MutableList<String> = createData()
        var adapter = RecycleViewAdapter(data, object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                startAction(data[position])
            }

        })
        mRecycleView.layoutManager = GridLayoutManager(baseContext, 3)
        mRecycleView.adapter = adapter
        adapter.notifyDataSetChanged()
    }


    private fun createData(): MutableList<String> {
        var list = ArrayList<String>()
        sourceData.forEach {
            list.add(it.key)
        }
        return list
    }

    private fun startAction(key: String) {
        var intent = Intent(baseContext, sourceData[key])
        startActivity(intent)
    }
}