package com.example.myapplication.data

import kotlinx.coroutines.delay
import timber.log.Timber

class MockNearbyDevicesApi : NearbyDevicesApi {
    val list: List<NearbyDevice> = listOf(
        NearbyDevice(0L), NearbyDevice(1L), NearbyDevice(3L),
        NearbyDevice(4L)
    )

    override suspend fun fetchNearbyDevices(): List<NearbyDevice> {
        //delay(3000)
        Timber.d("New devices fetched!")
        return list.shuffled()
    }
}