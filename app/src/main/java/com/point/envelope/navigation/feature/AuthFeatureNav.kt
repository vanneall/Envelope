package com.point.envelope.navigation.feature

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.point.auth.authorization.presenter.mvi.AuthorizationViewModel
import com.point.auth.authorization.presenter.ui.AuthorizationScreen
import com.point.auth.registration.presenter.credentials.CredentialsViewModel
import com.point.auth.registration.presenter.mvi.RegistrationViewModel
import com.point.auth.registration.presenter.profile.RegistrationProfileViewModel
import com.point.auth.registration.ui.host.RegistrationHostScreen
import com.point.chats.R
import com.point.envelope.BottomBarState
import com.point.envelope.TopAppBarState2
import com.point.envelope.navigation.navhost.ComposeNavigationRoute.SubRoute
import com.point.envelope.navigation.navhost.asComposeRoute
import com.point.envelope.navigation.extensions.subComposable

internal fun NavGraphBuilder.authFeature(
    navController: NavController,
    topAppBarState: MutableState<TopAppBarState2>,
    bottomBarState: MutableState<BottomBarState>,
) {

    subComposable<SubRoute.Authorization> {
        val focusManager = LocalFocusManager.current
        LaunchedEffect(Unit) {
            focusManager.clearFocus()
        }

        topAppBarState.value = TopAppBarState2(text = stringResource(R.string.settings_authorization_title))
        bottomBarState.value = BottomBarState(false)

        val viewModel = hiltViewModel<AuthorizationViewModel>()

        AuthorizationScreen(
            state = viewModel.composableState.value,
            onNavigate = { route -> navController.navigate(route.asComposeRoute) },
            onAction = viewModel::emitAction,
            events = viewModel.events,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
        )
    }

    subComposable<SubRoute.Registration> {
        val focusManager = LocalFocusManager.current
        LaunchedEffect(Unit) {
            focusManager.clearFocus()
        }

        topAppBarState.value = TopAppBarState2(
            text = stringResource(R.string.registration_screen_title),
            isBackVisible = true,
            onBackClick = { navController.popBackStack() },
        )
        bottomBarState.value = BottomBarState(false)

        val viewModel = hiltViewModel<RegistrationViewModel>()
        viewModel.regProfileViewModel = hiltViewModel<RegistrationProfileViewModel>()
        viewModel.credentialsViewModel = hiltViewModel<CredentialsViewModel>()

        RegistrationHostScreen(
            onAction = viewModel::emitAction,
            events = viewModel.events,
            onNavigate = { route -> navController.navigate(route.asComposeRoute) },
            regProfileState = viewModel.regProfileViewModel.composableState.value,
            regProfileAction = viewModel.regProfileViewModel::emitAction,
            credentialsState = viewModel.credentialsViewModel.composableState.value,
            credentialsAction = viewModel.credentialsViewModel::emitAction,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
        )
    }

}