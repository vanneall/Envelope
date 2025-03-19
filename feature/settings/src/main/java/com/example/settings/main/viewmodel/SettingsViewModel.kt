package com.example.settings.main.viewmodel

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.BatterySaver
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material.icons.filled.DesignServices
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Palette
import androidx.compose.ui.graphics.Color
import com.example.settings.R
import com.example.settings.main.ui.DEFAULT_IMAGE_URL
import com.point.viewmodel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(): MviViewModel<MainSettingsState, SettingsAction, Any>(
    initialValue = state
) {

    override fun reduce(action: SettingsAction, state: MainSettingsState): MainSettingsState =
        when (action) {
            SettingsAction.Refresh -> state.copy(isLoading = true)
        }
    
}

val userData = UserData(
    name = "Daniil",
    username = "@skylejke",
    url = DEFAULT_IMAGE_URL,
)

val settings = listOf(
    AppSettings(
        textId = R.string.storage_settings,
        icon = Icons.Default.DataUsage,
        iconBackground = Color.Blue,
    ),
    AppSettings(
        textId = R.string.battery_settings,
        icon = Icons.Default.BatterySaver,
        iconBackground = Color.Green,
    ),
    AppSettings(
        textId = R.string.theme_settings,
        icon = Icons.Default.Palette,
        iconBackground = Color.Red,
    ),
)

val exitSettings = listOf(
    ExitSettings(
        textId = R.string.exit_settings,
        icon = Icons.AutoMirrored.Filled.Logout,
        iconBackground = Color.Red,
    )
)

val userSettings = listOf(
    UserSettings(
        textId = R.string.edit_profile_settings,
        icon = Icons.Default.Edit,
        iconBackground = Color.Black,
        count = 0,
    ),
    UserSettings(
        textId = R.string.requests_settings,
        icon = Icons.Default.Inbox,
        iconBackground = Color.Gray,
        count = 15,
    ),
    UserSettings(
        textId = R.string.contacts_settings,
        icon = Icons.Default.Contacts,
        iconBackground = Color.Magenta,
        count = 228,
    )
)

val state = MainSettingsState(
    isLoading = false,
    userData = userData,
    settings = listOf(
        SettingsSection(
            settings = userSettings,
            type = Settings.Type.USER,
        ),
        SettingsSection(
            settings = settings,
            type = Settings.Type.APP,
        ),
        SettingsSection(
            settings = exitSettings,
            type = Settings.Type.ACTION,
        )
    )
)