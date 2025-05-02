package com.point.chats.creation.single.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Group
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.point.chats.R
import com.point.chats.chats.ui.ChatDescription
import com.point.chats.creation.single.mvi.actions.ChatCreationAction.UiEvent
import com.point.chats.creation.single.mvi.state.ChatCreationState
import com.point.chats.creation.single.mvi.state.UserUi
import com.point.navigation.Route
import com.point.services.chats.models.MessageType
import com.point.ui.colors.Black

@Composable
internal fun ChatCreationScreenContent(
    state: ChatCreationState,
    action: (UiEvent) -> Unit,
    navigate: (Route) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item {
            ChatCreationControls(
                navigate = navigate,
                modifier = Modifier.fillMaxWidth()
            )
        }

        state.users.forEach { (group, contacts) ->
            item {
                Text(
                    text = group,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                    ),
                    color = Black,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }
            items(
                items = contacts,
                key = { contact -> contact.username }
            ) { item ->
                UserCard(
                    userUi = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(),
                            onClick = { action(UiEvent.ContactSelected(item.username)) }
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .animateItem()
                )
            }
        }
    }
}

@Composable
private fun ChatCreationControls(navigate: (Route) -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        IconText(
            icon = Icons.Rounded.Group,
            title = stringResource(R.string.create_group_chat),
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClick = { navigate(Route.ChatsFeature.ChatsGroupCreation) }
                )
                .padding(horizontal = 16.dp, vertical = 10.dp)
        )

        IconText(
            icon = Icons.Rounded.Group,
            title = stringResource(R.string.add_contact),
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClick = { navigate(Route.ContactsFeature.ContactsSearch) }
                )
                .padding(horizontal = 16.dp, vertical = 10.dp)
        )
    }
}

@Composable
private fun IconText(icon: ImageVector, title: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .padding(4.dp)
        )

        Text(
            text = title,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
            ),
            color = Black,
        )
    }
}

@Composable
internal fun UserCard(
    userUi: UserUi,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.height(intrinsicSize = IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        AsyncImage(
            model = userUi.photo,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.ic_person_error_24),
            fallback = painterResource(R.drawable.ic_person_default_24),
        )

        ChatDescription(
            title = userUi.name,
            text = userUi.status,
            type = MessageType.TEXT,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        )
    }
}
