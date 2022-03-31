package com.example.demo.sundu.recycleview.customlayoutone

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomLayoutOneViewModel : ViewModel() {

    var listData = mutableListOf<CustomLayoutOneData>()

    var liveData = MutableLiveData<List<CustomLayoutOneData>>()

    fun loadData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                for (i: Int in 1..100) {
                    listData.add(CustomLayoutOneData("第 $i 项"))
                }
            }
            liveData.postValue(listData)
        }
    }
}