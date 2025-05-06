package com.point.chats.chats.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.point.chats.R
import com.point.chats.chats.mvi.actions.ChatsAction.UiEvent
import com.point.chats.chats.mvi.state.ChatEventUi
import com.point.chats.chats.mvi.state.ChatMode
import com.point.chats.chats.mvi.state.ChatUi
import com.point.services.chats.models.ChatType
import com.point.services.chats.models.MessageType
import com.point.ui.Theme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
internal fun ChatEditable(
    chatUi: ChatUi,
    chatMode: ChatMode,
    action: (UiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.height(intrinsicSize = IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        if (chatUi.type == ChatType.PRIVATE) {
            Icon(
                imageVector = Icons.Rounded.BookmarkBorder,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(color = Theme.colorScheme.accent)
                    .padding(8.dp),
            )
        } else {
            AsyncImage(
                model = chatUi.photo,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.ic_person_error_24),
                fallback = painterResource(R.drawable.ic_person_default_24),
            )
        }

        ChatDescription(
            title = if (chatUi.type == ChatType.PRIVATE) stringResource(R.string.bookmars) else chatUi.name,
            text = chatUi.lastChatEventUi.message,
            type = chatUi.lastChatEventUi.type,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        )

        when (chatMode) {
            is ChatMode.Idle -> {
                Column(modifier = Modifier.fillMaxHeight(), horizontalAlignment = Alignment.End) {
                    TimeText(instant = chatUi.lastChatEventUi.timestamp)
                }
            }

            is ChatMode.Edit -> {
                Box(contentAlignment = Alignment.Center) {
                    Checkbox(
                        checked = chatUi.id in chatMode.selected,
                        onCheckedChange = { checked -> action(UiEvent.SelectChat(id = chatUi.id, checked)) },
                        modifier = Modifier.size(24.dp),
                    )
                }
            }
        }
    }
}

@Composable
internal fun ChatDescription(title: String, type: MessageType, text: String?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.CenterVertically),
    ) {
        Text(
            text = title,
            style = Theme.typography.bodyL,
            fontWeight = FontWeight.Medium,
            color = TextPrimaryColor,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )

        when (type) {
            MessageType.IMAGE -> "Фото"
            MessageType.CREATED -> "Чат создан"
            MessageType.TEXT -> text
        }?.let {
            Text(
                text = it,
                style = Theme.typography.bodyS,
                color = TextSecondaryColor,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
            )
        }
    }
}

@Composable
internal fun TimeText(instant: Instant, modifier: Modifier = Modifier) {
    val zoneId = remember { ZoneId.systemDefault() }
    val localDateTime = remember(instant) { instant.atZone(zoneId).toLocalDateTime() }
    val today = remember { LocalDate.now(zoneId) }

    val formattedText = remember(localDateTime) {
        if (localDateTime.toLocalDate().isEqual(today)) {
            localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        } else {
            localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        }
    }

    Text(
        text = formattedText,
        style = Theme.typography.bodyS,
        color = TextSecondaryColor,
        maxLines = 1,
        modifier = modifier,
    )
}

val TextPrimaryColor = Color(0xFF1E1E1E)
val TextSecondaryColor = Color(0xFFA2A2A2)

@Preview
@Composable
private fun ChatPreview() {
    Box(modifier = Modifier.background(color = Color.LightGray)) {
        ChatEditable(
            chatUi = ChatUi(
                id = "1",
                name = "User chat",
                photo = null,
                type = ChatType.ONE_ON_ONE,
                lastChatEventUi = ChatEventUi(
                    message = "User message",
                    type = MessageType.TEXT,
                    timestamp = Instant.now(),
                ),
            ),
            chatMode = ChatMode.Idle,
            action = { },
            modifier = Modifier
                .width(400.dp)
                .background(Color.White)
        )
    }
}

@Preview
@Composable
private fun ChatSelectedPreview() {
    Box(modifier = Modifier.background(color = Color.LightGray)) {
        ChatEditable(
            chatUi = ChatUi(
                id = "1",
                name = "User chat",
                photo = null,
                type = ChatType.ONE_ON_ONE,
                lastChatEventUi = ChatEventUi(
                    message = "User message",
                    type = MessageType.TEXT,
                    timestamp = Instant.now(),
                ),
            ),
            chatMode = ChatMode.Edit(selected = setOf("1")),
            action = { },
            modifier = Modifier
                .width(400.dp)
                .background(Color.White)
        )
    }
}

@Preview
@Composable
private fun ChatNotSelectedPreview() {
    Box(modifier = Modifier.background(color = Color.LightGray)) {
        ChatEditable(
            chatUi = ChatUi(
                id = "1",
                name = "User chat",
                photo = null,
                type = ChatType.ONE_ON_ONE,
                lastChatEventUi = ChatEventUi(
                    message = "User message",
                    type = MessageType.TEXT,
                    timestamp = Instant.now(),
                ),
            ),
            chatMode = ChatMode.Edit(selected = setOf()),
            action = { },
            modifier = Modifier
                .width(400.dp)
                .background(Color.White)
        )
    }
}