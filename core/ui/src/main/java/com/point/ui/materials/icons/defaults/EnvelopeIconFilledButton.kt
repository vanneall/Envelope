package com.point.ui.materials.icons.defaults

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.point.ui.colors.new.NewEnvelopeTheme

@Composable
fun EnvelopeIconFilledButton(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    @StringRes contentDescription: Int? = null,
    enabled: Boolean = true,
) {
    FilledIconButton(
        onClick = onClick ?: {},
        modifier = modifier,
        enabled = enabled,
        colors = EnvelopeIconDefaults.TertiaryColors,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription?.let { resId -> stringResource(resId) },
        )
    }
}

@Preview
@Composable
private fun EnvelopeFilledIconPreview() {
    NewEnvelopeTheme {
        EnvelopeIconFilledButton(
            icon = Icons.Rounded.Done,
        )
    }
}