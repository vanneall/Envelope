package com.point.envelope.navigation.feature

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.toRoute
import com.point.contacts.R
import com.point.contacts.main.presenter.ui.ContactsScreen
import com.point.contacts.profile.ui.content.ProfileScreen
import com.point.contacts.profile.viewmodel.ProfileViewModel
import com.point.contacts.requests.ui.UserRequestsScreen
import com.point.contacts.search.ui.UserSearchScreen
import com.point.envelope.BottomBarState
import com.point.envelope.navigation.extensions.entryComposable
import com.point.envelope.navigation.extensions.subComposable
import com.point.envelope.navigation.navhost.ComposeNavigationRoute.EntryRoute
import com.point.envelope.navigation.navhost.ComposeNavigationRoute.SubRoute
import com.point.envelope.navigation.navhost.asComposeRoute
import com.point.navigation.Route
import com.point.ui.LocalUser
import com.point.ui.scaffold.fab.FabState
import com.point.ui.scaffold.topappbar.state.TopAppBarAction
import com.point.ui.scaffold.topappbar.state.TopAppBarState
import com.point.ui.scaffold.topappbar.type.AppBarType

internal fun NavGraphBuilder.contactsFeature(
    navController: NavController,
    topAppBarState: MutableState<TopAppBarState>,
    bottomBarState: MutableState<BottomBarState>,
    fabState: MutableState<FabState>,
) {
    entryComposable<EntryRoute.Contacts> {
        bottomBarState.value = BottomBarState(true)
        fabState.value = FabState.Hidden

        ContactsScreen(
            topAppBarState = topAppBarState,
            onNavigation = { route -> navController.navigate(route.asComposeRoute) },
            modifier = Modifier.fillMaxSize(),
        )
    }

    subComposable<SubRoute.SearchContacts> {
        bottomBarState.value = BottomBarState(true)
        fabState.value = FabState.Hidden

        UserSearchScreen(
            topAppBarState = topAppBarState,
            onBack = { navController.popBackStack() },
            onNavigation = { route -> navController.navigate(route.asComposeRoute) },
            modifier = Modifier.fillMaxSize(),
        )
    }

    subComposable<SubRoute.NotificationContacts> {
        bottomBarState.value = BottomBarState(true)
        fabState.value = FabState.Hidden

        UserRequestsScreen(
            topAppBarState = topAppBarState,
            onBack = { navController.popBackStack() },
            onNavigation = { route -> navController.navigate(route.asComposeRoute) },
            modifier = Modifier.fillMaxSize(),
        )
    }

    subComposable<SubRoute.UserProfile> {
        val userProfileData = it.toRoute<SubRoute.UserProfile>()

        val username = requireNotNull(LocalUser.current?.username)
        topAppBarState.value = TopAppBarState(
            appBarType = AppBarType.HeaderAppBar(
                headerRes = R.string.profile_title
            ),
            onBack = { navController.popBackStack() },
            actions = if (userProfileData.username == username) {
                listOf(
                    TopAppBarAction(
                        icon = Icons.Rounded.Edit,
                        action = { navController.navigate(Route.SettingsFeature.ProfileEdit(username).asComposeRoute) },
                        tag = "EDIT_PROFILE"
                    )
                )
            } else {
                emptyList()
            }
        )

        val viewModel = hiltViewModel<ProfileViewModel, ProfileViewModel.Factory> { factory ->
            factory.create(username = userProfileData.username)
        }

        ProfileScreen(
            state = viewModel.composableState.value,
            onNavigation = { route -> navController.navigate(route.asComposeRoute) },
            onAction = viewModel::emitAction,
            events = viewModel.events,
            modifier = Modifier.fillMaxSize()
        )
    }
}