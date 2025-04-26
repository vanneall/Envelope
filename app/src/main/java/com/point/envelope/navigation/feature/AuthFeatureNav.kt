package com.point.envelope.navigation.feature

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.point.auth.authorization.presenter.ui.screen.AuthorizationScreen
import com.point.auth.authorization.presenter.viewmodel.AuthorizationViewModel
import com.point.auth.registration.presenter.credentials.CredentialsViewModel
import com.point.auth.registration.presenter.host.HostViewModel
import com.point.auth.registration.presenter.profile.RegistrationProfileViewModel
import com.point.auth.registration.ui.host.RegistrationHostScreen
import com.point.envelope.BottomBarState
import com.point.envelope.navigation.extensions.subComposable
import com.point.envelope.navigation.navhost.ComposeNavigationRoute.SubRoute
import com.point.envelope.navigation.navhost.asComposeRoute
import com.point.envelope.scaffold.fab.FabState
import com.point.envelope.scaffold.topappbar.state.TopAppBarState
import com.point.envelope.scaffold.topappbar.type.AppBarType
import com.point.navigation.Route

internal fun NavGraphBuilder.authFeature(
    navController: NavController,
    topAppBarState: MutableState<TopAppBarState>,
    bottomBarState: MutableState<BottomBarState>,
    fabState: MutableState<FabState>,
) {

    composable<SubRoute.Authorization> {
        val focusManager = LocalFocusManager.current
        LaunchedEffect(Unit) {
            focusManager.clearFocus()
        }

        topAppBarState.value = TopAppBarState(
            appBarType = AppBarType.EmptyAppBar
        )

        fabState.value = FabState.Hidden

        bottomBarState.value = BottomBarState(false)

        val viewModel = hiltViewModel<AuthorizationViewModel>()

        AuthorizationScreen(
            state = viewModel.composableState.value,
            onNavigate = { route ->
                if (route is Route.AuthFeature) {
                    navController.navigate(route.asComposeRoute)
                } else {
                    navController.navigate(route.asComposeRoute) {
                        popUpTo(SubRoute.Authorization) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            },
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

        topAppBarState.value = TopAppBarState(
            appBarType = AppBarType.EmptyAppBar,
            onBack = { navController.popBackStack() }
        )

        bottomBarState.value = BottomBarState(false)

        fabState.value = FabState.Hidden

        val viewModel = hiltViewModel<HostViewModel>()
        viewModel.regProfileViewModel = hiltViewModel<RegistrationProfileViewModel>()
        viewModel.credentialsViewModel = hiltViewModel<CredentialsViewModel>()

        RegistrationHostScreen(
            onAction = viewModel::emitAction,
            events = viewModel.events,
            onNavigate = { route ->
                navController.navigate(route.asComposeRoute) {
                    popUpTo(SubRoute.Authorization) { inclusive = true }
                    launchSingleTop = true
                }
            },
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