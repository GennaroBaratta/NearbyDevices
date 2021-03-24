package com.example.myapplication.data

interface NearbyDevicesApi {
    suspend fun fetchNearbyDevices(): List<NearbyDevice>
}
