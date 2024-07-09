package com.example.kotlinlearn

fun main() {
    var message = Message()
    var status = Message.Status()
    status.short = SUCCESS
    message.status = status
    println(message.success())

}

class Message{
    var status:Status? = null
    fun success():Boolean = status?.short == CODE
    class Status{
        var short:Short? = null
    }
}



var SUCCESS :Short =  10
var CODE :Short = 10