package com.point.auth.registration.ui.host

import androidx.compose.foundation.layout.fillMaxSize
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
                { OnboardingScreen(
                    modifier = Modifier.fillMaxSize(),
                ) },
                {
                    ProfileScreen(
                        state = regProfileState,
                        onAction = regProfileAction,
                        modifier = Modifier.fillMaxSize(),
                    )
                },
                {
                    CredentialsScreen(
                        state = credentialsState,
                        onAction = credentialsAction,
                        modifier = Modifier.fillMaxSize(),
                    )
                },
            )
        ),
        onAction = onAction,
        events = events,
        onNavigate = onNavigate,
        modifier = modifier,
        state = RegStateHost(code = credentialsState.code),
        email = credentialsState.email,
    )
}