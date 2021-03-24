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
    val nearbyDevices: Flow<NearbyDevicesDataSource.State> =
        nearbyDevicesDataSource.nearbyDevices
            .flowOn(defaultDispatcher)

    val friendNearbyDevices = nearbyDevices
        .map { state ->
            when (state) {
                is NearbyDevicesDataSource.State.Success ->
                    state.devices.filter { userData.isFriend(it) }
                is NearbyDevicesDataSource.State.Error -> TODO()
                NearbyDevicesDataSource.State.InProgress -> TODO()
            }
        }
        .flowOn(defaultDispatcher)
}