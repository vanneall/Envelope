package com.point.chats.chats.mvi.state

import androidx.compose.runtime.Immutable
import com.point.services.chats.models.ChatInfo
import com.point.services.chats.models.ChatType
import com.point.services.chats.models.MessageType
import java.time.Instant


@Immutable
internal data class ChatState(
    val chats: List<ChatUi> = emptyList(),
    val mode: ChatMode = ChatMode.Idle,
) {
    val isEdit get() = mode is ChatMode.Edit

    fun isChecked(id: String) = if (isEdit) (mode as ChatMode.Edit).selected.contains(id) else false
}

@Immutable
internal data class ChatUi(
    val id: String,
    val name: String,
    val type: ChatType,
    val photo: String?,
    val lastChatEventUi: ChatEventUi,
)

internal data class ChatEventUi(
    val message: String,
    val type: MessageType,
    val timestamp: Instant,
)

@Immutable
internal sealed interface ChatMode {

    data object Idle : ChatMode

    data class Edit(val selected: Set<String>) : ChatMode
}

internal fun ChatInfo.toChatsUi() = ChatUi(
    id = id,
    name = name,
    photo = photo,
    type = type,
    lastChatEventUi = ChatEventUi(
        message = lastMessage?.text ?: "",
        type = lastMessage?.type ?: MessageType.CREATED,
        timestamp = lastMessage?.timestamp ?: Instant.now(),
    ),
)