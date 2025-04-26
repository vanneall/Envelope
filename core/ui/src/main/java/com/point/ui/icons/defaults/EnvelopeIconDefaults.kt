package com.point.ui.icons.defaults

import androidx.compose.material3.IconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.point.ui.colors.new.EnvelopeTheme

object EnvelopeIconDefaults {

    val TertiaryColors: IconButtonColors
        @Composable
        @ReadOnlyComposable
        get() = EnvelopeTheme.envelopeIconFilledButton ?: IconButtonColors(
            containerColor = EnvelopeTheme.colorScheme.tertiary,
            contentColor = EnvelopeTheme.colorScheme.onTertiary,
            disabledContentColor = EnvelopeTheme.colorScheme.secondary,
            disabledContainerColor = EnvelopeTheme.colorScheme.onSecondary,
        )
}