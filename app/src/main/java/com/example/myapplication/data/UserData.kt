package com.example.myapplication.data

data class UserData(val id: Long) {
    fun isFriend(device: NearbyDevice): Boolean {
        return device.id.toInt() % 2 == 0
    }
}
