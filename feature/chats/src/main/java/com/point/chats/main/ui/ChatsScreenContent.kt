package com.point.chats.main.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.point.chats.main.viewmodel.ChatAction
import com.point.chats.main.viewmodel.ChatsState
import com.point.navigation.Route
import com.point.ui.Theme

@Composable
fun ChatsScreenContent(
    state: ChatsState,
    onAction: (ChatAction) -> Unit,
    onNavigate: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(
            count = state.chats.size,
            key = { index -> state.chats[index].id },
        ) {
            val interactionSource = remember { MutableInteractionSource() }

            ChatComposable(
                chat = state.chats[it],
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = rememberRipple(color = Theme.colorScheme.overlay)
                    ) { onNavigate(Route.ChatsFeature.Messaging(state.chats[it].id)) },
            )
        }
    }
}