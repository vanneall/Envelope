package com.point.chats.multi.info.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.point.chats.R
import com.point.chats.multi.info.viewmodel.MultiChatInfoAction
import com.point.chats.multi.info.viewmodel.MultiChatInfoState
import com.point.chats.multi.info.viewmodel.UserInfo
import com.point.navigation.Route
import com.point.services.chats.models.ChatType
import com.point.services.chats.models.UserRole
import com.point.ui.Theme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MultiChatInfoScreenContent(
    state: MultiChatInfoState,
    navigate: (Route) -> Unit,
    onAction: (MultiChatInfoAction.UiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showModal by remember { mutableStateOf(ModalSelected(false, null)) }

    LazyColumn(modifier = modifier) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                AsyncImage(
                    model = state.chatPreviewPhotos.firstOrNull(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    error = painterResource(R.drawable.ic_person_error_24),
                    fallback = painterResource(R.drawable.ic_person_default_24),
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape),
                )

                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = state.name,
                        style = Theme.typography.titleS,
                    )
                    Text(
                        text = pluralStringResource(
                            R.plurals.participants_count,
                            state.chatUsers.size,
                            state.chatUsers.size
                        ),
                        style = Theme.typography.bodyS,
                        color = Theme.colorScheme.textSecondary,
                    )
                }
            }
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }

        if (state.description != null) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = stringResource(R.string.description), style = Theme.typography.titleS)
                    Text(text = state.description, style = Theme.typography.bodyM)
                }
            }
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }

        item {
            Text(
                text = stringResource(R.string.participants),
                style = Theme.typography.titleS,
                modifier = Modifier.padding(bottom = 12.dp),
            )
        }
        items(
            items = state.chatUsers,
            key = { it.id },
        ) { item ->
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .combinedClickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(),
                            onClick = { navigate(Route.ContactsFeature.UserProfile(item.id)) },
                            onLongClick = { showModal = ModalSelected(true, item) }
                        )
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AsyncImage(
                        model = item.photoId,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        error = painterResource(R.drawable.ic_person_error_24),
                        fallback = painterResource(R.drawable.ic_person_default_24),
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape),
                    )

                    Text(
                        text = item.name,
                        style = Theme.typography.bodyM,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = { showModal = ModalSelected(true, item) }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Edit,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }

    if (showModal.show) {
        UserInfoBottomSheet(
            showModal.user!!,
            { showModal = ModalSelected(false, null) },
            onAction = onAction,
            modifier = Modifier
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
        {},
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
}


