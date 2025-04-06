package com.example.settings.main.viewmodel

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.point.navigation.Route

data class MainSettingsState(
    val isRefreshing: Boolean,
    val userData: UserData,
    val settings: List<SettingsSection>,
)

data class UserData(
    val name: String = "",
    val username: String = "",
    val requests: Int = 0,
    val contacts: Int = 0,
    val photoId: String? = null,
)

@Immutable
data class SettingsSection(val settings: List<Settings>, val type: Settings.Type)

data class UserSettings(
    @StringRes
    override val textId: Int,
    override val icon: ImageVector,
    override val iconColor: Color,
    override val iconBackground: Color,
    val count: Int,
    val route: Route? = null,
): Settings

data class AppSettings(
    @StringRes
    override val textId: Int,
    override val icon: ImageVector,
    override val iconColor: Color,
    override val iconBackground: Color,
) : Settings

data class ExitSettings(
    @StringRes
    override val textId: Int,
    override val icon: ImageVector,
    override val iconColor: Color,
    override val iconBackground: Color,
): Settings

@Immutable
interface Settings {
    val textId: Int
    val icon: ImageVector
    val iconColor: Color
    val iconBackground: Color
    
    enum class Type {
        APP,
        USER,
        ACTION,
        ;
    }
}