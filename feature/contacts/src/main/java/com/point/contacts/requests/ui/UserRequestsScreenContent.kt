package com.point.contacts.requests.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.point.contacts.R
import com.point.contacts.main.presenter.ui.TextPrimaryColor
import com.point.contacts.main.presenter.viewmodel.Contact
import com.point.contacts.requests.viewModel.RequestsAction.UiAction
import com.point.contacts.requests.viewModel.RequestsState
import com.point.navigation.Route
import com.point.ui.materials.buttons.styled.ButtonStyle
import com.point.ui.materials.buttons.styled.PrimaryButton
import com.point.ui.materials.buttons.styled.SecondaryButton

@Composable
internal fun UserRequestsScreenContent(
    state: RequestsState,
    onAction: (UiAction) -> Unit,
    onNavigation: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        SegmentedSwitch(state.selected, onSelect = { onAction(UiAction.Select(it)) })

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            items(
                items = state.contacts,
                key = { request -> request.id }
            ) { request ->
                UserCard(
                    contact = Contact(
                        username = request.username,
                        name = request.userBase.userBase.name,
                        photo = request.userBase.userBase.photo,
                    ),
                    onPrimaryClick = { onAction(UiAction.AcceptRequest(request.id)) },
                    onSecondaryClick = { onAction(UiAction.DenyRequest(request.id)) },
                    onTertiary = { onAction(UiAction.Cancel(request.id)) },
                    isIncoming = state.selected == 0,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(),
                            onClick = { onNavigation(Route.ContactsFeature.UserProfile(request.username)) }
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .animateItem()
                )
            }
        }
    }
}

@Composable
internal fun UserCard(
    contact: Contact,
    isIncoming: Boolean,
    onPrimaryClick: () -> Unit,
    onSecondaryClick: () -> Unit,
    onTertiary: () -> Unit,
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

        ChatDescriptionButton(
            title = contact.name,
            onPrimaryClick = onPrimaryClick,
            onSecondaryClick = onSecondaryClick,
            onTertiary = onTertiary,
            isIncoming = isIncoming,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        )
    }
}

@Composable
internal fun ChatDescriptionButton(
    title: String,
    isIncoming: Boolean,
    onTertiary: () -> Unit,
    onPrimaryClick: () -> Unit,
    onSecondaryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.CenterVertically),
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
            ),
            color = TextPrimaryColor,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 4.dp),
        ) {
            if (isIncoming) {
                PrimaryButton(
                    onClick = onPrimaryClick,
                    text = stringResource(R.string.accept_request),
                    buttonStyle = ButtonStyle.S,
                )
            }

            SecondaryButton(
                onClick = onSecondaryClick,
                text = if (isIncoming) stringResource(R.string.reject_request) else stringResource(R.string.cancel),
                buttonStyle = ButtonStyle.S,
            )
        }
    }
}

@Composable
fun SegmentedSwitch(
    selected: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val titles = listOf("Заявки", "Отправленные")

    Row(
        modifier = modifier
            .background(Color(0xFFF5F5F5), RoundedCornerShape(24.dp))
            .padding(4.dp)
            .height(40.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        titles.forEachIndexed { index, title ->
            val isSelected = index == selected
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isSelected) Color(0xFF1C1C1C) else Color.Transparent)
                    .clickable { onSelect(index) }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    color = if (isSelected) Color.White else Color.Black,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
