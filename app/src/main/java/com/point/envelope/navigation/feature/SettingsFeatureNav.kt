package com.point.envelope.navigation.feature

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.example.settings.main.ui.MainSettingsScreen
import com.example.settings.main.viewmodel.SettingsViewModel
import com.point.chats.R
import com.point.envelope.BottomBarState
import com.point.envelope.TopAppBarState2
import com.point.envelope.navigation.navhost.ComposeNavigationRoute.EntryRoute
import com.point.envelope.navigation.extensions.entryComposable

internal fun NavGraphBuilder.settingsFeature(
    navController: NavController,
    topAppBarState: MutableState<TopAppBarState2>,
    bottomBarState: MutableState<BottomBarState>,
) {
    entryComposable<EntryRoute.Settings> {
        bottomBarState.value = BottomBarState(true)
        topAppBarState.value =
            TopAppBarState2(text = stringResource(R.string.settings_screen_title))

        val viewModel = hiltViewModel<SettingsViewModel>()

        MainSettingsScreen(
            state = viewModel.composableState.value,
            onAction = viewModel::emitAction,
            modifier = Modifier.fillMaxSize(),
        )
    }
}