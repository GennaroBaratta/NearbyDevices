package com.example.myapplication.ui.deviceList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.NearbyDevicesRepository

class DeviceListViewModelFactory(
    private val nearbyDevicesRepository: NearbyDevicesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeviceListViewModel::class.java)) {
            return DeviceListViewModel(nearbyDevicesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}