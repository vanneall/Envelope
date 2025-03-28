package com.point.auth.registration.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.point.auth.registration.presenter.profile.RegProfileAction
import com.point.auth.registration.presenter.profile.RegProfileState

@Composable
fun ProfileScreen(
    state: RegProfileState,
    onAction: (RegProfileAction) -> Unit,
    modifier: Modifier = Modifier
) {
    ProfileScreenContent(
        state = state,
        onAction = onAction,
        modifier = modifier,
    )
}