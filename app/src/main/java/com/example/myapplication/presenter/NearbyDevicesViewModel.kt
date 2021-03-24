package com.example.myapplication.presenter

import androidx.lifecycle.*
import com.example.myapplication.data.NearbyDevicesRepository

class NearbyDevicesViewModel(
    private val nearbyDevicesRepository: NearbyDevicesRepository,
) : ViewModel() {
    val devices get() = nearbyDevicesRepository.nearbyDevices
}