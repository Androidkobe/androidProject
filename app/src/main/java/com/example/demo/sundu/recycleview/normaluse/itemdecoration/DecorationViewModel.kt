package com.example.demo.sundu.recycleview.normaluse.itemdecoration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DecorationViewModel: ViewModel() {

    var listData = mutableListOf<DecorationData>()

    var liveData = MutableLiveData<List<DecorationData>>()

    fun loadData(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                for (i:Int in 1..100){
                    listData.add(DecorationData("第 $i 项"))
                }
            }
            liveData.postValue(listData)
        }
    }
}