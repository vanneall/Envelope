package com.point.chats.creation.group.confirm.ui

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.point.chats.R
import com.point.chats.creation.group.confirm.mvi.actions.GroupChatConfirmAction
import com.point.chats.creation.group.confirm.mvi.events.ChatGroupConfirmEvents
import com.point.chats.creation.group.confirm.mvi.viewmodel.ChatGroupConfirmViewModel
import com.point.navigation.Route
import com.point.ui.scaffold.holder.scaffoldHolder
import com.point.ui.scaffold.topappbar.state.TopAppBarAction
import com.point.ui.scaffold.topappbar.state.TopAppBarState
import com.point.ui.scaffold.topappbar.type.AppBarType

@Composable
fun ChatGroupConfirmScreen(
    ids: List<String>,
    back: () -> Unit,
    navigate: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {

    val viewModel = hiltViewModel<ChatGroupConfirmViewModel, ChatGroupConfirmViewModel.Factory> {
        it.create(ids)
    }

    val scaffoldHolder = requireNotNull(LocalActivity.current?.scaffoldHolder)
    scaffoldHolder.topAppBarState = TopAppBarState(
        appBarType = AppBarType.HeaderAppBar(headerRes = R.string.group),
        onBack = back,
    )

    ChatGroupConfirmScreenContent(
        state = viewModel.composableState.value,
        action = viewModel::emitAction,
        modifier = modifier.padding(horizontal = 16.dp)
    )

    LaunchedEffect(viewModel.state.chatName, viewModel.state.chatPhoto) {
        val available = viewModel.state.chatName.isNotEmpty() && viewModel.state.chatPhoto != null

        scaffoldHolder.topAppBarState = if (!available) {
            scaffoldHolder.topAppBarState.copy(
                actions = scaffoldHolder.topAppBarState.actions.filter {
                    it.tag != "DONE"
                }
            )
        } else {
            scaffoldHolder.topAppBarState.copy(
                actions = scaffoldHolder.topAppBarState.actions + TopAppBarAction(
                    icon = Icons.Rounded.Done,
                    action = { viewModel.emitAction(GroupChatConfirmAction.UiEvent.CreateGroupChat) },
                    tag = "DONE",
                )
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is ChatGroupConfirmEvents.GroupChatCreated -> navigate(Route.ChatsFeature.Messaging(event.id))
            }
        }
    }
}