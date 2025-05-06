package com.point.chats.creation.group.confirm.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.point.chats.R
import com.point.chats.creation.group.confirm.mvi.actions.GroupChatConfirmAction
import com.point.chats.creation.group.confirm.mvi.state.GroupChatConfirmState
import com.point.chats.creation.single.ui.UserCard
import com.point.ui.Theme

@Composable
internal fun ChatGroupConfirmScreenContent(
    state: GroupChatConfirmState,
    action: (GroupChatConfirmAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(color = Color(0xFFF6F6F6), shape = RoundedCornerShape(20.dp))
                    .padding(horizontal = 16.dp, vertical = 20.dp),
            ) {
                val photoPicker = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                    uri?.let { action(GroupChatConfirmAction.UiEvent.PhotoSelected(uri)) }
                }

                AsyncImage(
                    model = state.chatPhoto,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(color = Color.Gray)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(),
                            onClick = { photoPicker.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)) }
                        )
                )

                TextField(
                    value = state.chatName,
                    onValueChange = { action(GroupChatConfirmAction.UiEvent.NameEntered(name = it)) },
                    textStyle = TextStyle(
                        fontSize = 16.sp
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.group_name),
                            style = Theme.typography.bodyL,
                            color = Color(0xFFA2A2A2)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )
            }
        }


        items(
            items = state.users,
            key = { it.username }
        ) { item ->
            UserCard(
                userUi = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem()
            )
        }
    }
}