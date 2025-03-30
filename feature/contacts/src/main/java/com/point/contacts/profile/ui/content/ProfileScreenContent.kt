package com.point.contacts.profile.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonOff
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.point.contacts.R
import com.point.contacts.profile.viewmodel.ProfileAction
import com.point.contacts.profile.viewmodel.ProfileState
import com.point.ui.Theme

@Composable
internal fun ProfileScreenContent(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollableState = rememberScrollState()
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.verticalScroll(scrollableState),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            UserAvatar(null, modifier = Modifier.size(120.dp))

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = state.name,
                style = Theme.typography.titleL,
                color = Theme.colorScheme.textPrimary
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "@${state.username}",
                style = Theme.typography.labelL,
                color = Theme.colorScheme.textSecondary,
            )
        }

        ActionsRow(
            state = state,
            onAction = onAction,
            modifier = Modifier.fillMaxWidth(),
        )

        Text(
            text = stringResource(R.string.information),
            style = Theme.typography.bodyL,
            color = Theme.colorScheme.accent,
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Theme.colorScheme.surface, shape = RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            if (state.status != null) {
                UserDescriptionItem(
                    title = stringResource(R.string.status),
                    description = state.status,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (state.status != null && state.about != null) {
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
            }

            if (state.about != null) {

                UserDescriptionItem(
                    title = stringResource(R.string.about),
                    description = state.about,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun ActionsRow(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            space = 12.dp,
            alignment = Alignment.CenterHorizontally,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (state.showSendMessage) {
            ActionButton(
                text = "Сообщение",
                icon = Icons.Default.Message,
                onClick = {},
                modifier = Modifier
                    .defaultMinSize(minHeight = 60.dp)
                    .background(
                        color = Theme.colorScheme.surface,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clip(RoundedCornerShape(12.dp))
                    .padding(8.dp)
            )
        }

        if (state.showDeleteFromContacts) {
            ActionButton(
                text = "Удалить",
                icon = Icons.Default.PersonOff,
                onClick = { onAction(ProfileAction.DeleteFromContacts) },
                modifier = Modifier
                    .defaultMinSize(minHeight = 60.dp)
                    .background(
                        color = Theme.colorScheme.surface,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clip(RoundedCornerShape(12.dp))
                    .padding(8.dp)
            )
        }

        if (state.showAddToContacts) {
            ActionButton(
                text = "Добавить",
                icon = Icons.Default.PersonAdd,
                onClick = { onAction(ProfileAction.AddContact) },
                modifier = Modifier
                    .defaultMinSize(minHeight = 60.dp)
                    .background(
                        color = Theme.colorScheme.surface,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clip(RoundedCornerShape(12.dp))
                    .padding(8.dp)
            )
        } else if (state.showSentRequest) {
            ActionButton(
                text = "Отправлено",
                icon = Icons.Default.MarkEmailRead,
                onClick = {},
                modifier = Modifier
                    .defaultMinSize(minHeight = 60.dp)
                    .background(
                        color = Theme.colorScheme.surface,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clip(RoundedCornerShape(12.dp))
                    .padding(8.dp)
            )
        }

        ActionButton(
            text = "Заблокировать",
            icon = Icons.Default.Block,
            onClick = {},
            modifier = Modifier
                .background(
                    color = Theme.colorScheme.surface,
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(RoundedCornerShape(12.dp))
                .padding(8.dp)
        )
    }
}

@Composable
private fun ActionButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val ripple = rememberRipple(color = Color.Black)
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = ripple,
                onClick = onClick,
            )
            .then(modifier)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Theme.colorScheme.accent,
        )

        Text(
            text = text,
            style = Theme.typography.bodyM,
            color = Theme.colorScheme.accent,
        )
    }
}

