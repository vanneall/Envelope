package com.point.chats.creation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.point.chats.creation.viewmodel.MultiCreationAction
import com.point.chats.creation.viewmodel.MultiCreationState

@Composable
fun MultiDialogCreation(
    state: MultiCreationState,
    onAction: (MultiCreationAction.UiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    MultiDialogCreationContent(
        state = state,
        onAction = onAction,
        modifier = modifier,
    )
}