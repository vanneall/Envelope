package com.example.settings.main.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.settings.main.viewmodel.MainSettingsState
import com.example.settings.main.viewmodel.SettingsAction
import com.example.settings.main.viewmodel.SettingsEvent
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.point.navigation.Route
import kotlinx.coroutines.flow.Flow

@Composable
fun MainSettingsScreen(
    state: MainSettingsState,
    onAction: (SettingsAction) -> Unit,
    events: Flow<SettingsEvent>,
    onNavigate: (Route) -> Unit,
    onLeftFromAccount: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(state.isRefreshing),
        onRefresh = { onAction(SettingsAction.Action.Refresh) },
        modifier = modifier,
    ) {
        MainSettingsScreenContent(
            state = state,
            onNavigate = onNavigate,
            onAction = onAction,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
        )

        LaunchedEffect(Unit) {
            events.collect{ event ->
                when(event) {
                    SettingsEvent.LeftFromAccount -> onLeftFromAccount()
                }
            }
        }
    }
}