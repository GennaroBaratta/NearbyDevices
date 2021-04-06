package com.example.myapplication.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.MockNearbyDevicesApi
import com.example.myapplication.data.NearbyDevice

@Composable
fun DeviceList(
    devices: List<NearbyDevice>,
    loading: Boolean
) {
    if (loading && devices.isEmpty()) {
        LoadingDeviceListShimmer(imageHeight = 250.dp)
    } else
        LazyColumn {
            items(devices) { device ->
                DeviceCard(device)
            }
        }
}

@Preview
@Composable
fun DeviceListPreview() {
    DeviceList(devices = MockNearbyDevicesApi().fetchNearbyDevicesNotSus(), false)
}

@Preview
@Composable
fun DeviceListLoadingPreview() {
    DeviceList(devices = emptyList(), true)
}