package com.point.chats.main.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.point.chats.main.viewmodel.ChatsState
import com.point.navigation.Route
import com.point.chats.main.viewmodel.ChatAction.UiAction
import com.point.chats.main.viewmodel.items.ChatUi
import com.point.chats.main.viewmodel.items.Mode
import com.point.ui.Theme
import com.point.ui.materials.divider.EnvelopeHorizontalDivider

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatsScreenContent(
    state: ChatsState,
    onAction: (UiAction) -> Unit,
    onNavigate: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(
            items = state.chats,
            key = { _, chat -> (chat as ChatUi).id },
        ) { index, chat ->
            val interactionSource = remember { MutableInteractionSource() }
            ChatCard(
                chatUi = chat as ChatUi,
                onAction = onAction,
                inEditMode = state.mode == Mode.EDIT,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .combinedClickable(
                        interactionSource = interactionSource,
                        indication = rememberRipple(color = Theme.colorScheme.overlay),
                        onClick = {
                            if (state.mode == Mode.EDIT) {
                                onAction(
                                    UiAction.OnChatCheckedChange(
                                        id = chat.id,
                                        checked = !chat.checked,
                                    )
                                )
                            } else {
                                onNavigate(Route.ChatsFeature.Messaging(chat.id))
                            }
                        },
                        onLongClick = {
                            onAction(
                                UiAction.OnChatCheckedChange(
                                    id = chat.id,
                                    checked = !chat.checked,
                                )
                            )
                        }
                    )
                    .padding(horizontal = 10.dp),
            )

            val isLast by remember { derivedStateOf { index != state.chats.lastIndex } }

            if (isLast) {
                EnvelopeHorizontalDivider(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}