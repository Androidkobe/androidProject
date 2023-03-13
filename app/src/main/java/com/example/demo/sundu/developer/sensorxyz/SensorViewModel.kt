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
            object : SenSorGyrDegreesHelper.SenSorTwistListener {
                override fun twistSuccess(orientationType: Int, orientation: Int) {
                }

            },//x 轴 35度
            3, 35
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