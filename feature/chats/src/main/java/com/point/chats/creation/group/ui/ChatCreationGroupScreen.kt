package com.point.chats.creation.group.ui

import androidx.activity.compose.LocalActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.point.chats.R
import com.point.chats.creation.group.mvi.viewmodel.ChatCreationGroupViewModel
import com.point.navigation.Route
import com.point.ui.scaffold.holder.scaffoldHolder
import com.point.ui.scaffold.topappbar.state.TopAppBarAction
import com.point.ui.scaffold.topappbar.state.TopAppBarState
import com.point.ui.scaffold.topappbar.type.AppBarType

@Composable
fun ChatCreationGroupScreen(
    navigate: (Route) -> Unit,
    back: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = hiltViewModel<ChatCreationGroupViewModel>()

    val scaffoldHolder = requireNotNull(LocalActivity.current?.scaffoldHolder)
    scaffoldHolder.topAppBarState = TopAppBarState(
        appBarType = AppBarType.HeaderAppBar(headerRes = R.string.group),
        onBack = back,
    )

    ChatCreationGroupScreenContent(
        state = viewModel.composableState.value,
        action = viewModel::emitAction,
        modifier = modifier,
    )

    LaunchedEffect(viewModel.state.selected) {
        scaffoldHolder.topAppBarState = if (viewModel.state.selected.isEmpty()) {
            scaffoldHolder.topAppBarState.copy(
                actions = scaffoldHolder.topAppBarState.actions.filter {
                    it.tag != "DONE"
                }
            )
        } else {
            scaffoldHolder.topAppBarState.copy(
                actions = scaffoldHolder.topAppBarState.actions + TopAppBarAction(
                    icon = Icons.Rounded.Done,
                    action = { },
                    tag = "DONE",
                )
            )
        }
    }
}