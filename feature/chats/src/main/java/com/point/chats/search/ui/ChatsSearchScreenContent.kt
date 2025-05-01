package com.point.chats.search.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.point.chats.R
import com.point.chats.search.mvi.actions.ChatsSearchAction.UiEvent
import com.point.chats.search.mvi.state.ChatsSearchState
import com.point.navigation.Route
import com.point.ui.colors.Black

@Composable
internal fun ChatsSearchScreenContent(
    state: ChatsSearchState,
    action: (UiEvent) -> Unit,
    navigate: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        item {
            if (state.chats.isEmpty()) return@item
            val titleModifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)

            if (state.recentShowed) {
                RecentTitle(
                    title = stringResource(R.string.recent_chats),
                    buttonTitle = stringResource(R.string.clear),
                    onClick = {},
                    modifier = titleModifier,
                )
            } else {
                Text(
                    text = stringResource(R.string.messages),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                    ),
                    color = Black,
                    modifier = titleModifier,
                )
            }
        }

        items(
            items = state.chats,
            key = { chat -> chat.id }
        ) { chat ->
            Chat(
                chatUi = chat,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(),
                        onClick = { }
                    )
                    .animateItem()
            )
        }
    }
}

@Composable
private fun RecentTitle(title: String, buttonTitle: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
            ),
            color = Black,
        )

        TextButton(
            onClick = onClick,

            ) {
            Text(
                text = buttonTitle,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                ),
                color = Black,
            )
        }
    }
}