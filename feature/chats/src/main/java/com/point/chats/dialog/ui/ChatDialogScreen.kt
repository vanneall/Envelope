package com.point.chats.dialog.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.point.chats.dialog.viewmodel.ChatDialogAction
import com.point.chats.dialog.viewmodel.ChatDialogState

@Composable
fun ChatDialogScreen(
    state: ChatDialogState,
    onAction: (ChatDialogAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.isInitialLoading) {
        MessageShimmer(
            modifier = modifier,
        )
    } else {
        ChatDialogScreenContent(
            state = state,
            onAction = onAction,
            modifier = modifier,
        )
    }

}