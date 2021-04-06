package com.example.myapplication.data

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class NearbyDevicesDataSource(
    private val nearbyDevicesApi: NearbyDevicesApi,
    private val refreshIntervalMs: Long = 6000
) {
    val nearbyDevices: Flow<DataState> = flow {
        //while (true) {
            val nearbyDevices = nearbyDevicesApi.fetchNearbyDevices()
            emit(DataState.Success(nearbyDevices))
            delay(refreshIntervalMs)
        //}
    }
        .flowOn(IO)


    sealed class DataState {
        object InProgress : DataState()
        data class Success(val devices: List<NearbyDevice>) : DataState()
        data class Error(val exception: Exception) : DataState()
    }
}
