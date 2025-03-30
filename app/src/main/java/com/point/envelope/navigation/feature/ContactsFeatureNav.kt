package com.point.envelope.navigation.feature

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.toRoute
import com.point.contacts.main.presenter.ui.ContactsScreen
import com.point.contacts.main.presenter.viewmodel.UserContactsViewModel
import com.point.contacts.profile.ui.content.ProfileScreen
import com.point.contacts.profile.viewmodel.ProfileViewModel
import com.point.contacts.requests.ui.UserRequestsScreen
import com.point.contacts.requests.viewModel.RequestsContactsViewModel
import com.point.contacts.search.ui.SearchUsersScreen
import com.point.contacts.search.viewModel.SearchContactsViewModel
import com.point.envelope.BottomBarState
import com.point.envelope.TopAppBarState2
import com.point.envelope.TopBarAction
import com.point.envelope.navigation.navhost.ComposeNavigationRoute.EntryRoute
import com.point.envelope.navigation.navhost.ComposeNavigationRoute.SubRoute
import com.point.envelope.navigation.navhost.asComposeRoute
import com.point.envelope.navigation.extensions.entryComposable
import com.point.envelope.navigation.extensions.subComposable

internal fun NavGraphBuilder.contactsFeature(
    navController: NavController,
    topAppBarState: MutableState<TopAppBarState2>,
    bottomBarState: MutableState<BottomBarState>,
) {
    entryComposable<EntryRoute.Contacts> {
        topAppBarState.value = TopAppBarState2(
            text = stringResource(com.point.contacts.R.string.contacts_screen_title),
            actions = listOf(
                TopBarAction(
                    icon = Icons.Default.Search,
                    action = { navController.navigate(SubRoute.SearchContacts) },
                ),
                TopBarAction(
                    icon = Icons.Default.Notifications,
                    action = { navController.navigate(SubRoute.NotificationContacts) },
                )
            )
        )
        bottomBarState.value = BottomBarState(true)
        val viewModel = hiltViewModel<UserContactsViewModel>()

        ContactsScreen(
            state = viewModel.composableState.value,
            onAction = viewModel::emitAction,
            onNavigation = { route -> navController.navigate(route.asComposeRoute) },
            modifier = Modifier.fillMaxSize(),
        )
    }

    subComposable<SubRoute.SearchContacts> {
        topAppBarState.value = TopAppBarState2(
            text = stringResource(com.point.contacts.R.string.search_screen_title),
            isBackVisible = true,
            onBackClick = { navController.popBackStack() },
        )
        bottomBarState.value = BottomBarState(true)
        val viewModel = hiltViewModel<SearchContactsViewModel>()

        SearchUsersScreen(
            state = viewModel.composableState.value,
            onAction = viewModel::emitAction,
            onNavigation = { route -> navController.navigate(route.asComposeRoute) },
            modifier = Modifier.fillMaxSize(),
        )
    }

    subComposable<SubRoute.NotificationContacts> {
        topAppBarState.value = TopAppBarState2(
            text = stringResource(com.point.contacts.R.string.search_requests_title),
            isBackVisible = true,
            onBackClick = { navController.popBackStack() },
        )
        bottomBarState.value = BottomBarState(true)
        val viewModel = hiltViewModel<RequestsContactsViewModel>()

        UserRequestsScreen(
            state = viewModel.composableState.value,
            onAction = viewModel::emitAction,
            onNavigation = { route -> navController.navigate(route.asComposeRoute) },
            modifier = Modifier.fillMaxSize(),
        )
    }

    subComposable<SubRoute.UserProfile> {
        val userProfileData = it.toRoute<SubRoute.UserProfile>()

        topAppBarState.value = TopAppBarState2(
            text = stringResource(com.point.contacts.R.string.profile_title),
            isBackVisible = true,
            onBackClick = { navController.popBackStack() },
        )

        val viewModel = hiltViewModel<ProfileViewModel, ProfileViewModel.Factory> { factory ->
            factory.create(username = userProfileData.username)
        }

        ProfileScreen(
            state = viewModel.composableState.value,
            onAction = viewModel::emitAction,
            modifier = Modifier.fillMaxSize()
        )
    }
}