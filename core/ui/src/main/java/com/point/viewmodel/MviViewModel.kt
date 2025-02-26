package com.point.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class MviViewModel<UiState, Action, Event>(initialValue: UiState) : ViewModel() {

    private val _state = MutableStateFlow(initialValue)

    val state: UiState
        get() = _state.value

    private val _actions = MutableSharedFlow<Action>()

    val actions: SharedFlow<Action>
        get() = _actions.asSharedFlow()

    private val _events = MutableSharedFlow<Event>()

    val events: SharedFlow<Event>
        get() = _events.asSharedFlow()

    val composableState: State<UiState>
        @Composable
        get() = _state.collectAsState()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            launch {
                _actions.collect { action ->
                    _state.update { reduce(action, state) }
                }
            }
        }
    }

    fun emitAction(action: Action) {
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    fun emitEvent(event: Event) {
        viewModelScope.launch {
            _events.emit(event)
        }
    }

    protected fun reduce(action: Action, state: UiState): UiState = state

    inline fun <reified T : Action> mapAction(crossinline block: suspend (T) -> Action) {
        viewModelScope.launch {
            actions.filterIsInstance<T>()
                .map { block(it) }
                .collect { emitAction(it) }
        }
    }

    inline fun <reified T : Action> handleAction(crossinline block: suspend (T) -> Unit) {
        viewModelScope.launch {
            actions.filterIsInstance<T>()
                .collect {
                    block(it)
                }
        }
    }
}
