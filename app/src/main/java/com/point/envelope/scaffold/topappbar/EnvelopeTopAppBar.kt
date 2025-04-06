package com.point.envelope.scaffold.topappbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.point.envelope.scaffold.GoBackIcon
import com.point.envelope.scaffold.actions.ActionIcon
import com.point.envelope.scaffold.topappbar.state.TopAppBarState
import com.point.ui.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnvelopeTopAppBar(
    appBarState: TopAppBarState,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            appBarState.appBarType.AsComposable(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 0.dp)
            )
        },
        navigationIcon = {
            if (appBarState.onBack != null) {
                GoBackIcon(
                    onBack = appBarState.onBack::invoke,
                )
            }
        },
        actions = {
            appBarState.actions.forEach { action ->
                ActionIcon(
                    icon = action.icon,
                    action = action.action,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Theme.colorScheme.background,
        ),
        modifier = modifier,
    )
}