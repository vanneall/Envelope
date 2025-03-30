package com.example.settings.main.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.settings.main.viewmodel.MainSettingsState
import com.example.settings.main.viewmodel.SettingsAction
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.point.navigation.Route

@Composable
fun MainSettingsScreen(
    state: MainSettingsState,
    onAction: (SettingsAction) -> Unit,
    onNavigate: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(state.isLoading),
        onRefresh = { onAction(SettingsAction.Refresh) },
        modifier = modifier,
    ) {
        MainSettingsScreenContent(
            state = state,
            onNavigate = onNavigate,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
        )
    }
}