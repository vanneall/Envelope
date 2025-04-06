package com.point.envelope.navigation.feature

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.toRoute
import com.point.contacts.R
import com.point.contacts.main.presenter.ui.ContactsScreen
import com.point.contacts.main.presenter.viewmodel.UserContactsViewModel
import com.point.contacts.profile.ui.content.ProfileScreen
import com.point.contacts.profile.viewmodel.ProfileViewModel
import com.point.contacts.requests.ui.UserRequestsScreen
import com.point.contacts.requests.viewModel.RequestsContactsViewModel
import com.point.contacts.search.ui.SearchUsersScreen
import com.point.contacts.search.viewModel.SearchContactsAction
import com.point.contacts.search.viewModel.SearchContactsViewModel
import com.point.envelope.BottomBarState
import com.point.envelope.navigation.extensions.entryComposable
import com.point.envelope.navigation.extensions.subComposable
import com.point.envelope.navigation.navhost.ComposeNavigationRoute.EntryRoute
import com.point.envelope.navigation.navhost.ComposeNavigationRoute.SubRoute
import com.point.envelope.navigation.navhost.asComposeRoute
import com.point.envelope.scaffold.topappbar.state.TopAppBarAction
import com.point.envelope.scaffold.topappbar.state.TopAppBarState
import com.point.envelope.scaffold.topappbar.type.AppBarType

internal fun NavGraphBuilder.contactsFeature(
    navController: NavController,
    topAppBarState: MutableState<TopAppBarState>,
    bottomBarState: MutableState<BottomBarState>,
) {
    entryComposable<EntryRoute.Contacts> {
        topAppBarState.value = TopAppBarState(
            appBarType = AppBarType.HeaderAppBar(
                headerRes = com.point.contacts.R.string.contacts_screen_title,
            ),
            actions = listOf(
                TopAppBarAction(
                    icon = Icons.Default.Search,
                    action = { navController.navigate(SubRoute.SearchContacts) },
                ),
                TopAppBarAction(
                    icon = Icons.Default.Notifications,
                    action = { navController.navigate(SubRoute.NotificationContacts) },
                )
            ),
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
        val viewModel = hiltViewModel<SearchContactsViewModel>()

        topAppBarState.value = TopAppBarState(
            appBarType = AppBarType.SearchAppBar(
                placeHolder = R.string.search_screen_title,
                onInput = { viewModel.emitAction(SearchContactsAction.OnNameTyped(it)) }
            ),
            onBack = { navController.popBackStack() },
        )
        bottomBarState.value = BottomBarState(true)

        SearchUsersScreen(
            state = viewModel.composableState.value,
            onAction = viewModel::emitAction,
            onNavigation = { route -> navController.navigate(route.asComposeRoute) },
            modifier = Modifier.fillMaxSize(),
        )
    }

    subComposable<SubRoute.NotificationContacts> {
        topAppBarState.value = TopAppBarState(
            appBarType = AppBarType.HeaderAppBar(
                headerRes = R.string.search_requests_title
            ),
            onBack = { navController.popBackStack() },
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

        topAppBarState.value = TopAppBarState(
            appBarType = AppBarType.HeaderAppBar(
                headerRes = R.string.profile_title
            ),
            onBack = { navController.popBackStack() },
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