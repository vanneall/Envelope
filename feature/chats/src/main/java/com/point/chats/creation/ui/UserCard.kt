package com.point.chats.creation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.point.chats.creation.data.User
import com.point.chats.creation.viewmodel.MultiCreationAction.UiAction
import com.point.ui.components.user.UserCardInfo

@Composable
internal fun UserCard(
    user: User,
    onAction: (UiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    com.point.ui.components.user.UserCard(
        user = UserCardInfo(
            name = user.name,
            photoUrl = user.photoId,
        ),
        modifier = modifier,
        trailing = {
            Checkbox(
                checked = user.checked,
                onCheckedChange = { onAction(UiAction.CheckUser(user.username)) }
            )
        }
    )
}

@Preview
@Composable
private fun UserCardPreview() {
    UserCard(
        user = User(
            username = "@username",
            name = "User name",
            photoId = "",
            checked = false,
        ),
        onAction = {},
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
    )
}