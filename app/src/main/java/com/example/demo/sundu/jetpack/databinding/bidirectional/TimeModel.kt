package com.example.demo.sundu.jetpack.databinding.bidirectional

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField


class TimeModel : BaseObservable() {
    var timeObservable = ObservableField<String>()
}