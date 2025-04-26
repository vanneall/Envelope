package com.point.envelope

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message

import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.point.envelope.navigation.navhost.ComposeNavigationRoute

sealed class BottomBarEntryPoint(
    @StringRes
    val textId: Int,
    val composeNavigationRoute: ComposeNavigationRoute,
    private val selectedIcon: ImageVector,
    private val unselectedIcon: ImageVector,
) {

    fun icon(selected: Boolean) = if (selected) selectedIcon else unselectedIcon

    data object AllChats : BottomBarEntryPoint(
        textId = R.string.chats_screen,
        selectedIcon = Icons.AutoMirrored.Filled.Message,
        unselectedIcon = Icons.AutoMirrored.Default.Message,
        composeNavigationRoute = ComposeNavigationRoute.EntryRoute.Chats,
    )

    data object Contacts : BottomBarEntryPoint(
        textId = R.string.contacts_screen,
        selectedIcon = Icons.Filled.SupervisedUserCircle,
        unselectedIcon = Icons.Default.SupervisedUserCircle,
        composeNavigationRoute = ComposeNavigationRoute.EntryRoute.Contacts,
    )

    data object Settings : BottomBarEntryPoint(
        textId = R.string.settings_screen,
        selectedIcon = Icons.Default.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        composeNavigationRoute = ComposeNavigationRoute.EntryRoute.Settings,
    )
}

val entryPoints = listOf(
    BottomBarEntryPoint.AllChats,
    BottomBarEntryPoint.Contacts,
    BottomBarEntryPoint.Settings,
)