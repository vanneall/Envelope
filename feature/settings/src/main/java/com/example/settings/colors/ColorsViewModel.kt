package com.example.settings.colors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.point.color.AppColorColor
import com.point.settings.model.AppColor
import com.point.settings.repository.AppSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ColorsViewModel @Inject constructor(private val appSettingsRepository: AppSettingsRepository) : ViewModel() {
    private val _selectedColor = MutableStateFlow(AppColorColor.BLUE)
    val selectedColor: StateFlow<AppColorColor> = _selectedColor.asStateFlow()

    init {
        viewModelScope.launch {
            appSettingsRepository.getSettings().collect {
                _selectedColor.value = map(it.selectedColor)
            }
        }
    }

    fun onColorSelected(color: AppColorColor) {
        _selectedColor.value = color
        viewModelScope.launch {
            appSettingsRepository.setSelectedColor(map(color))
        }
    }

    fun map(cl: AppColor) = when (cl) {
        AppColor.RED -> AppColorColor.RED
        AppColor.BLUE -> AppColorColor.BLUE
        AppColor.GREEN -> AppColorColor.GREEN
        AppColor.BROWN -> AppColorColor.BROWN
        AppColor.PURPLE -> AppColorColor.PURPLE
    }

    fun map(cl: AppColorColor) = when (cl) {
        AppColorColor.RED -> AppColor.RED
        AppColorColor.BLUE -> AppColor.BLUE
        AppColorColor.GREEN -> AppColor.GREEN
        AppColorColor.BROWN -> AppColor.BROWN
        AppColorColor.PURPLE -> AppColor.PURPLE
    }
}