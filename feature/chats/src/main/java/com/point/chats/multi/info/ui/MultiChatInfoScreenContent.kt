package com.point.chats.multi.info.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.point.chats.multi.info.viewmodel.MultiChatInfoAction
import com.point.chats.multi.info.viewmodel.MultiChatInfoState
import com.point.chats.multi.info.viewmodel.UserInfo
import com.point.services.chats.models.ChatType
import com.point.services.chats.models.UserRole
import com.point.ui.components.user.UserBase
import com.point.ui.components.user.UserCardInfo

@Composable
fun MultiChatInfoScreenContent(
    state: MultiChatInfoState,
    onAction: (MultiChatInfoAction.UiAction) -> Unit,
    modifier: Modifier = Modifier,
) {

    var showModal by remember { mutableStateOf(ModalSelected(false, null)) }

    LazyColumn(modifier = modifier) {
        item { Text(text = state.name) }

        item { Spacer(modifier = Modifier.height(20.dp)) }

        item { Text(text = state.description ?: "Пустое описание") }

        item { Spacer(modifier = Modifier.height(20.dp)) }

        items(state.chatUsers, key = { it.id }) {
            com.point.ui.components.user.UserTextCard(
                user = UserCardInfo(
                    userBase = UserBase(it.name),
                    null,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        showModal = ModalSelected(true, it)
                    })
        }
    }

    if (showModal.show) {
        UserInfoBottomSheet(
            showModal.user!!,
            { showModal = ModalSelected(false, null) },
            onAction = onAction,
            modifier = Modifier.fillMaxSize()
        )
    }
}

data class ModalSelected(
    val show: Boolean,
    val user: UserInfo?,
)

@Composable
@Preview
fun MultiChatInfoScreenContentPreview() {
    MultiChatInfoScreenContent(
        state = MultiChatInfoState(
            id = "1",
            name = "multi chat",
            description = "description",
            type = ChatType.MANY,
            chatPreviewPhotos = emptyList(),
            mediaContentIds = emptyList(),
            chatUsers = listOf(
                UserInfo(
                    id = "1",
                    name = "User #1",
                    photoId = null,
                    userRole = UserRole(
                        name = "admin",
                    )
                ),
                UserInfo(
                    id = "2",
                    name = "User #2",
                    photoId = null,
                    userRole = UserRole(
                        name = "simple",
                    )
                ),
            )
        ),
        {},
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
}


