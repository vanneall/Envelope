package com.point.chats.creation.group.confirm.mvi.state

import android.net.Uri
import androidx.compose.runtime.Immutable
import com.point.chats.creation.single.mvi.state.UserUi

@Immutable
internal data class GroupChatConfirmState(
    val chatName: String = "",
    val chatPhoto: Uri? = null,
    val users: List<UserUi> = emptyList(),
)