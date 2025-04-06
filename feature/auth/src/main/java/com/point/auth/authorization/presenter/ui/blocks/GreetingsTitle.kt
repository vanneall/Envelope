package com.point.auth.authorization.presenter.ui.blocks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.point.auth.R
import com.point.ui.Theme

@Composable
internal fun GreetingsTitle(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.greetings_title),
            style = Theme.typography.headlineM,
            color = Theme.colorScheme.textPrimary,
        )

        Text(
            text = stringResource(R.string.greetings_subtitle),
            style = Theme.typography.bodyL,
            color = Theme.colorScheme.textSecondary,
        )
    }
}