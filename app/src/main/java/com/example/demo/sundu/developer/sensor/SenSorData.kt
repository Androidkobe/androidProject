package com.example.demo.sundu.developer.sensor

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class SenSorData : BaseObservable() {
    @get:Bindable
    var x: String? = "14"
        set(value) {
            field = value
            notifyChange()
//            Log.e("sundu", "set sen sro x = $x")
        }

    @get:Bindable
    var y: String? = "14"
        set(value) {
            field = value
            notifyChange()
//            Log.e("sundu", "set sen sro y = $y")
        }

    @get:Bindable
    var z: String? = "14"
        set(value) {
            field = value
            notifyChange()
//            Log.e("sundu", "set sen sor z = $z")
        }

}