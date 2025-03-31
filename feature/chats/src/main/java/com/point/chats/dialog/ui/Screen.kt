package com.point.chats.dialog.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    Column(
        modifier = modifier.padding(
            WindowInsets.ime.exclude(WindowInsets.navigationBars).asPaddingValues()
        )
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 12.dp),
            reverseLayout = true,
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
                            onAction = onAction,
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
fun Message(message: MessageSentEvent, onAction: (ChatDialogAction) -> Unit, modifier: Modifier = Modifier) {
    val formattedTime = remember(message.timestamp) {
        DateTimeFormatter.ofPattern("HH:mm")
            .withZone(ZoneId.systemDefault())
            .format(message.timestamp)
    }

    var showMenu by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val ripple = rememberRipple(color = Color.Black)
    Column(
        modifier = modifier
            .background(
                color = Theme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = ripple,
                onClick = { showMenu = true })
            .padding(8.dp)
    ) {
        Text(
            text = message.text,
            color = Theme.colorScheme.textPrimary,
            style = Theme.typography.bodyL
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = formattedTime,
            color = Theme.colorScheme.textSecondary,
            style = Theme.typography.labelM,
        )

        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false },
            modifier = Modifier.background(
                color = Theme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            )
        ) {
            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null
                    )
                },
                text = { Text("Редактировать") },
                onClick = {
                    showMenu = false
                }
            )
            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                },
                text = { Text("Удалить") },
                onClick = {
                    showMenu = false
                    onAction(ChatDialogAction.Delete(message.id))
                }
            )
        }
    }
}