package com.point.auth.registration.ui.credentials

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.point.auth.registration.presenter.credentials.CredentialsAction
import com.point.auth.registration.presenter.credentials.CredentialsState
import com.point.ui.EnvelopeTheme
import com.point.ui.Theme

@Composable
internal fun CredentialsScreenContent(
    state: CredentialsState,
    onAction: (CredentialsAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(WindowInsets.ime.exclude(WindowInsets.navigationBars).asPaddingValues())
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(64.dp)
    ) {
        Text(
            text = "Логин и пароль",
            style = Theme.typography.titleL,
        )

        UserCredentialsInputBlock(
            state = state,
            onAction = onAction,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Preview(locale = "ru")
@Composable
private fun ContentPreview() {
    EnvelopeTheme {
        CredentialsScreenContent(
            state = CredentialsState(),
            onAction = {},
            modifier = Modifier.fillMaxSize().background(color = Color.White).padding(horizontal = 20.dp, vertical = 20.dp)
        )
    }
}

