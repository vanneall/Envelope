package com.point.chats.main.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.point.chats.main.viewmodel.Chat
import com.point.chats.main.viewmodel.ChatAction
import com.point.chats.main.viewmodel.ChatsState
import com.point.chats.main.viewmodel.MessageShort
import com.point.navigation.Route
import com.point.ui.EnvelopeTheme
import com.point.ui.Theme
import java.time.Instant

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

@Preview(locale = "ru")
@Composable
private fun ContentPreview() {
    EnvelopeTheme {
        ChatsScreenContent(
            state = ChatsState(
                chats = listOf(
                    Chat(
                        id ="1",
                        name = "Person 1",
                        photoUrl = null,
                        lastMessage = MessageShort(id = "mes_1", text = "Last message", Instant.now())
                    ),
                    Chat(
                        id ="2",
                        name = "Person 2",
                        photoUrl = null,
                        lastMessage = MessageShort(id = "mes_2", text = "Last message", Instant.now())
                    ),
                )
            ),
            onAction = {},
            onNavigate = {},
            modifier = Modifier.fillMaxSize().background(color = Color.White).padding(horizontal = 10.dp, vertical = 10.dp)
        )
    }
}