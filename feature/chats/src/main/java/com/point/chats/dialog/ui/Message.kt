package com.point.chats.dialog.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.point.chats.R
import com.point.chats.dialog.viewmodel.ChatDialogAction.UiEvent
import com.point.services.chats.events.models.MessageUi
import com.point.ui.Theme
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
internal fun Message(
    messageUi: MessageUi,
    isFromCurrentUser: Boolean,
    isGroup: Boolean,
    action: (UiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val formattedTime = remember(messageUi.timestamp) {
        DateTimeFormatter.ofPattern("HH:mm")
            .withZone(ZoneId.systemDefault())
            .format(messageUi.timestamp)
    }

    var showMenu by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = { showMenu = true }
            )
            .padding(vertical = 8.dp)
    ) {
        if (isGroup) {
            Text(
                text = messageUi.senderName,
                color = Theme.colorScheme.accent,
                style = Theme.typography.bodyS,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        if (messageUi.attachments.isNotEmpty()) {
            PhotoGrid(messageUi.attachments, modifier = Modifier.width(250.dp))
        }

        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
        ) {
            Text(
                text = messageUi.text,
                color = if (isFromCurrentUser) Color.White else Theme.colorScheme.textPrimary,
                style = Theme.typography.bodyM,
                modifier = Modifier.weight(1f, false)
            )

            if (messageUi.isEdited) {
                Text(
                    text = stringResource(R.string.edited),
                    color = if (isFromCurrentUser) Color.White else Theme.colorScheme.textSecondary,
                    style = Theme.typography.labelM,
                    modifier = Modifier.weight(1f, false),
                )
            }

            Text(
                text = formattedTime,
                color = if (isFromCurrentUser) Color.White else Theme.colorScheme.textSecondary,
                style = Theme.typography.labelM,
                modifier = Modifier.weight(1f, false),
            )
        }

        MessageItemMenu(
            id = messageUi.id,
            text = messageUi.text,
            visible = showMenu,
            onDismiss = { showMenu = false },
            action = action,
            modifier = Modifier.background(
                color = Theme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            )
        )
    }
}

@Composable
private fun MessageItemMenu(
    id: String,
    text: String,
    visible: Boolean,
    onDismiss: () -> Unit,
    action: (UiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownMenu(
        expanded = visible,
        onDismissRequest = onDismiss,
        modifier = modifier,
    ) {
        DropdownMenuItem(
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null
                )
            },
            text = {
                Text(text = stringResource(R.string.edit), style = Theme.typography.bodyS)
            },
            onClick = {
                action(UiEvent.EditMessage(id = id, text = text))
                onDismiss()
            }
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        )

        DropdownMenuItem(
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null
                )
            },
            text = {
                Text(text = stringResource(R.string.delete), style = Theme.typography.bodyS)
            },
            onClick = {
                action(UiEvent.DeleteMessage(id = id))
                onDismiss()
            }
        )
    }
}