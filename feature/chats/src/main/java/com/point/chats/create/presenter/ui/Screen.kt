package com.point.chats.create.presenter.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.point.chats.create.presenter.viewmodel.Contact
import com.point.chats.create.presenter.viewmodel.CreateChatAction
import com.point.chats.create.presenter.viewmodel.CreateChatEvent
import com.point.chats.create.presenter.viewmodel.CreateChatState
import com.point.chats.main.ui.UserPhoto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ContactsScreen(
    state: CreateChatState,
    events: Flow<CreateChatEvent>,
    onAction: (CreateChatAction) -> Unit,
    onNavigate: () -> Unit,
    onPopBackState: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(modifier = modifier) {
        TextField(
            value = state.search,
            onValueChange = { onAction(CreateChatAction.Action.OnValueChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            trailingIcon = {
                Row {

                    if (state.search.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            modifier = Modifier
                                .size(16.dp)
                                .clickable {
                                    onAction(CreateChatAction.Action.OnValueChanged(""))
                                }
                        )

                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(
                count = state.contacts.size,
                key = { state.contacts[it].id },
            ) {
                ChatComposable(
                    contact = state.contacts[it],
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth()
                        .clickable { onAction(CreateChatAction.Action.CreateChatWithContact(state.contacts[it].id)) },
                )

                if (it != state.contacts.lastIndex) {
                    VerticalDivider(
                        thickness = 1.dp,
                        color = Color.LightGray,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        events.collectLatest {
            onPopBackState()
        }
    }
}

@Composable
private fun ChatComposable(contact: Contact, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(color = Color.White)
            .padding(vertical = 8.dp),
    ) {
        UserPhoto(
            color = contact.image,
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
                text = contact.name,
                fontSize = 14.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}