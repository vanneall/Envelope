package com.point.chats.main.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.point.chats.main.viewmodel.Chat
import com.point.chats.main.viewmodel.ChatAction
import com.point.chats.main.viewmodel.ChatEvents
import com.point.chats.main.viewmodel.ChatsState
import com.point.ui.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun ChatsScreen(
    state: ChatsState,
    events: Flow<ChatEvents>,
    onAction: (ChatAction) -> Unit,
    onNavigate: () -> Unit,
    onNavigateToChat: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(48.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .background(Theme.colorScheme.accent, shape = RoundedCornerShape(16.dp))
                    .clickable { onNavigate() }
                    .padding(8.dp)
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            items(
                count = state.chats.size,
                key = { index -> state.chats[index].id },
            ) {
                ChatComposable(
                    chat = state.chats[it],
                    modifier = Modifier
                        .height(60.dp)
                        .clickable { onNavigateToChat(state.chats[it].id) },
                )

                if (it != state.chats.lastIndex) {
                    VerticalDivider(
                        thickness = 1.dp,
                        color = Color.LightGray,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        events.collect {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG)
        }
    }
}

@Composable
private fun ChatComposable(chat: Chat, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(color = Color.White)
            .padding(vertical = 8.dp),
    ) {
        UserPhoto(
            color = chat.image,
            modifier = Modifier.size(40.dp)
        )

        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
        ) {
            Text(
                text = chat.name,
                fontSize = 14.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                text = chat.lastMessage,
                fontSize = 12.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.Normal,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
fun UserPhoto(color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(color = color, shape = CircleShape)
    )
}

@Preview
@Composable
fun ChatPreview() {
    ChatComposable(
        chat = Chat(
            name = "My dialog",
            image = Color.Red,
            lastMessage = "It's my last message in this dialog"
        ),
        modifier = Modifier.height(60.dp)
    )
}

@Preview
@Composable
fun ChatScreenPreview() {
    ChatsScreen(
        state = ChatsState(
            chats = listOf(
                Chat(
                    id = "1",
                    name = "My dialog",
                    image = Theme.colorScheme.accent,
                    lastMessage = "It's my last very very very big message in this dialog"
                ),
                Chat(
                    id = "2",
                    name = "My dialog",
                    image = Theme.colorScheme.accent,
                    lastMessage = "It's my last message in this dialog"
                ),
                Chat(
                    id = "3",
                    name = "My dialog",
                    image = Theme.colorScheme.accent,
                    lastMessage = "It's my last message in this dialog"
                ),
                Chat(
                    id = "4",
                    name = "My dialog",
                    image = Theme.colorScheme.accent,
                    lastMessage = "It's my last message in this dialog"
                ),
                Chat(
                    id = "5",
                    name = "My dialog",
                    image = Theme.colorScheme.accent,
                    lastMessage = "It's my last message in this dialog"
                )
            )
        ),
        onAction = {},
        modifier = Modifier.size(300.dp, 560.dp),
        events = emptyFlow(),
        onNavigate = {},
        onNavigateToChat = {},
    )
}