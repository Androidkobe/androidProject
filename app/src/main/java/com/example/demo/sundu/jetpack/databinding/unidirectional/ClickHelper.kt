package com.example.demo.sundu.jetpack.databinding.unidirectional

class ClickHelper() {

    fun onChangeName(userObserableUser: ObserableUser) {
        userObserableUser.name += userObserableUser.age
        userObserableUser.price++
    }

    fun onChangeAge(userObserableUser: ObserableUser) {
        userObserableUser.age++
        userObserableUser.price++
    }

}
