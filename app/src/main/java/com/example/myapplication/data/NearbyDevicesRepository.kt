package com.example.myapplication.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class NearbyDevicesRepository(
    nearbyDevicesDataSource: NearbyDevicesDataSource,
    private val userData: UserData,
    defaultDispatcher: CoroutineDispatcher
) {
    val nearbyDevices: Flow<NearbyDevicesDataSource.DataState> =
        nearbyDevicesDataSource.nearbyDevices
            .flowOn(defaultDispatcher)

    val friendNearbyDevices = nearbyDevices
        .map { state ->
            when (state) {
                is NearbyDevicesDataSource.DataState.Success ->
                    state.devices.filter { userData.isFriend(it) }
                is NearbyDevicesDataSource.DataState.Error -> TODO()
                NearbyDevicesDataSource.DataState.InProgress -> TODO()
            }
        }
        .flowOn(defaultDispatcher)
}