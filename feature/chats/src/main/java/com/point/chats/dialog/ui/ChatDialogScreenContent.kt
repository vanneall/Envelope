package com.point.chats.dialog.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.point.chats.dialog.viewmodel.ChatDialogAction.UiEvent
import com.point.chats.dialog.viewmodel.ChatDialogState
import com.point.services.chats.events.models.MessageUi
import com.point.services.chats.models.ChatType
import com.point.ui.LocalUser
import com.point.ui.Theme

@Composable
internal fun ChatDialogScreenContent(
    state: ChatDialogState,
    action: (UiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Column(
        modifier = modifier.padding(
            WindowInsets.ime.exclude(WindowInsets.navigationBars).asPaddingValues()
        )
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            val listState = rememberLazyListState()

            LaunchedEffect(state.events.size) {
                if (state.events.isNotEmpty()) {
                    listState.animateScrollToItem(0)
                }
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 12.dp),
                reverseLayout = true,
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .align(Alignment.BottomCenter)
            ) {
                items(
                    items = state.events,
                    key = { it.id }
                ) { item ->
                    when (item) {
                        is MessageUi -> {
                            val messageFromCurrentUser = requireNotNull(LocalUser.current?.username) == item.sender
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .animateItem()) {
                                Message(
                                    messageUi = item,
                                    isFromCurrentUser = messageFromCurrentUser,
                                    isGroup = state.chatType == ChatType.MANY,
                                    action = action,
                                    modifier = Modifier
                                        .background(
                                            color = if (messageFromCurrentUser) {
                                                Theme.colorScheme.accent
                                            } else {
                                                Theme.colorScheme.surface
                                            },
                                            shape = RoundedCornerShape(
                                                topStart = 12.dp,
                                                topEnd = 12.dp,
                                                bottomEnd = if (messageFromCurrentUser) {
                                                    0.dp
                                                } else {
                                                    12.dp
                                                },
                                                bottomStart = if (messageFromCurrentUser) {
                                                    12.dp
                                                } else {
                                                    0.dp
                                                }
                                            )
                                        )
                                        .clip(RoundedCornerShape(20.dp))
                                        .widthIn(max = screenWidth / 0.5f)
                                        .align(if (messageFromCurrentUser) Alignment.CenterEnd else Alignment.CenterStart)
                                )
                            }
                        }

                        else -> {}
                    }
                }
            }
        }

        val photoPicker = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) {
            action(UiEvent.OnPhotoPicked(photos = it))
        }

        MessageField(
            text = state.message,
            onTextChange = { action(UiEvent.TypeMessage(it)) },
            onAdd = {
                photoPicker.launch(
                    PickVisualMediaRequest(
                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly,
                        maxItems = 10,
                        isOrderedSelection = true,
                    )
                )
            },
            onSend = { action(UiEvent.SendMessage) },
            onDelete = { action(UiEvent.OnPhotoDeletedFromMessage(it)) },
            content = state.photos,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xffEBEBEB), RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .padding(top = 8.dp),
        )
    }
}
