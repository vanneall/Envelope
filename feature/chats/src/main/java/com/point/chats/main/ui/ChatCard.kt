package com.point.chats.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.point.chats.main.viewmodel.ChatAction.UiAction
import com.point.chats.main.viewmodel.items.ChatUi
import com.point.ui.Theme
import com.point.ui.components.user.UserCard
import com.point.ui.components.user.UserCardInfo
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun ChatCard(chatUi: ChatUi, onAction: (UiAction) -> Unit, inEditMode: Boolean, modifier: Modifier = Modifier) {
    UserCard(
        UserCardInfo(
            name = chatUi.name,
            photo = chatUi.photoUrl,
            supportText = chatUi.lastMessage?.text,
        ),
        modifier = modifier,
        trailing = {
            when {
                inEditMode -> {
                    Checkbox(
                        checked = chatUi.checked,
                        onCheckedChange = { checked -> onAction(UiAction.OnChatCheckedChange(chatUi.id, checked)) },
                    )
                }

                chatUi.lastMessage != null -> {
                    val formattedTime = remember(chatUi.lastMessage.timestamp) {
                        DateTimeFormatter.ofPattern("HH:mm")
                            .withZone(ZoneId.systemDefault())
                            .format(chatUi.lastMessage.timestamp)
                    }

                    Text(
                        text = formattedTime,
                        style = Theme.typography.bodyS,
                        color = Theme.colorScheme.textSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                else -> {
                    Text(
                        text = "новый",
                        style = Theme.typography.labelL,
                        color = Color.White,
                        modifier = Modifier
                            .background(
                                color = Theme.colorScheme.accent,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(4.dp)
                    )
                }
            }
        },
    )
}