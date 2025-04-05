package com.point.chats.main.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.point.chats.main.viewmodel.ChatAction
import com.point.chats.main.viewmodel.ChatsState
import com.point.navigation.Route
import com.point.ui.Theme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatsScreenContent(
    state: ChatsState,
    onAction: (ChatAction) -> Unit,
    onNavigate: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(
            items = state.chats,
            key = { _, chat -> chat.id },
        ) { index, chat ->
            val interactionSource = remember { MutableInteractionSource() }

            ChatComposable(
                chat = chat,
                modifier = Modifier
                    .combinedClickable(
                        interactionSource = interactionSource,
                        indication = rememberRipple(color = Theme.colorScheme.overlay),
                        onClick = { onNavigate(Route.ChatsFeature.Messaging(chat.id)) },
                        onLongClick = { onAction(ChatAction.Action.DeleteDialog(chat.id)) },
                    ),
            )

            val isLast by remember { derivedStateOf { index != state.chats.lastIndex } }

            if (isLast) {
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}