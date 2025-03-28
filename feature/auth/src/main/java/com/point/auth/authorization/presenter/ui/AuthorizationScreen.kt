package com.point.auth.authorization.presenter.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.point.auth.authorization.presenter.mvi.AuthAction
import com.point.auth.authorization.presenter.mvi.AuthEvent
import com.point.auth.authorization.presenter.mvi.AuthState
import kotlinx.coroutines.flow.Flow

@Composable
fun AuthorizationScreen(
    state: AuthState,
    events: Flow<AuthEvent>,
    onAction: (AuthAction) -> Unit,
    onNavigateMain: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AuthorizationScreenContent(
        state = state,
        onAction = onAction,
        onNavigate = onNavigate,
        modifier = modifier,
        onNavigateMain = onNavigateMain,
        events = events,
    )
}