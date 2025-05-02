package com.point.chats.camera

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CameraViewModel : ViewModel() {
    private val _state = mutableStateOf(CameraUiState())
    val state: State<CameraUiState> = _state

    fun toggleFlash() {
        _state.value = _state.value.copy(isFlashEnabled = !_state.value.isFlashEnabled)
    }

    fun toggleAspectRatio() {
        _state.value = _state.value.copy(
            aspectRatio = if (_state.value.aspectRatio == AspectRatioOption.RATIO_16_9)
                AspectRatioOption.RATIO_4_3
            else
                AspectRatioOption.RATIO_16_9
        )
    }

    fun toggleGrid() {
        _state.value = _state.value.copy(isGridEnabled = !_state.value.isGridEnabled)
    }
}

