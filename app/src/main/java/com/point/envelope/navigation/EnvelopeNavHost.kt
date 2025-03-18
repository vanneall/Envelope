package com.point.envelope.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.point.auth.authorization.presenter.mvi.AuthorizationViewModel
import com.point.auth.authorization.presenter.ui.AuthorizationScreen
import com.point.auth.registration.presenter.mvi.RegistrationViewModel
import com.point.auth.registration.presenter.ui.RegistrationScreen
import com.point.chats.create.presenter.ui.ContactsScreen
import com.point.chats.create.presenter.viewmodel.CreateChatViewModel
import com.point.chats.dialog.ui.ChatDialog
import com.point.chats.dialog.viewmodel.ChatDialogViewModel
import com.point.chats.main.ui.ChatsScreen
import com.point.chats.main.viewmodel.Chat
import com.point.chats.main.viewmodel.ChatsHostViewModel
import com.point.contacts.search.presenter.ui.SearchContactScreen
import com.point.contacts.search.presenter.viewmodel.SearchContactsViewModel
import com.point.chats.R
import com.point.envelope.TopAppBarState
import com.point.envelope.TopAppBarState2

@Composable
fun EnvelopeNavHost(
    navHostController: NavHostController,
    modifier: Modifier,
    topAppBarState: MutableState<TopAppBarState2>,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Authorization,
        modifier = modifier,
    ) {

        composable<Screen.Authorization> {

            val viewModel = hiltViewModel<AuthorizationViewModel>()

            AuthorizationScreen(
                state = viewModel.composableState.value,
                onNavigate = { navHostController.navigate(Screen.Registration) },
                onAction = viewModel::emitAction,
                modifier = Modifier.fillMaxSize(),
            )
        }

        composable<Screen.Registration> {

            val viewModel = hiltViewModel<RegistrationViewModel>()

            RegistrationScreen(
                state = viewModel.composableState.value,
                onAction = viewModel::emitAction,
                onNavigate = { navHostController.popBackStack() },
                modifier = Modifier.fillMaxSize(),
            )
        }

        composable<Screen.Chat> {
            val args = it.toRoute<Screen.Chat>()

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

        composable<Screen.AllChats> {

            val viewModel = hiltViewModel<ChatsHostViewModel>()

            topAppBarState.value =
                TopAppBarState2(text = stringResource(R.string.chats_host_screen_title))
            
            ChatsScreen(
                state = viewModel.composableState.value,
                events = viewModel.events,
                onAction = {},
                onNavigateToChat = { navHostController.navigate(Screen.Chat(it)) },
                onNavigate = { navHostController.navigate(Screen.CreateNewChat) },
                modifier = Modifier.fillMaxSize(),
            )
        }

        composable<Screen.Profile> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Profile")
            }
        }

        composable<Screen.CreateNewChat> {


            val viewModel = hiltViewModel<CreateChatViewModel>()

            ContactsScreen(
                state = viewModel.composableState.value,
                onAction = viewModel::emitAction,
                events = viewModel.events,
                onNavigate = { navHostController.navigate(Screen.SearchContacts) },
                onPopBackState = { navHostController.popBackStack() },
                modifier = Modifier.fillMaxSize(),
            )
        }

        composable<Screen.SearchContacts> {

            val viewModel = hiltViewModel<SearchContactsViewModel>()

            SearchContactScreen(
                state = viewModel.composableState.value,
                onAction = viewModel::emitAction,
                onPopBackState = { navHostController.popBackStack() },
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}