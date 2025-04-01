package com.point.contacts.profile.ui.content

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.point.contacts.profile.ui.initializing.ProfileScreenContentShimmer
import com.point.contacts.profile.viewmodel.ProfileAction
import com.point.contacts.profile.viewmodel.ProfileEvent
import com.point.contacts.profile.viewmodel.ProfileState
import com.point.navigation.Route
import kotlinx.coroutines.flow.Flow

@Composable
fun ProfileScreen(
    state: ProfileState,
    onNavigation: (Route) -> Unit,
    onAction: (ProfileAction) -> Unit,
    events: Flow<ProfileEvent>,
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
                events = events,
                onNavigation = onNavigation,
                modifier = modifier.padding(horizontal = 8.dp),
            )
        }
    }
}