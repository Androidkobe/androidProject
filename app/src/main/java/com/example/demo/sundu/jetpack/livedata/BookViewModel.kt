package com.example.demo.sundu.jetpack.livedata

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */

class BookViewModel(application: Application) : AndroidViewModel(application) {

    var bookData : MutableLiveData<Book> = MutableLiveData()

    fun loadData(){
        bookData.value
    }
}