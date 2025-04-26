package com.point.chats.multi.info.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.point.chats.multi.info.viewmodel.MultiChatInfoAction
import com.point.chats.multi.info.viewmodel.MultiChatInfoState

@Composable
fun MultiChatInfoScreen(
    state: MultiChatInfoState,
    onAction: (MultiChatInfoAction.UiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    MultiChatInfoScreenContent(
        state = state,
        onAction = onAction,
        modifier = modifier,
    )
}