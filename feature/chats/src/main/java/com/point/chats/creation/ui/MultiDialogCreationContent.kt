package com.point.chats.creation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.point.chats.creation.data.User
import com.point.chats.creation.viewmodel.MultiCreationAction.UiAction
import com.point.chats.creation.viewmodel.MultiCreationState

@Composable
internal fun MultiDialogCreationContent(
    state: MultiCreationState,
    onAction: (UiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier.padding(horizontal = 20.dp)) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(items = state.users, key = { it.username }) { user ->
                UserCard(
                    user = user,
                    onAction = onAction,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        if (state.isAvailableToCreate) {
            Button(
                onClick = { onAction(UiAction.CreateMultiChat) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Создать чат"
                )
            }
        }
    }
}


@Preview
@Composable
private fun MultiDialogCreationContentPreview() {
    MultiDialogCreationContent(
        state = MultiCreationState(
            users = buildList {
                repeat(5) {
                    add(
                        User(
                            username = "$it",
                            name = "User #$it",
                            photoId = null,
                            checked = false,
                        )
                    )
                }
            }
        ),
        onAction = {},
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    )
}