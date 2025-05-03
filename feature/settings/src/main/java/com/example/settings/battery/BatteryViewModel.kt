package com.example.settings.battery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.point.settings.repository.AppSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BatteryViewModel @Inject constructor(
    private val repository: AppSettingsRepository
) : ViewModel() {

    private val _useAnimations = MutableStateFlow(true)
    val useAnimations: StateFlow<Boolean> = _useAnimations.asStateFlow()

    private val _batteryThreshold = MutableStateFlow(50)
    val batteryThreshold: StateFlow<Int> = _batteryThreshold.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getSettings()
                .distinctUntilChanged()
                .collect {
                    _useAnimations.value = it.useAnimations
                    _batteryThreshold.value = it.batteryThreshold
                }
        }
    }

    fun onAnimationToggled(enabled: Boolean) {
        _useAnimations.value = enabled
        viewModelScope.launch {
            repository.changeAnimationUsage(enabled)
        }
    }

    fun onBatteryThresholdChanged(percent: Int) {
        _batteryThreshold.value = percent
        viewModelScope.launch {
            repository.setBatteryThreshold(percent)
        }
    }
}
