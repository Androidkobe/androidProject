package com.example.kotlinlearn

fun main() {

}

//data class User2(val name: String, val age: Int){
//    //复制
//    fun copy(name: String = this.name, age: Int = this.age): User2 = User2(name, age)
//}

data class User1(val name: String = "", val age: Int = 0) {


}


fun fun1() {
    val person1 = Person("John")
    val person2 = Person("John")
    person1.age = 10
    person2.age = 20
}

//在类体中声明的属性
data class Person(val name: String) {
    var age: Int = 0
}
