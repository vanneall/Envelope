package com.point.chats.dialog.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.point.chats.dialog.viewmodel.ChatDialogAction
import com.point.chats.dialog.viewmodel.ChatDialogState
import com.point.chats.dialog.viewmodel.Message
import com.point.ui.Theme

@Composable
fun ChatDialog(
    state: ChatDialogState,
    onAction: (ChatDialogAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.imePadding()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().weight(1f)
        ) {
            items(
                items = state.events,
                key = { it.id }
            ) {
                Message(
                    message = it,
                    modifier = Modifier.wrapContentSize()
                )
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
fun Message(message: Message, modifier: Modifier = Modifier) {
    Text(
        text = message.content,
        color = Color.Black,
        modifier = modifier.background(color = Theme.colorScheme.accent, shape = RoundedCornerShape(8.dp)),
    )
}