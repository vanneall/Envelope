@file:OptIn(ExperimentalLayoutApi::class)

package com.point.chats.creation.group.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.point.chats.R
import com.point.chats.chats.ui.ChatDescription
import com.point.chats.creation.group.mvi.actions.ChatCreationGroupAction.UiEvent
import com.point.chats.creation.group.mvi.state.ChatCreationGroupState
import com.point.chats.creation.single.mvi.state.UserUi
import com.point.services.chats.models.MessageType
import com.point.ui.Theme
import com.point.ui.colors.Black

@Composable
internal fun ChatCreationGroupScreenContent(
    state: ChatCreationGroupState,
    action: (UiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            state.users.flatMap { it.value }.filter { it.username in state.selected }.forEach { user ->
                UserChips(
                    userUi = user,
                    modifier = Modifier
                        .height(22.dp)
                        .background(color = Color(0xFFEBEBEB), shape = RoundedCornerShape(20.dp))
                        .padding(end = 6.dp)
                )
            }

            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier.height(22.dp),
            ) {
                Text(
                    text = stringResource(R.string.invite_to_invite),
                    style = Theme.typography.bodyS,
                    color = Black,
                )
            }
        }
        LazyColumn(modifier = modifier) {
            state.users.forEach { (group, contacts) ->
                item {
                    Text(
                        text = group,
                        style = Theme.typography.bodyM,
                        color = Black,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                    )
                }
                items(
                    items = contacts,
                    key = { contact -> contact.username }
                ) { item ->
                    UserCardSelectable(
                        userUi = item,
                        selected = item.username in state.selected,
                        action = action,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(),
                                onClick = { action(UiEvent.PickUser(item.username)) }
                            )
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                            .animateItem()
                    )
                }
            }
        }
    }
}

@Composable
internal fun UserChips(userUi: UserUi, modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        AsyncImage(
            model = userUi.photo,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            fallback = painterResource(R.drawable.ic_person_default_24),
            error = painterResource(R.drawable.ic_person_error_24),
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape),
        )

        Text(
            text = userUi.name,
            style = Theme.typography.bodyS,
            color = Black,
        )
    }
}

@Composable
internal fun UserCardSelectable(
    userUi: UserUi,
    selected: Boolean,
    action: (UiEvent) -> Unit,
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

        Checkbox(
            checked = selected,
            onCheckedChange = { action(UiEvent.PickUser(userUi.username)) }
        )
    }
}