package com.example.settings.accessibility

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.point.settings.model.LetterSpacingPresetSettings
import com.point.settings.repository.AppSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccessibilityViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
) : ViewModel() {

    private val _selectedPreset = MutableStateFlow(LetterSpacingPresetSettings.NORMAL)
    val selectedPreset: StateFlow<LetterSpacingPresetSettings> = _selectedPreset.asStateFlow()

    init {
        viewModelScope.launch {
            appSettingsRepository.getSettings()
                .map { it.letterSpacing }
                .collect { _selectedPreset.value = it }
        }
    }

    fun onPresetSelected(preset: LetterSpacingPresetSettings) {
        _selectedPreset.value = preset
        viewModelScope.launch {
            appSettingsRepository.setLetterSpacingPreset(preset)
        }
    }
}
