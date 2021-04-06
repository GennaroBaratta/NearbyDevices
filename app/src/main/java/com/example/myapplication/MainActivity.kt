package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.core.content.ContextCompat
import com.example.myapplication.ui.deviceList.DeviceListScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import timber.log.Timber


class MainActivity : ComponentActivity() {
    // Register the permissions callback, which handles the user's response to the
    // system permissions dialog. Save the return value, an instance of
    // ActivityResultLauncher. You can use either a val, as shown in this snippet,
    // or a lateinit var in your onAttach() or onCreate() method.
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
                setContent {
                    MyApplicationTheme {
                        // A surface container using the 'background' color from the theme
                        Surface(color = MaterialTheme.colors.background) {
                            DeviceListScreen()
                        }
                    }
                }
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.

            }
        }

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED) {
                true ->
                    setContent {
                        MyApplicationTheme {
                            // A surface container using the 'background' color from the theme
                            Surface(color = MaterialTheme.colors.background) {
                                DeviceListScreen()
                            }
                        }
                    }
                else -> {
                    Timber.d("Permission denied")
                    shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
        }
    }
}

/*
@ExperimentalMaterialApi
@Composable
fun LogScreen(
) {
    val viewModel: NearbyDevicesViewModel = viewModel(
        factory = NearbyDevicesViewModelFactory(
            NearbyDevicesRepository(
                defaultDispatcher = Dispatchers.Default,
                nearbyDevicesDataSource = NearbyDevicesDataSource(
                    nearbyDevicesApi = MockNearbyDevicesApi()
                ),
                userData = UserData(0L)
            )
        )
    )
    /*
    val uiState by produceState<DataState>(initialValue = DataState.InProgress, viewModel) {
        viewModel.devices.collectWhileStarted(lifecycleOwner) {
            value = it
        }

        //Anche se cancello la coroutine rimane la sottoscrizione al flow
        /*
        viewModel.devices.collect() {
            value = it
        }*/
    }*/
    //val uiState = viewModel.state.collectAsState()
/*
    val (devicesUiState, refreshDevices, clearError) = produceUiState(viewModel) {

    }
*/
    val lifecycleOwner = LocalLifecycleOwner.current
    val stateFlowLifecycleAware = remember(viewModel.devices, lifecycleOwner) {
        viewModel.devices.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }
    val uiState by stateFlowLifecycleAware.collectAsState(DataState.InProgress)
    when (uiState) {
        DataState.InProgress -> Log("In Progress", true) {
            viewModel.observeDevices()
        }
        is DataState.Success -> Log((uiState as DataState.Success).devices.toString(), false) {
            viewModel.observeDevices()
        }
        is DataState.Error -> TODO()
    }

}

@ExperimentalMaterialApi
@Composable
fun Log(name: String, loading: Boolean, onRefresh: () -> Unit) {

    Box(Modifier.fillMaxSize()) {
        Text(text = "Hello $name!")
    }

}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Log("Baratta", true) {}
    }
}

 */
