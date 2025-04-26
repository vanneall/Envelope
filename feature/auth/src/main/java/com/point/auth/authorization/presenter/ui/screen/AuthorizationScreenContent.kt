package com.point.auth.authorization.presenter.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.point.auth.authorization.presenter.ui.blocks.AuthorizationButtons
import com.point.auth.authorization.presenter.ui.blocks.CredentialsInputFields
import com.point.auth.authorization.presenter.ui.blocks.GreetingsTitle
import com.point.auth.authorization.presenter.viewmodel.AuthAction
import com.point.auth.authorization.presenter.viewmodel.AuthEvent
import com.point.auth.authorization.presenter.viewmodel.AuthState
import com.point.navigation.Route
import com.point.ui.EnvelopeTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

@Composable
internal fun AuthorizationScreenContent(
    state: AuthState,
    onAction: (AuthAction) -> Unit,
    events: Flow<AuthEvent>,
    onNavigate: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.weight(0.1f))

        GreetingsTitle(modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.weight(0.15f).height(20.dp))

        CredentialsInputFields(
            state = state,
            onAction = onAction,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(20.dp).weight(0.5f))

        AuthorizationButtons(
            onAction = onAction,
            onNavigate = onNavigate,
            modifier = Modifier.fillMaxWidth()
                .padding(WindowInsets.ime.exclude(WindowInsets.navigationBars).asPaddingValues()),
        )
    }

    LaunchedEffect(Unit) {
        launch {
            events.collect { event ->
                when (event) {
                    AuthEvent.NavigateAllChats -> onNavigate(Route.ChatsFeature.Chats)
                }
            }
        }
    }
}


@Preview
@Composable
private fun ContentPreview() {
    EnvelopeTheme {
        AuthorizationScreenContent(
            state = AuthState(),
            onAction = {},
            events = emptyFlow(),
            onNavigate = {},
            modifier = Modifier.fillMaxSize().background(color = Color.White).padding(horizontal = 20.dp)
        )
    }
}