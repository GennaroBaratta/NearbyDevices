package com.example.myapplication

import com.example.myapplication.utils.IntentViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

abstract class StatefulIntentViewModel<Intent, State>(
    val initialState: State
) : IntentViewModel<Intent>() {
    private var state = initialState

    @ExperimentalCoroutinesApi
    private val statesBroadcast = BroadcastChannel<State>(1)

    private val stateMutex = Mutex()

    @ExperimentalCoroutinesApi
    @FlowPreview
    val states = statesBroadcast.asFlow()

    @ExperimentalCoroutinesApi
    protected suspend fun setState(reducer: State.() -> State) =
        stateMutex.withLock {
            state = state.reducer()
            statesBroadcast.send(state)
        }

    @ExperimentalCoroutinesApi
    protected suspend fun withState(action: (State).() -> Unit) =
        setState {
            this.apply(action)
        }
}