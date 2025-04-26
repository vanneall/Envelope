package com.point.chats.multi.info.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.point.chats.multi.info.viewmodel.MultiChatInfoAction
import com.point.chats.multi.info.viewmodel.UserInfo

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
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = userInfo.name)

            with(userInfo.userRole) {
                AbilityCard("Can pin messages", canSentMessages, {}, Modifier.fillMaxWidth())
                AbilityCard("Can sent messages", canSentMessages, {}, Modifier.fillMaxWidth())
            }

            Text("Удалить", Modifier.clickable { onAction(MultiChatInfoAction.UiAction.DeleteUser(userInfo.id)) })
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
        Text(text = title)

        Switch(
            checked = checked,
            onCheckedChange = onCheck
        )
    }
}