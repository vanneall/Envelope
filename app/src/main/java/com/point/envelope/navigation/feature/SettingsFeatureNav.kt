package com.point.envelope.navigation.feature

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.example.settings.main.ui.MainSettingsScreen
import com.example.settings.main.viewmodel.SettingsViewModel
import com.example.settings.profile.ui.ProfileEditScreen
import com.example.settings.profile.viewmodel.ProfileEditAction
import com.example.settings.profile.viewmodel.ProfileEditViewModel
import com.point.chats.R
import com.point.envelope.BottomBarState
import com.point.envelope.navigation.extensions.entryComposable
import com.point.envelope.navigation.extensions.subComposable
import com.point.envelope.navigation.navhost.ComposeNavigationRoute.EntryRoute
import com.point.envelope.navigation.navhost.ComposeNavigationRoute.SubRoute
import com.point.envelope.navigation.navhost.asComposeRoute
import com.point.ui.scaffold.fab.FabState
import com.point.ui.scaffold.topappbar.state.TopAppBarAction
import com.point.ui.scaffold.topappbar.state.TopAppBarState
import com.point.ui.scaffold.topappbar.type.AppBarType

internal fun NavGraphBuilder.settingsFeature(
    navController: NavController,
    topAppBarState: MutableState<TopAppBarState>,
    bottomBarState: MutableState<BottomBarState>,
    fabState: MutableState<FabState>,
) {
    entryComposable<EntryRoute.Settings> {

        topAppBarState.value = TopAppBarState(
            appBarType = AppBarType.HeaderAppBar(
                headerRes = R.string.settings_screen_title
            ),
        )
        bottomBarState.value = BottomBarState(true)
        fabState.value = FabState.Hidden

        val viewModel = hiltViewModel<SettingsViewModel>()

        MainSettingsScreen(
            state = viewModel.composableState.value,
            onAction = viewModel::emitAction,
            onNavigate = { route -> navController.navigate(route.asComposeRoute) },
            events = viewModel.events,
            onLeftFromAccount = {
                navController.navigate(SubRoute.Authorization) {
                    popUpTo<EntryRoute.Settings> { inclusive = true }
                    launchSingleTop = true
                }
            },
            modifier = Modifier.fillMaxSize(),
        )
    }

    subComposable<SubRoute.EditProfile> {

        val viewModel = hiltViewModel<ProfileEditViewModel>()

        topAppBarState.value = TopAppBarState(
            appBarType = AppBarType.HeaderAppBar(
                headerRes = R.string.profile_edit_screen_title
            ),
            onBack = { navController.popBackStack() },
            actions = listOf(
                TopAppBarAction(
                    icon = Icons.Default.Check,
                    action = { viewModel.emitAction(ProfileEditAction.OnSavePressed) }
                )
            )
        )
        bottomBarState.value = BottomBarState(true)

        fabState.value = FabState.Hidden
        ProfileEditScreen(
            state = viewModel.composableState.value,
            onAction = viewModel::emitAction,
            modifier = Modifier.fillMaxSize(),
        )
    }
}