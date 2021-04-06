package com.example.myapplication.ui.deviceList

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.data.MockNearbyDevicesApi
import com.example.myapplication.data.NearbyDevicesDataSource
import com.example.myapplication.data.NearbyDevicesRepository
import com.example.myapplication.data.UserData
import com.example.myapplication.ui.components.DeviceList
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.Dispatchers

@Composable
fun DeviceListScreen() {
    val viewModel: DeviceListViewModel = viewModel(
        factory = DeviceListViewModelFactory(
            NearbyDevicesRepository(
                defaultDispatcher = Dispatchers.Default,
                nearbyDevicesDataSource = NearbyDevicesDataSource(
                    nearbyDevicesApi = MockNearbyDevicesApi()
                ),
                userData = UserData(0L)
            )
        )
    )
    val devices = viewModel.devices.value

    val loading = viewModel.loading.value

    val context= LocalContext.current

    ContextCompat.checkSelfPermission(context,"android.permission.ACCESS_FINE_LOCATION")

    MyApplicationTheme() {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    viewModel.onTriggerEvent(DeviceListEvent.RefreshEvent)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_refresh_24),
                        contentDescription = "Refresh Icon"
                    )
                }
            }
        ) {
            DeviceList(devices, loading)
        }
    }
}

@Preview
@Composable
fun DeviceListScreenPreview() {
    DeviceListScreen()
}