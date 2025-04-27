package com.point.settings.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.point.settings.model.AppSettings
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

internal class AppSettingsRepositoryImpl(private val sharedPrefs: SharedPreferences) : AppSettingsRepository {

    override fun getSettings() = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, _ ->
            trySend(settings)
        }

        sharedPrefs.registerOnSharedPreferenceChangeListener(listener)
        send(settings)

        awaitClose { sharedPrefs.unregisterOnSharedPreferenceChangeListener(listener) }
    }

    private val settings: AppSettings
        get() = AppSettings(
            useAnimations = sharedPrefs.getBoolean(KEY_USE_ANIMATIONS, true)
        )

    override suspend fun changeAnimationUsage(use: Boolean) {
        sharedPrefs.edit { putBoolean(KEY_USE_ANIMATIONS, use) }
    }

    companion object {
        const val SHARED_PREFS_NAME = "app_settings_prefs"

        private const val KEY_USE_ANIMATIONS = "use_animations"
    }
}