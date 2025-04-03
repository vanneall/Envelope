package com.point.auth.registration.ui.host

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.point.auth.registration.presenter.credentials.CredentialsAction
import com.point.auth.registration.presenter.credentials.CredentialsState
import com.point.auth.registration.presenter.host.HostAction
import com.point.auth.registration.presenter.host.HostEvent
import com.point.auth.registration.presenter.profile.RegProfileAction
import com.point.auth.registration.presenter.profile.RegProfileState
import com.point.auth.registration.ui.credentials.CredentialsScreen
import com.point.auth.registration.ui.onboarding.OnboardingScreen
import com.point.auth.registration.ui.profile.ProfileScreen
import com.point.navigation.Route
import kotlinx.coroutines.flow.Flow

@Composable
fun RegistrationHostScreen(
    onAction: (HostAction) -> Unit,
    events: Flow<HostEvent>,
    onNavigate: (Route) -> Unit,
    regProfileState: RegProfileState,
    regProfileAction: (RegProfileAction) -> Unit,
    credentialsState: CredentialsState,
    credentialsAction: (CredentialsAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    RegistrationHostScreenContent(
        pages = ImmutableList(
            listOf(
                { OnboardingScreen() },
                {
                    ProfileScreen(
                        state = regProfileState,
                        onAction = regProfileAction,
                    )
                },
                {
                    CredentialsScreen(
                        state = credentialsState,
                        onAction = credentialsAction,
                    )
                },
            )
        ),
        onAction = onAction,
        events = events,
        onNavigate = onNavigate,
        modifier = modifier,
    )
}