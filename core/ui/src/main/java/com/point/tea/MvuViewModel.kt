package com.point.tea

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.point.tea.LoadableState.Loading
import com.point.tea.LoadableState.StateHolder.Success
import com.point.tea.commands.CommandHandler
import com.point.tea.events.EmptyEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class MvuViewModel<UiState, Event, Effect, Dependency, Command : CommandHandler<Event, Dependency, Effect>>(
    private val dependency: Dependency,
    initialState: LoadableState<UiState> = Loading<UiState, Success<UiState>>()
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<LoadableState<UiState>> = _state

    private val _events = MutableSharedFlow<Event>(replay = 10, extraBufferCapacity = 10)

    private val _effects = MutableSharedFlow<Effect>(replay = 10, extraBufferCapacity = 10)
    val effects: SharedFlow<Effect> = _effects

    init {
        viewModelScope.launch {
            _events.collect { startEvent ->
                launch {
                    var event = startEvent
                    do {
                        val (newState, command) = reduce(event = event, state = state.value)
                        event = command.handle(dependency = dependency) { _effects.tryEmit(it) }
                        _state.update { newState }
                    } while (event !is EmptyEvent)
                }
            }
        }
    }

    protected abstract fun reduce(event: Event, state: LoadableState<UiState>): Pair<LoadableState<UiState>, Command>

    fun emitEvent(event: Event): Boolean = _events.tryEmit(event)
}
