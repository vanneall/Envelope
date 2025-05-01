package com.point.chats.chats.ui

import androidx.activity.compose.LocalActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.point.chats.R
import com.point.chats.chats.mvi.actions.ChatsAction
import com.point.chats.chats.mvi.state.ChatMode
import com.point.chats.chats.mvi.viewmodel.ChatsViewModel
import com.point.chats.main.ui.ChatsInitialLoading
import com.point.navigation.Route
import com.point.tea.dataOrNull
import com.point.ui.scaffold.holder.scaffoldHolder
import com.point.ui.scaffold.topappbar.state.TopAppBarAction
import com.point.ui.scaffold.topappbar.state.TopAppBarState
import com.point.ui.scaffold.topappbar.type.AppBarType

@Composable
fun ChatsScreen(navigate: (Route) -> Unit, modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<ChatsViewModel>()
    val state = viewModel.composableState.value
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = state.isDirtyLoading),
        onRefresh = { viewModel.emitAction(ChatsAction.UiEvent.Refresh) },
        swipeEnabled = !state.isLoading,
    ) {
        when {
            state.isClearLoading -> {
                ChatsInitialLoading(modifier = modifier)
            }

            state.dataOrNull != null -> {
                ChatsScreenContent(
                    state = requireNotNull(state.dataOrNull),
                    action = viewModel::emitAction,
                    navigate = navigate,
                    modifier = modifier,
                )
            }
        }
    }

    val scaffoldHolder = requireNotNull(LocalActivity.current).scaffoldHolder
    LaunchedEffect(Unit) {
        scaffoldHolder.topAppBarState = TopAppBarState(
            appBarType = AppBarType.HeaderAppBar(
                headerRes = R.string.chats_host_screen_title,
            ),
            actions = listOf(
                TopAppBarAction(
                    icon = Icons.Rounded.Search,
                    action = { navigate(Route.ChatsFeature.SearchChats) },
                    tag = "SEARCH"
                )
            )
        )
    }

    LaunchedEffect(state.dataOrNull?.mode) {
        val mode = state.dataOrNull?.mode ?: return@LaunchedEffect
        scaffoldHolder.topAppBarState = if (mode is ChatMode.Edit) {
            scaffoldHolder.topAppBarState.copy(
                actions = scaffoldHolder.topAppBarState.actions + TopAppBarAction(
                    icon = Icons.Rounded.Delete,
                    action = { viewModel.emitAction(ChatsAction.UiEvent.DeleteSelectedChats) },
                    tag = "DELETE"
                )
            )
        } else {
            scaffoldHolder.topAppBarState.copy(
                actions = scaffoldHolder.topAppBarState.actions.filter { action -> action.tag != "DELETE" }
            )
        }
    }
}