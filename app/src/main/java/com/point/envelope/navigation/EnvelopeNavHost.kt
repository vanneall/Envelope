package com.point.envelope.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.point.auth.authorization.presenter.mvi.AuthorizationViewModel
import com.point.auth.authorization.presenter.ui.AuthorizationScreen
import com.point.chats.create.presenter.ui.ContactsScreen
import com.point.chats.create.presenter.viewmodel.CreateChatViewModel
import com.point.chats.main.ui.ChatsScreen
import com.point.chats.main.viewmodel.ChatsHostViewModel

@Composable
fun EnvelopeNavHost(navHostController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Authorization,
        modifier = modifier,
    ) {

        composable<Screen.Authorization> {

            val viewModel = hiltViewModel<AuthorizationViewModel>()

            AuthorizationScreen(
                state = viewModel.composableState.value,
                onAction = viewModel::emitAction,
                modifier = Modifier.fillMaxSize(),
            )
        }

        composable<Screen.Registration> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Registration")
            }
        }

        composable<Screen.Chat> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Chat")
            }
        }

        composable<Screen.AllChats> {

            val viewModel = hiltViewModel<ChatsHostViewModel>()

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ChatsScreen(
                    state = viewModel.composableState.value,
                    events = viewModel.events,
                    onAction = {},
                    onNavigate = { navHostController.navigate(Screen.CreateNewChat) },
                    modifier = Modifier.fillMaxSize(),
                )
            }
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
                onNavigate = {},
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}