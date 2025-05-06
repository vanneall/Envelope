package com.example.settings.main.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.BatterySaver
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Accessibility
import androidx.lifecycle.viewModelScope
import com.example.settings.R
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
import com.point.user.storage.UserStorage
import com.point.viewmodel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.point.user.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userStorage: UserStorage,
    private val contactsRepository: UserRepository,
) : MviViewModel<MainSettingsState, SettingsAction, SettingsEvent>(
    initialValue = MainSettingsState(),
) {

    init {
        viewModelScope.launch {
            contactsRepository.fetchUserDetailedInfo().fold(
                onSuccess = {
                    emitAction(SettingsAction.Event.UserDataFetched(it))
                },
                onFailure = { it.printStackTrace() }
            )
        }
        onLeftFromAccount()
        handleOnRefresh()
    }

    override fun reduce(action: SettingsAction, state: MainSettingsState) = when(action) {
        SettingsAction.Action.Refresh -> state.copy(isRefreshing = true)

        is SettingsAction.Event.UserDataFetched -> state.copy(
            isRefreshing = false,
            userData = UserData(
                name = action.data.name,
                username = "@${action.data.username}",
                requests = 0,
                contacts = 0,
                photoId = action.data.photos.firstOrNull()
            ),
            settings = getState(action.data.username).settings
        )
        SettingsAction.Action.LeftFromAccount -> state

    }

    private fun handleOnRefresh() {
        handleAction<SettingsAction.Action.Refresh> {
            contactsRepository.fetchUserDetailedInfo().fold(
                onSuccess = {
                    emitAction(SettingsAction.Event.UserDataFetched(it))
                },
                onFailure = { it.printStackTrace() }
            )
        }
    }

    private fun onLeftFromAccount() {
        handleAction<SettingsAction.Action.LeftFromAccount> {
            userStorage.updateToken(null)
            userStorage.setUser(null)
            emitEvent(SettingsEvent.LeftFromAccount)
        }
    }
}

val settings = listOf(
    AppSettings(
        textId = R.string.theme_settings,
        icon = Icons.Default.Palette,
        iconColor = PurpleContent,
        iconBackground = PurpleContainer,
        route = Route.SettingsFeature.Appearance,
    ),
    AppSettings(
        textId = R.string.accessibility_settings,
        icon = Icons.Rounded.Accessibility,
        iconColor = GrayContentLight,
        iconBackground = GrayContainerLight,
        route = Route.SettingsFeature.Accessibility,
    ),
    AppSettings(
        textId = R.string.battery_settings,
        icon = Icons.Default.BatterySaver,
        iconColor = CyanContent,
        iconBackground = CyanContainer,
        route = Route.SettingsFeature.Battery,
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

fun getUserSettings(username: String) = listOf(
    UserSettings(
        textId = R.string.edit_profile_settings,
        icon = Icons.Default.Edit,
        iconColor = BlueContentLight,
        iconBackground = BlueContainerLight,
        count = 0,
        route = Route.ContactsFeature.UserProfile(username),
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

fun getState(username: String) = MainSettingsState(
    isRefreshing = false,
    userData = UserData(),
    settings = listOf(
        SettingsSection(
            settings = getUserSettings(username),
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