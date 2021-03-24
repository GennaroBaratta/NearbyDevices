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
    val nearbyDevices: Flow<State> = flow {
        while (true) {
            val nearbyDevices = nearbyDevicesApi.fetchNearbyDevices()
            emit(State.Success(nearbyDevices))
            delay(refreshIntervalMs)
        }
    }
        .flowOn(IO)

    sealed class State {
        object InProgress : State()
        data class Success(val devices: List<NearbyDevice>) : State()
        data class Error(val exception: Exception) : State()
    }
}
