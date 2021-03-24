package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.MockNearbyDevicesApi
import com.example.myapplication.data.NearbyDevicesDataSource

import com.example.myapplication.data.NearbyDevicesDataSource.State
import com.example.myapplication.data.NearbyDevicesRepository
import com.example.myapplication.data.UserData
import com.example.myapplication.presenter.NearbyDevicesViewModel
import com.example.myapplication.presenter.NearbyDevicesViewModelFactory
import com.example.myapplication.ui.components.SwipeToRefreshLayout
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.freelapp.flowlifecycleobserver.collectWhileResumed
import com.freelapp.flowlifecycleobserver.collectWhileResumedIn
import com.freelapp.flowlifecycleobserver.collectWhileStarted
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import timber.log.Timber


class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    LogScreen(this)
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun LogScreen(
    lifecycleOwner: LifecycleOwner
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
    val uiState by produceState<State>(initialValue = State.InProgress, viewModel) {
        viewModel.devices.collectWhileStarted(lifecycleOwner) {
            value = it
        }

        //Anche se cancello la coroutine rimane la sottoscrizione al flow
        /*
        viewModel.devices.collect() {
            value = it
        }*/
    }

    when (uiState) {
        State.InProgress -> Log("In Progress")
        is State.Success -> Log((uiState as State.Success).devices.toString())
        is State.Error -> TODO()
    }

}

@ExperimentalMaterialApi
@Composable
fun Log(name: String) {
    //SwipeToRefreshLayout(false,){
        Text(text = "Hello $name!")
    //}
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Log("Baratta")
    }
}