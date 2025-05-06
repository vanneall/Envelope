package com.point.contacts.main.presenter.ui

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
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.point.contacts.R
import com.point.contacts.main.presenter.viewmodel.Contact
import com.point.contacts.main.presenter.viewmodel.ContactState
import com.point.contacts.main.presenter.viewmodel.ContactsActions
import com.point.navigation.Route
import com.point.ui.Theme
import com.point.ui.colors.Black

@Composable
fun ContactsScreenContent(
    state: ContactState,
    action: (ContactsActions) -> Unit,
    navigate: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        state.contacts.forEach { (group, contacts) ->
            item {
                Text(
                    text = group,
                    style = Theme.typography.bodyM,
                    fontWeight = FontWeight.Medium,
                    color = Black,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }
            items(
                items = contacts,
                key = { contact -> contact.username }
            ) { item ->
                UserCard(
                    contact = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(),
                            onClick = { navigate(Route.ContactsFeature.UserProfile(item.username)) }
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .animateItem()
                )
            }
        }
    }
}


@Composable
internal fun UserCard(
    contact: Contact,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.height(intrinsicSize = IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        AsyncImage(
            model = contact.photo,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.ic_person_error_24),
            fallback = painterResource(R.drawable.ic_person_default_24),
        )

        ChatDescription(
            title = contact.name,
            text = contact.status,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        )
    }
}

@Composable
internal fun ChatDescription(title: String, text: String?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.CenterVertically),
    ) {
        Text(
            text = title,
            style = Theme.typography.titleL,
            color = TextPrimaryColor,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )

        if (!text.isNullOrEmpty()) {
            Text(
                text = text,
                style = Theme.typography.bodyS,
                color = TextSecondaryColor,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
            )
        }
    }
}

val TextPrimaryColor = Color(0xFF1E1E1E)
val TextSecondaryColor = Color(0xFFA2A2A2)