package com.point.ui.icons.defaults

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.point.ui.colors.new.EnvelopeTheme
import com.point.ui.colors.new.NewEnvelopeTheme

@Composable
fun EnvelopeIconButton(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    tint: Color = EnvelopeTheme.colorScheme.onPrimary,
    @StringRes contentDescription: Int? = null,
    enabled: Boolean = true,
) {
    IconButton(
        onClick = onClick ?: {},
        modifier = modifier,
        enabled = enabled,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription?.let { resId -> stringResource(resId) },
            tint = tint,
        )
    }
}

@Preview
@Composable
private fun EnvelopeFilledIconPreview() {
    NewEnvelopeTheme {
        EnvelopeIconButton(
            icon = Icons.Rounded.Done,
        )
    }
}

