package com.example.myapplication.data

import kotlinx.coroutines.delay
import timber.log.Timber

class MockNearbyDevicesApi : NearbyDevicesApi {
    private val list: List<NearbyDevice> = (1..100).map {
        NearbyDevice(it.toLong())
    }

    override suspend fun fetchNearbyDevices(): List<NearbyDevice> {
        //delay(3000)
        Timber.d("New devices fetched!")
        return list.shuffled()
    }

    fun fetchNearbyDevicesNotSus(): List<NearbyDevice> {
        return list.shuffled()
    }
}