package com.example.demo.sundu.recycleview

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.sundu.recycleview.citylist.CityHeadRecycleView
import com.example.demo.sundu.recycleview.customlayoutone.CustomLayoutOneRecycleView
import com.example.demo.sundu.recycleview.imtalk.ImTalkRecycleView
import com.example.demo.sundu.recycleview.normaluse.NormalUseRecycleViewActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class RecycleViewDataModel : ViewModel() {

    var sourceMap = mutableMapOf<String, Class<*>>()

    init {
        sourceMap["基本使用"] = NormalUseRecycleViewActivity::class.java
        sourceMap["城市列表"] = CityHeadRecycleView::class.java
        sourceMap["对话"] = ImTalkRecycleView::class.java
        sourceMap["自定义layout(一)"] = CustomLayoutOneRecycleView::class.java
    }

    var datalist = mutableListOf<RecyclerViewItem>()

    var liveData = MutableLiveData<List<RecyclerViewItem>>()


    fun loadData() {
        viewModelScope.launch(Dispatchers.Main) {
            Log.e("sundu", "current thread = " + Thread.currentThread().name)
            sourceMap.keys.asFlow().flowOn(Dispatchers.IO).map { key ->
                getItemData(key)
            }.flowOn(Dispatchers.IO).collect {
                datalist.add(it)
                liveData.value = datalist
            }
        }
    }

    private fun getItemData(key: String): RecyclerViewItem {
        return RecyclerViewItem(key, sourceMap[key])
    }


//    fun loadData() {
//        viewModelScope.launch(Dispatchers.Main) {
//            Log.e("sundu", "current thread = " + Thread.currentThread().name)
//            flow<Int> {
//                Log.e("sundu", "flow emit thread = " + Thread.currentThread().name)
//                emit(1)
//                emit(1)
//                emit(1)
//                emit(1)
//            }.flowOn(Dispatchers.IO)
//                .map { request ->
//                    getItemData(request)
//                    Log.e("sundu", "flow map thread = " + Thread.currentThread().name)
//                }.flowOn(Dispatchers.IO)
//                .collect { response ->
//                    Log.e("sundu", "flow collect thread = " + Thread.currentThread().name)
//                    Log.e("sundu", response.toString())
//                }
//        }
//    }


}