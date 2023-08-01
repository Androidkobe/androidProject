package com.example.demo.sundu.custview.cropimage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class AngleViewModel : ViewModel() {
    var angleData = MutableLiveData<Float>()

    fun startAction() {
        viewModelScope.launch {
            var process = 0f
            var rollback = false
            flow<Float> {
                while (true) {
                    delay(10)
                    if (!rollback) {
                        process += 0.01f
                    } else {
                        process -= 0.01f
                    }
                    if (process < 0f) {
                        rollback = false
                    }
                    if (process > 1f) {
                        rollback = true
                    }
                    emit(process)
                }
            }.flowOn(Dispatchers.Default)
                .collect { result ->
                    angleData.value = result
                }
        }
    }

}