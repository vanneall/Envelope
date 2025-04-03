package com.point.auth.authorization.presenter.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.point.auth.authorization.presenter.viewmodel.AuthAction
import com.point.auth.authorization.presenter.viewmodel.AuthEvent
import com.point.auth.authorization.presenter.viewmodel.AuthState
import com.point.navigation.Route
import kotlinx.coroutines.flow.Flow

@Composable
fun AuthorizationScreen(
    state: AuthState,
    events: Flow<AuthEvent>,
    onAction: (AuthAction) -> Unit,
    onNavigate: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    AuthorizationScreenContent(
        state = state,
        onAction = onAction,
        events = events,
        onNavigate = onNavigate,
        modifier = modifier,
    )
}