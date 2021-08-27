package com.example.demo.sundu.jetpack.livedata

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */

class BookViewModel(application: Application) : AndroidViewModel(application) {

    var bookData : MutableLiveData<Book> = MutableLiveData()

    fun loadData() {
        viewModelScope.launch {

        }
        bookData.value
    }
}