package com.point.chats.dialog.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.point.chats.dialog.data.events.MessageSentEvent
import com.point.chats.dialog.viewmodel.ChatDialogAction
import com.point.chats.dialog.viewmodel.ChatDialogState
import com.point.ui.Theme
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun ChatDialogScreenContent(
    state: ChatDialogState,
    onAction: (ChatDialogAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
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
                            modifier = Modifier
                                .background(
                                    color = Theme.colorScheme.surface,
                                    shape = RoundedCornerShape(
                                        topStart = 12.dp,
                                        topEnd = 12.dp,
                                        bottomEnd = 12.dp,
                                        bottomStart = 0.dp
                                    )
                                )
                                .clip(RoundedCornerShape(12.dp))
                                .widthIn(max = screenWidth / 0.5f)
                        )
                    }

                    else -> {}
                }

            }
        }

        val photoPicker = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) {
            onAction(ChatDialogAction.UiAction.OnPhotoPicked(photos = it))
        }

        AnimatedVisibility(
            visible = state.photos.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
        ) {
            val scrollableState = rememberScrollState()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .horizontalScroll(state = scrollableState)
                    .fillMaxWidth()
                    .background(
                        color = Theme.colorScheme.surface,
                        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                    ),
            ) {
                val context = LocalContext.current
                state.photos.forEach { uri ->
                    Box(
                        modifier = Modifier.size(100.dp)
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(uri)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize(),
                        )

                        val interactionSource = remember { MutableInteractionSource() }
                        Icon(
                            imageVector = Icons.Default.Clear,
                            tint = Color.White,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 4.dp, top = 4.dp)
                                .size(20.dp)
                                .background(color = Theme.colorScheme.accent, shape = CircleShape)
                                .clip(CircleShape)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = rememberRipple(color = Color.Black)
                                ) {
                                    onAction(ChatDialogAction.UiAction.OnPhotoDeletedFromMessage(uri))
                                }
                                .align(Alignment.TopEnd)
                                .padding(4.dp)
                        )
                    }
                }
            }
        }
        TextField(
            value = state.message,
            onValueChange = { onAction(ChatDialogAction.TypeMessage(it)) },
            singleLine = true,
            placeholder = { Text(text = "Сообщение") },
            trailingIcon = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 12.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onAction(ChatDialogAction.Send) },
                    )

                    val interactionSource = remember { MutableInteractionSource() }
                    Icon(
                        imageVector = Icons.Default.AttachFile,
                        contentDescription = null,
                        modifier = Modifier
                            .rotate(225f)
                            .size(40.dp)
                            .clip(CircleShape)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = rememberRipple(color = Color.Black),
                            ) {
                                photoPicker.launch(
                                    PickVisualMediaRequest(
                                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly,
                                        maxItems = 10,
                                    )
                                )
                            }
                            .padding(8.dp)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun Message(
    message: MessageSentEvent,
    onAction: (ChatDialogAction) -> Unit,
    modifier: Modifier = Modifier,
) {
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
            .clickable(
                interactionSource = interactionSource,
                indication = ripple,
                onClick = { showMenu = true })
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = message.userName,
            color = Theme.colorScheme.accent,
            style = Theme.typography.bodyS,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))

        if (message.attachments.isNotEmpty()) {
            CompactPhotoGrid(message.attachments)
        }


        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
        ) {
            Text(
                text = message.text,
                color = Theme.colorScheme.textPrimary,
                style = Theme.typography.bodyL,
                modifier = Modifier.weight(1f, false)
            )

            if (message.isEdited) {
                Text(
                    text = "изменено",
                    color = Theme.colorScheme.textSecondary,
                    style = Theme.typography.labelM,
                    modifier = Modifier.weight(1f, false),
                )
            }

            Text(
                text = formattedTime,
                color = Theme.colorScheme.textSecondary,
                style = Theme.typography.labelM,
                modifier = Modifier.weight(1f, false),
            )
        }

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
                    onAction(ChatDialogAction.Edit(message.id, message.text))
                    showMenu = false
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
                text = { Text("Удалить") },
                onClick = {
                    onAction(ChatDialogAction.Delete(message.id))
                    showMenu = false
                }
            )
        }
    }
}

@Composable
fun CompactPhotoGrid(attachments: List<Long>) {
    Column(
        modifier = Modifier.width(250.dp)
    ) {
        attachments.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                rowItems.forEach { item ->
                    AsyncImage(
                        model = "http://192.168.0.192:8084/photos/$item",
                        contentDescription = null,
                        modifier = Modifier
                            .weight(1f)
                            .then(
                                if (rowItems.size == 1) {
                                    Modifier.weight(1f)
                                } else {
                                    Modifier.aspectRatio(1f)
                                }
                            )
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}