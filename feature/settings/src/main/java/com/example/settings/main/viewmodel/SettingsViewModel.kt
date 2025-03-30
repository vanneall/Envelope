package com.example.settings.main.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.BatterySaver
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import com.example.settings.R
import com.example.settings.main.ui.DEFAULT_IMAGE_URL
import com.point.navigation.Route
import com.point.ui.colors.BlueContainerLight
import com.point.ui.colors.BlueContentLight
import com.point.ui.colors.CyanContainer
import com.point.ui.colors.CyanContent
import com.point.ui.colors.GrayContainerLight
import com.point.ui.colors.GrayContentLight
import com.point.ui.colors.GreenContainer
import com.point.ui.colors.GreenContent
import com.point.ui.colors.OrangeContainer
import com.point.ui.colors.OrangeContent
import com.point.ui.colors.PurpleContainer
import com.point.ui.colors.PurpleContent
import com.point.ui.colors.RedContainer
import com.point.ui.colors.RedContent
import com.point.viewmodel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() :
    MviViewModel<MainSettingsState, SettingsAction, Any>(
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
        textId = R.string.theme_settings,
        icon = Icons.Default.Palette,
        iconColor = PurpleContent,
        iconBackground = PurpleContainer,
    ),
    AppSettings(
        textId = R.string.storage_settings,
        icon = Icons.Default.DataUsage,
        iconColor = GrayContentLight,
        iconBackground = GrayContainerLight,
    ),
    AppSettings(
        textId = R.string.battery_settings,
        icon = Icons.Default.BatterySaver,
        iconColor = CyanContent,
        iconBackground = CyanContainer,
    ),
)

val exitSettings = listOf(
    ExitSettings(
        textId = R.string.exit_settings,
        icon = Icons.AutoMirrored.Filled.Logout,
        iconBackground = RedContainer,
        iconColor = RedContent,
    )
)

val userSettings = listOf(
    UserSettings(
        textId = R.string.edit_profile_settings,
        icon = Icons.Default.Edit,
        iconColor = BlueContentLight,
        iconBackground = BlueContainerLight,
        count = 0,
        route = Route.SettingsFeature.ProfileEdit,
    ),
    UserSettings(
        textId = R.string.requests_settings,
        icon = Icons.Default.Notifications,
        iconColor = OrangeContent,
        iconBackground = OrangeContainer,
        count = 15,
        route = Route.ContactsFeature.ContactsRequests,
    ),
    UserSettings(
        textId = R.string.contacts_settings,
        icon = Icons.Default.Person,
        iconColor = GreenContent,
        iconBackground = GreenContainer,
        count = 228,
        route = Route.ContactsFeature.UserContacts,
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