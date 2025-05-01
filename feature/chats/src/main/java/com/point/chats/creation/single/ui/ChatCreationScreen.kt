package com.point.chats.creation.single.ui

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.point.chats.R
import com.point.chats.creation.single.mvi.events.ChatCreationEvent
import com.point.chats.creation.single.mvi.viewmodel.ChatCreationViewModel
import com.point.navigation.Route
import com.point.ui.scaffold.holder.scaffoldHolder
import com.point.ui.scaffold.topappbar.state.TopAppBarState
import com.point.ui.scaffold.topappbar.type.AppBarType

@Composable
fun ChatCreationScreen(navigate: (Route) -> Unit, back: () -> Unit, modifier: Modifier = Modifier) {

    val viewModel = hiltViewModel<ChatCreationViewModel>()

    requireNotNull(LocalActivity.current?.scaffoldHolder).topAppBarState = TopAppBarState(
        appBarType = AppBarType.HeaderAppBar(headerRes = R.string.new_message),
        onBack = back,
    )

    ChatCreationScreenContent(
        state = viewModel.composableState.value,
        navigate = navigate,
        action = viewModel::emitAction,
        modifier = modifier,
    )

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is ChatCreationEvent.OpenChat -> navigate(Route.ChatsFeature.Messaging(chatId = event.id))
            }
        }
    }
}