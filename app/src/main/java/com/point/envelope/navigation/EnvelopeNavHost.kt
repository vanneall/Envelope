package com.point.envelope.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.settings.main.ui.MainSettingsScreen
import com.example.settings.main.viewmodel.SettingsViewModel
import com.point.auth.authorization.presenter.mvi.AuthorizationViewModel
import com.point.auth.authorization.presenter.ui.AuthorizationScreen
import com.point.auth.registration.presenter.credentials.CredentialsViewModel
import com.point.auth.registration.presenter.mvi.RegistrationViewModel
import com.point.auth.registration.presenter.profile.RegistrationProfileViewModel
import com.point.auth.registration.ui.host.RegistrationHostScreen
import com.point.chats.R
import com.point.chats.create.presenter.ui.ContactsScreen
import com.point.chats.create.presenter.viewmodel.CreateChatViewModel
import com.point.chats.dialog.ui.ChatDialog
import com.point.chats.dialog.viewmodel.ChatDialogViewModel
import com.point.chats.main.ui.ChatsScreen
import com.point.chats.main.viewmodel.ChatsHostViewModel
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
import com.point.envelope.navigation.Screen.Main
import com.point.envelope.navigation.Screen.SubScreen

@Composable
fun EnvelopeNavHost(
    navHostController: NavHostController,
    modifier: Modifier,
    topAppBarState: MutableState<TopAppBarState2>,
    bottomBarState: MutableState<BottomBarState>,
) {
    NavHost(
        navController = navHostController,
        startDestination = SubScreen.Authorization,
        modifier = modifier,
    ) {

        subComposable<SubScreen.Authorization> {
            val focusManager = LocalFocusManager.current
            LaunchedEffect(Unit) {
                focusManager.clearFocus()
            }

            topAppBarState.value =
                TopAppBarState2(text = stringResource(R.string.settings_authorization_title))
            bottomBarState.value = BottomBarState(false)

            val viewModel = hiltViewModel<AuthorizationViewModel>()

            AuthorizationScreen(
                state = viewModel.composableState.value,
                onNavigate = { navHostController.navigate(SubScreen.Registration) },
                onNavigateMain = { navHostController.navigate(Main.AllChats) },
                onAction = viewModel::emitAction,
                events = viewModel.events,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
            )
        }

        subComposable<Screen.SubScreen.Registration> {
            val focusManager = LocalFocusManager.current
            LaunchedEffect(Unit) {
                focusManager.clearFocus()
            }

            topAppBarState.value = TopAppBarState2(
                text = stringResource(R.string.registration_screen_title),
                isBackVisible = true,
                onBackClick = { navHostController.popBackStack() },
            )
            bottomBarState.value = BottomBarState(false)

            val viewModel = hiltViewModel<RegistrationViewModel>()
            viewModel.regProfileViewModel = hiltViewModel<RegistrationProfileViewModel>()
            viewModel.credentialsViewModel = hiltViewModel<CredentialsViewModel>()

            RegistrationHostScreen(
                onAction = viewModel::emitAction,
                events = viewModel.events,
                onNavigateToMain = { navHostController.navigate(Main.AllChats) },
                regProfileState = viewModel.regProfileViewModel.composableState.value,
                regProfileAction = viewModel.regProfileViewModel::emitAction,
                credentialsState = viewModel.credentialsViewModel.composableState.value,
                credentialsAction = viewModel.credentialsViewModel::emitAction,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
            )
        }

        subComposable<SubScreen.Chat> {
            bottomBarState.value = BottomBarState(true)
            val args = it.toRoute<SubScreen.Chat>()

            val viewModel: ChatDialogViewModel =
                hiltViewModel<ChatDialogViewModel, ChatDialogViewModel.Factory>(
                    creationCallback = { factory -> factory.create(args.id) }
                )

            ChatDialog(
                state = viewModel.composableState.value,
                onAction = viewModel::emitAction,
                modifier = Modifier.fillMaxSize()
            )
        }

        mainComposable<Main.AllChats> {
            val focusManager = LocalFocusManager.current
            LaunchedEffect(Unit) {
                focusManager.clearFocus()
            }
            bottomBarState.value = BottomBarState(true)
            val viewModel = hiltViewModel<ChatsHostViewModel>()

            topAppBarState.value =
                TopAppBarState2(text = stringResource(R.string.chats_host_screen_title))

            ChatsScreen(
                state = viewModel.composableState.value,
                events = viewModel.events,
                onAction = {},
                onNavigateToChat = { navHostController.navigate(SubScreen.Chat(it)) },
                onNavigate = { navHostController.navigate(SubScreen.CreateNewChat) },
                modifier = Modifier.fillMaxSize(),
            )
        }

        mainComposable<Main.Profile> {
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

        subComposable<SubScreen.CreateNewChat> {
            bottomBarState.value = BottomBarState(true)

            val viewModel = hiltViewModel<CreateChatViewModel>()

            ContactsScreen(
                state = viewModel.composableState.value,
                onAction = viewModel::emitAction,
                events = viewModel.events,
                onNavigate = { navHostController.navigate(Main.Contacts) },
                onPopBackState = { navHostController.popBackStack() },
                modifier = Modifier.fillMaxSize(),
            )
        }

        mainComposable<Main.Contacts> {
            topAppBarState.value = TopAppBarState2(
                text = stringResource(com.point.contacts.R.string.contacts_screen_title),
                actions = listOf(
                    TopBarAction(
                        icon = Icons.Default.Search,
                        action = { navHostController.navigate(SubScreen.SearchContacts) },
                    ),
                    TopBarAction(
                        icon = Icons.Default.Notifications,
                        action = { navHostController.navigate(SubScreen.NotificationContacts) },
                    )
                )
            )
            bottomBarState.value = BottomBarState(true)
            val viewModel = hiltViewModel<UserContactsViewModel>()

            ContactsScreen(
                state = viewModel.composableState.value,
                onAction = viewModel::emitAction,
                onNavigation = { navHostController.navigate(SubScreen.UserProfile(it)) },
                modifier = Modifier.fillMaxSize(),
            )
        }

        subComposable<SubScreen.SearchContacts> {
            topAppBarState.value = TopAppBarState2(
                text = stringResource(com.point.contacts.R.string.search_screen_title),
                isBackVisible = true,
                onBackClick = { navHostController.popBackStack() },
            )
            bottomBarState.value = BottomBarState(true)
            val viewModel = hiltViewModel<SearchContactsViewModel>()

            SearchUsersScreen(
                state = viewModel.composableState.value,
                onAction = viewModel::emitAction,
                onNavigation = { navHostController.navigate(SubScreen.UserProfile(it)) },
                modifier = Modifier.fillMaxSize(),
            )
        }

        subComposable<SubScreen.NotificationContacts> {
            topAppBarState.value = TopAppBarState2(
                text = stringResource(com.point.contacts.R.string.search_requests_title),
                isBackVisible = true,
                onBackClick = { navHostController.popBackStack() },
            )
            bottomBarState.value = BottomBarState(true)
            val viewModel = hiltViewModel<RequestsContactsViewModel>()

            UserRequestsScreen(
                state = viewModel.composableState.value,
                onAction = viewModel::emitAction,
                onNavigation = { navHostController.navigate(SubScreen.UserProfile(it)) },
                modifier = Modifier.fillMaxSize(),
            )
        }

        subComposable<SubScreen.UserProfile> {
            val userProfileData = it.toRoute<SubScreen.UserProfile>()

            topAppBarState.value = TopAppBarState2(
                text = stringResource(com.point.contacts.R.string.profile_title),
                isBackVisible = true,
                onBackClick = { navHostController.popBackStack() },
            )

            val viewModel = hiltViewModel<ProfileViewModel, ProfileViewModel.Factory> { factory ->
                factory.create(username = userProfileData.userId)
            }

            ProfileScreen(
                state = viewModel.composableState.value,
                onAction = viewModel::emitAction,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

private inline fun <reified T : SubScreen> NavGraphBuilder.subComposable(
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable<T>(
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) },
        content = content,
    )
}

private inline fun <reified T : Main> NavGraphBuilder.mainComposable(
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable<T>(
        enterTransition = {
            slideInVertically(
                initialOffsetY = { it / 2 }
            ) + scaleIn(
                initialScale = 0.85f,
                animationSpec = tween(200, easing = FastOutSlowInEasing)
            ) + fadeIn(
                animationSpec = tween(200)
            )
        },
        exitTransition = { fadeOut(animationSpec = tween(0)) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) },
        content = content,
    )
}

