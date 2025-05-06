package com.point.settings.repository

import com.point.settings.model.AppColor
import com.point.settings.model.AppSettings
import com.point.settings.model.LetterSpacingPresetSettings
import kotlinx.coroutines.flow.Flow

interface AppSettingsRepository {

    fun getSettings(): Flow<AppSettings>

    suspend fun changeAnimationUsage(use: Boolean)

    suspend fun setBatteryThreshold(percent: Int)

    suspend fun setSelectedColor(color: AppColor)

    suspend fun setLetterSpacingPreset(preset: LetterSpacingPresetSettings)
}