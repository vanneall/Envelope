package com.point.envelope.navigation.feature

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.toRoute
import com.point.chats.chats.ui.ChatsScreen
import com.point.chats.creation.ui.MultiDialogCreation
import com.point.chats.creation.viewmodel.MultiCreationViewModel
import com.point.chats.dialog.ui.ChatDialogScreen
import com.point.chats.dialog.viewmodel.ChatDialogEvent
import com.point.chats.dialog.viewmodel.ChatDialogViewModel
import com.point.chats.multi.info.ui.MultiChatInfoScreen
import com.point.chats.multi.info.viewmodel.MultiChatInfoViewModel
import com.point.chats.search.ui.ChatsSearchScreen
import com.point.envelope.BottomBarState
import com.point.envelope.navigation.extensions.entryComposable
import com.point.envelope.navigation.extensions.subComposable
import com.point.envelope.navigation.navhost.ComposeNavigationRoute.EntryRoute
import com.point.envelope.navigation.navhost.ComposeNavigationRoute.SubRoute
import com.point.envelope.navigation.navhost.asComposeRoute
import com.point.services.chats.models.ChatType
import com.point.ui.scaffold.fab.FabState
import com.point.ui.scaffold.topappbar.state.TopAppBarState
import com.point.ui.scaffold.topappbar.type.AppBarType
import kotlinx.coroutines.launch

internal fun NavGraphBuilder.chatsFeature(
    navController: NavController,
    topAppBarState: MutableState<TopAppBarState>,
    bottomBarState: MutableState<BottomBarState>,
    fabState: MutableState<FabState>,
) {
    entryComposable<EntryRoute.Chats> {
        val focusManager = LocalFocusManager.current
        LaunchedEffect(Unit) {
            focusManager.clearFocus()
        }

        bottomBarState.value = BottomBarState(true)

        fabState.value = FabState.Showed(
            icon = Icons.Default.Add,
            action = { navController.navigate(SubRoute.MultiChatCreation) },
        )

        ChatsScreen(
            navigate = { route -> navController.navigate(route.asComposeRoute) },
            modifier = Modifier.fillMaxSize()
        )
    }

    subComposable<SubRoute.SearchChats> {

        fabState.value = FabState.Hidden

        ChatsSearchScreen(
            navigate = { },
            back = { navController.popBackStack() },
            modifier = Modifier.fillMaxSize(),
        )
    }

    subComposable<SubRoute.Messaging> {

        val args = it.toRoute<SubRoute.Messaging>()

        val viewModel: ChatDialogViewModel = hiltViewModel<ChatDialogViewModel, ChatDialogViewModel.Factory>(
            creationCallback = { factory -> factory.create(args.chatId) }
        )

        bottomBarState.value = BottomBarState(false)

        fabState.value = FabState.Hidden

        LaunchedEffect(Unit) {
            launch {
                viewModel.events.collect {
                    if (it !is ChatDialogEvent.ChatInited) return@collect
                    topAppBarState.value = TopAppBarState(
                        appBarType = AppBarType.UserAppBar(
                            name = it.name,
                            photo = null,
                            onUserProfileClick = {
                                if (viewModel.state.chatType == ChatType.MANY) {
                                    navController.navigate(
                                        SubRoute.MultiChatInfo(viewModel.chatId)
                                    )
                                }
                            }
                        ),
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }


        ChatDialogScreen(
            state = viewModel.composableState.value,
            onAction = viewModel::emitAction,
            modifier = Modifier.fillMaxSize()
        )
    }

    subComposable<SubRoute.MultiChatCreation> {
        topAppBarState.value = TopAppBarState(
            appBarType = AppBarType.HeaderAppBar(
                header = "Создание мульти чата",
            ),
            onBack = { navController.popBackStack() }
        )

        bottomBarState.value = BottomBarState(false)

        fabState.value = FabState.Hidden

        val viewModel = hiltViewModel<MultiCreationViewModel>()

        MultiDialogCreation(
            state = viewModel.composableState.value,
            onAction = viewModel::emitAction,
            modifier = Modifier.fillMaxSize()
        )
    }

    subComposable<SubRoute.MultiChatInfo> {
        val args = it.toRoute<SubRoute.MultiChatInfo>()

        topAppBarState.value = TopAppBarState(
            appBarType = AppBarType.HeaderAppBar(
                header = "Инфо",
            ),
            onBack = { navController.popBackStack() }
        )

        bottomBarState.value = BottomBarState(false)

        fabState.value = FabState.Hidden

        val viewModel = hiltViewModel<MultiChatInfoViewModel, MultiChatInfoViewModel.Factory> { factory ->
            factory.create(args.chatId)
        }

        MultiChatInfoScreen(
            state = viewModel.composableState.value,
            onAction = viewModel::emitAction,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        )
    }
}