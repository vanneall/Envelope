package com.point.ui.materials.divider

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.point.ui.colors.new.EnvelopeTheme

@Composable
@NonRestartableComposable
fun EnvelopeHorizontalDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier,
        thickness = 1.dp,
        color = EnvelopeTheme.colorScheme.onPrimary
    )
}