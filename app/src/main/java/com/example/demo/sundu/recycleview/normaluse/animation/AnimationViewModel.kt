package com.example.demo.sundu.recycleview.normaluse.animation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AnimationViewModel: ViewModel() {

    var listData = mutableListOf<AnimationData>()

    var liveData = MutableLiveData<List<AnimationData>>()

    fun loadData(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                for (i:Int in 1..4){
                    listData.add(AnimationData("第 $i 项"))
                }
            }
            liveData.postValue(listData)
        }
    }

    fun  addData(){
        viewModelScope.launch {
            listData.add(AnimationData("第 ${listData.size+1} 项"))
        }
    }
    fun  remove(){
        viewModelScope.launch {
            listData.removeAt(listData.size-1)
        }
    }
}