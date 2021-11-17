package com.example.demo.sundu.developer.recycleview

data class DevelopItemData(var title: String, var url: String) {

    var id = 0

    init {
        title += " - sundu"
    }
}
