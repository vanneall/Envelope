package com.example.settings.profile.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.settings.profile.viewmodel.ProfileEditAction
import com.example.settings.profile.viewmodel.ProfileEditState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun ProfileEditScreen(
    state: ProfileEditState,
    onAction: (ProfileEditAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val swipeRefreshState = rememberSwipeRefreshState(false)
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {},
    ) {
        ProfileEditScreenContent(
            state = state,
            onAction = onAction,
            modifier = modifier.padding(horizontal = 10.dp)
        )
    }
}