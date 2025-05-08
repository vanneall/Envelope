package com.point.chats.multi.info.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.point.chats.multi.info.viewmodel.MultiChatInfoAction
import com.point.chats.multi.info.viewmodel.MultiChatInfoState
import com.point.navigation.Route

@Composable
fun MultiChatInfoScreen(
    state: MultiChatInfoState,
    navigate: (Route) -> Unit,
    onAction: (MultiChatInfoAction.UiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    MultiChatInfoScreenContent(
        state = state,
        onAction = onAction,
        navigate = navigate,
        modifier = modifier,
    )
}