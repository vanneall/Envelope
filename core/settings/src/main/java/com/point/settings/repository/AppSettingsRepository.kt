package com.point.settings.repository

import com.point.settings.model.AppSettings
import kotlinx.coroutines.flow.Flow

interface AppSettingsRepository {

    fun getSettings(): Flow<AppSettings>

    suspend fun changeAnimationUsage(use: Boolean)
}