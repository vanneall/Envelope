package com.point.contacts.profile.ui.content

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.point.contacts.profile.ui.initializing.ProfileScreenContentShimmer
import com.point.contacts.profile.viewmodel.ProfileAction
import com.point.contacts.profile.viewmodel.ProfileState

@Composable
fun ProfileScreen(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.isInitialLoading) {
        ProfileScreenContentShimmer(
            modifier = modifier,
        )
    } else {
        val swipeRefreshState = rememberSwipeRefreshState(state.isRefreshing)
        SwipeRefresh(
            state = swipeRefreshState,
            swipeEnabled = state.isRefreshingEnable,
            onRefresh = { onAction(ProfileAction.Refresh) }
        ) {
            ProfileScreenContent(
                state = state,
                onAction = onAction,
                modifier = modifier.padding(horizontal = 8.dp),
            )
        }
    }
}