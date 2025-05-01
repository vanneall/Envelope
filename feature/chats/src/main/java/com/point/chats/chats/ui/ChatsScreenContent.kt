@file:OptIn(ExperimentalFoundationApi::class)

package com.point.chats.chats.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.point.chats.chats.mvi.actions.ChatsAction.UiEvent
import com.point.chats.chats.mvi.state.ChatState
import com.point.navigation.Route

@Composable
internal fun ChatsScreenContent(
    state: ChatState,
    action: (UiEvent) -> Unit,
    navigate: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(
            items = state.chats,
            key = { chat -> chat.id },
        ) { chat ->
            ChatEditable(
                chatUi = chat,
                chatMode = state.mode,
                action = action,
                modifier = Modifier
                    .fillMaxWidth()
                    .combinedClickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(),
                        onClick = {
                            if (state.isEdit) {
                                action(
                                    UiEvent.SelectChat(
                                        id = chat.id,
                                        checked = !state.isChecked(chat.id)
                                    )
                                )
                            } else {
                                navigate(Route.ChatsFeature.Messaging(chatId = chat.id))
                            }
                        },
                        onLongClick = {
                            action(
                                UiEvent.SelectChat(
                                    id = chat.id,
                                    checked = !state.isChecked(chat.id)
                                )
                            )
                        }
                    )
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .animateItem()
            )
        }
    }
}