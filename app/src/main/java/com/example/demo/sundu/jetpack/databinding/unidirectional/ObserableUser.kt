package com.example.demo.sundu.jetpack.databinding.unidirectional

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.demo.BR

class ObserableUser : BaseObservable() {

    @get:Bindable
    var name = ""
        set(value) {
            field = value
            // 只刷新当前属性
            notifyPropertyChanged(BR.name)
        }

    var price = 0f

    @get:Bindable
    var age = 0
        set(value) {
            field = value
            // 更新所有字段
            notifyChange()
        }
}
