package com.point.auth.registration.ui.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.point.ui.Theme

@Composable
fun OnboardingScreenContent(modifier: Modifier = Modifier) {
    val scrollableState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(scrollableState)
    ) {
        Text(
            text = "Добро пожаловать в Envelope",
            style = Theme.typography.headlineM,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Здесь небольшое описание",
            style = Theme.typography.bodyL,
        )
    }
}