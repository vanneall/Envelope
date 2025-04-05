package com.point.chats.main.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ChatsInitialLoading(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        repeat(9) {
            ChatsComposableShimmer(modifier = Modifier.fillMaxWidth())
        }
    }
}
