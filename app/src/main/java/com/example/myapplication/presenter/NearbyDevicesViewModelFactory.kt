package com.example.myapplication.presenter

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.NearbyDevicesRepository
import kotlinx.coroutines.CoroutineScope


class NearbyDevicesViewModelFactory(
    private val nearbyDevicesRepository: NearbyDevicesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NearbyDevicesViewModel::class.java)) {
            return NearbyDevicesViewModel(nearbyDevicesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}