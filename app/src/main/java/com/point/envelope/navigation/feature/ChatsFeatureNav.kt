package com.point.envelope.navigation.feature

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.toRoute
import com.point.chats.R
import com.point.chats.dialog.ui.ChatDialogScreen
import com.point.chats.dialog.viewmodel.ChatDialogViewModel
import com.point.chats.main.ui.ChatsScreen
import com.point.chats.main.viewmodel.ChatsHostViewModel
import com.point.envelope.BottomBarState
import com.point.envelope.navigation.extensions.entryComposable
import com.point.envelope.navigation.extensions.subComposable
import com.point.envelope.navigation.navhost.ComposeNavigationRoute.EntryRoute
import com.point.envelope.navigation.navhost.ComposeNavigationRoute.SubRoute
import com.point.envelope.navigation.navhost.asComposeRoute
import com.point.envelope.scaffold.topappbar.state.TopAppBarState
import com.point.envelope.scaffold.topappbar.type.AppBarType

internal fun NavGraphBuilder.chatsFeature(
    navController: NavController,
    topAppBarState: MutableState<TopAppBarState>,
    bottomBarState: MutableState<BottomBarState>,
) {
    entryComposable<EntryRoute.Chats> {
        val focusManager = LocalFocusManager.current
        LaunchedEffect(Unit) {
            focusManager.clearFocus()
        }

        bottomBarState.value = BottomBarState(true)
        val viewModel = hiltViewModel<ChatsHostViewModel>()

        topAppBarState.value = TopAppBarState(
            appBarType = AppBarType.HeaderAppBar(
                headerRes = R.string.chats_host_screen_title,
            ),
        )

        ChatsScreen(
            state = viewModel.composableState.value,
            events = viewModel.events,
            onAction = viewModel::emitAction,
            onNavigate = { route -> navController.navigate(route.asComposeRoute) },
            modifier = Modifier.fillMaxSize(),
        )
    }

    subComposable<SubRoute.Messaging> {

        topAppBarState.value = TopAppBarState(
            appBarType = AppBarType.HeaderAppBar(
                header = "Конкретный чат",
            ),
            onBack = { navController.popBackStack() }
        )

        bottomBarState.value = BottomBarState(false)

        val args = it.toRoute<SubRoute.Messaging>()

        val viewModel: ChatDialogViewModel = hiltViewModel<ChatDialogViewModel, ChatDialogViewModel.Factory>(
            creationCallback = { factory -> factory.create(args.chatId) }
        )

        ChatDialogScreen(
            state = viewModel.composableState.value,
            onAction = viewModel::emitAction,
            modifier = Modifier.fillMaxSize()
        )
    }
}