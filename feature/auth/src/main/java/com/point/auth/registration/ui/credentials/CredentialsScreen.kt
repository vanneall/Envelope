package com.point.auth.registration.ui.credentials

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.point.auth.registration.presenter.credentials.CredentialsAction
import com.point.auth.registration.presenter.credentials.CredentialsState

@Composable
fun CredentialsScreen(
    state: CredentialsState,
    onAction: (CredentialsAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    CredentialsScreenContent(
        state = state,
        onAction = onAction,
        modifier = modifier,
    )
}