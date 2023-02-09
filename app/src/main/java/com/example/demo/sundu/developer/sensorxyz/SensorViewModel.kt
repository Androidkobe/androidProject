package com.example.demo.sundu.developer.sensorxyz

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class SensorViewModel(application: Application) : AndroidViewModel(application) {

    var senSorDataLiveData: MutableLiveData<SenSorData> = MutableLiveData()

    fun createData() {
        var senSorData = SenSorData()
        senSorData.x = "24"
        senSorData.y = "26"
        senSorData.z = "28"
        senSorDataLiveData?.value = senSorData
        SenSorGyrDegreesHelper().registerListener(
            getApplication(),
            object : SenSorGyrDegreesHelper.SensorRotateListener {
                override fun rotate(left: Boolean) {
                }

            },
            this
        )
    }

    fun sendData(currentDegreeX: Int, currentDegreeY: Int, currentDegreeZ: Int) {
        var senSorData = senSorDataLiveData.value
        senSorData?.x = currentDegreeX.toString()
        senSorData?.y = currentDegreeY.toString()
        senSorData?.z = currentDegreeZ.toString()
        senSorDataLiveData.value = senSorData
    }
}