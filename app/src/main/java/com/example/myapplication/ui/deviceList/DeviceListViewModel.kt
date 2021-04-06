package com.example.myapplication.ui.deviceList

import android.Manifest
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.NearbyDevice
import com.example.myapplication.data.NearbyDevicesDataSource
import com.example.myapplication.data.NearbyDevicesRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import timber.log.Timber

class DeviceListViewModel(private val nearbyDevicesRepository: NearbyDevicesRepository) :
    ViewModel() {
    private val _devices: MutableState<List<NearbyDevice>> = mutableStateOf(emptyList())
    val devices: State<List<NearbyDevice>> = _devices

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    init {
        onTriggerEvent(DeviceListEvent.RefreshEvent)
    }

    fun onTriggerEvent(event: DeviceListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is DeviceListEvent.RefreshEvent -> {
                        withTimeout(15000) {
                            refresh()
                        }
                    }
                }
            } catch (e: Exception) {
                Timber.e("DeviceListViewModel launchJob: Exception: ${e}, ${e.cause}")
            } finally {
                Timber.d("DeviceListViewModel launchJob: finally called")
            }
        }
    }


    private fun refresh() {
        nearbyDevicesRepository.nearbyDevices
            .onEach { dataState ->
                _loading.value = dataState is NearbyDevicesDataSource.DataState.InProgress

                when (dataState) {
                    is NearbyDevicesDataSource.DataState.Success -> _devices.value =
                        dataState.devices
                    is NearbyDevicesDataSource.DataState.Error -> TODO()
                    NearbyDevicesDataSource.DataState.InProgress -> TODO()
                }
            }
            .catch { e ->
                Timber.e("DeviceListViewModel refresh: Exception: ${e}, ${e.cause}")
            }
            .launchIn(viewModelScope)
    }
}