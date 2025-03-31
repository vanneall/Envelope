package com.point.envelope.navigation.feature

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.toRoute
import com.point.chats.R
import com.point.chats.dialog.ui.ChatDialog
import com.point.chats.dialog.viewmodel.ChatDialogViewModel
import com.point.chats.main.ui.ChatsScreen
import com.point.chats.main.viewmodel.ChatsHostViewModel
import com.point.envelope.BottomBarState
import com.point.envelope.TopAppBarState2
import com.point.envelope.navigation.extensions.entryComposable
import com.point.envelope.navigation.extensions.subComposable
import com.point.envelope.navigation.navhost.ComposeNavigationRoute.EntryRoute
import com.point.envelope.navigation.navhost.ComposeNavigationRoute.SubRoute
import com.point.envelope.navigation.navhost.asComposeRoute

internal fun NavGraphBuilder.chatsFeature(
    navController: NavController,
    topAppBarState: MutableState<TopAppBarState2>,
    bottomBarState: MutableState<BottomBarState>,
) {
    entryComposable<EntryRoute.Chats> {
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
            onAction = viewModel::emitAction,
            onNavigate = { route -> navController.navigate(route.asComposeRoute) },
            modifier = Modifier.fillMaxSize(),
        )
    }

    subComposable<SubRoute.Messaging> {
        bottomBarState.value = BottomBarState(false)
        topAppBarState.value = TopAppBarState2(
            text = "Конкретный чат",
            isBackVisible = true,
            onBackClick = { navController.popBackStack() }
        )

        val args = it.toRoute<SubRoute.Messaging>()

        val viewModel: ChatDialogViewModel =
            hiltViewModel<ChatDialogViewModel, ChatDialogViewModel.Factory>(
                creationCallback = { factory -> factory.create(args.chatId) }
            )

        ChatDialog(
            state = viewModel.composableState.value,
            onAction = viewModel::emitAction,
            modifier = Modifier.fillMaxSize()
        )
    }
}