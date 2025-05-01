package com.point.chats.search.ui

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.point.chats.R
import com.point.chats.search.mvi.actions.ChatsSearchAction
import com.point.chats.search.mvi.viewmodel.ChatsSearchViewModel
import com.point.navigation.Route
import com.point.tea.dataOrNull
import com.point.ui.scaffold.holder.scaffoldHolder
import com.point.ui.scaffold.topappbar.state.TopAppBarState
import com.point.ui.scaffold.topappbar.type.AppBarType

@Composable
fun ChatsSearchScreen(navigate: (Route) -> Unit, back: () -> Unit, modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<ChatsSearchViewModel>()

    val scaffoldHolder = requireNotNull(LocalActivity.current?.scaffoldHolder)
    scaffoldHolder.topAppBarState = TopAppBarState(
        appBarType = AppBarType.SearchAppBar(
            placeHolder = R.string.search,
            onInput = { viewModel.emitAction(ChatsSearchAction.UiEvent.OnChatType(name = it)) }
        ),
        onBack = back,
    )

    ChatsSearchScreenContent(
        state = requireNotNull(viewModel.composableState.value.dataOrNull),
        action = viewModel::emitAction,
        navigate = navigate,
        modifier = modifier,
    )
}