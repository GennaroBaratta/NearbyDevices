package com.example.myapplication.ui.deviceList

sealed class DeviceListEvent {
    object RefreshEvent : DeviceListEvent()
}