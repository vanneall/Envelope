package com.point.chats.multi.info.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.point.chats.R
import com.point.chats.multi.info.viewmodel.MultiChatInfoAction
import com.point.chats.multi.info.viewmodel.UserInfo
import com.point.ui.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoBottomSheet(
    userInfo: UserInfo,
    onDismiss: () -> Unit,
    onAction: (MultiChatInfoAction.UiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier,
        containerColor = Theme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = userInfo.name, style = Theme.typography.bodyM)

            with(userInfo.userRole) {
                AbilityCard(stringResource(R.string.can_sent_messages), canSentMessages, {}, Modifier.fillMaxWidth())
            }

            Text(
                stringResource(R.string.delete),
                style = Theme.typography.bodyM,
                color = Theme.colorScheme.error,
                modifier = Modifier.clickable { onAction(MultiChatInfoAction.UiAction.DeleteUser(userInfo.id)) }
            )
        }
    }
}

@Composable
private fun AbilityCard(title: String, checked: Boolean, onCheck: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = Theme.typography.bodyM)

        Switch(
            checked = checked,
            onCheckedChange = onCheck,
            colors = SwitchDefaults.colors(
                checkedTrackColor = Theme.colorScheme.accent,
                checkedBorderColor = Color.Transparent
            )
        )
    }
}