package com.point.chats.dialog.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.point.chats.dialog.data.events.MessageSentEvent
import com.point.chats.dialog.viewmodel.ChatDialogAction
import com.point.chats.dialog.viewmodel.ChatDialogState
import com.point.ui.Theme
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun ChatDialog(
    state: ChatDialogState,
    onAction: (ChatDialogAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.imePadding()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .weight(1f)
        ) {
            items(
                items = state.events,
                key = { it.id }
            ) {
                when (it) {
                    is MessageSentEvent -> {
                        Message(
                            message = it,
                            modifier = Modifier.wrapContentSize()
                        )
                    }
                    else -> {}
                }

            }
        }

        TextField(
            value = state.message,
            onValueChange = { onAction(ChatDialogAction.TypeMessage(it)) },
            singleLine = true,
            placeholder = { Text(text = "Сообщение") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onAction(ChatDialogAction.Send) },
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun Message(message: MessageSentEvent, modifier: Modifier = Modifier) {
    val formattedTime = remember(message.timestamp) {
        DateTimeFormatter.ofPattern("HH:mm")
            .withZone(ZoneId.systemDefault())
            .format(message.timestamp)
    }

    Column(
        modifier = modifier
            .background(
                color = Theme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        Text(
            text = message.text.orEmpty(),
            color = Theme.colorScheme.textPrimary,
            style = Theme.typography.bodyL
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = formattedTime,
            color = Theme.colorScheme.textSecondary,
            style = Theme.typography.labelM
        )
    }
}