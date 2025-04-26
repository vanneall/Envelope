package com.point.ui.materials.buttons.defaults

import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.point.ui.colors.new.Black400
import com.point.ui.colors.new.Black600
import com.point.ui.colors.new.EnvelopeTheme
import com.point.ui.colors.new.Gray400
import com.point.ui.colors.new.Gray600

object EnvelopeButtonDefaults {

    val PrimaryColors: ButtonColors
        @ReadOnlyComposable
        @Composable
        get() = EnvelopeTheme.envelopePrimaryButtonColors ?: ButtonColors(
            containerColor = EnvelopeTheme.colorScheme.primary,
            contentColor = EnvelopeTheme.colorScheme.onPrimary,
            disabledContainerColor = Black600,
            disabledContentColor = Black400,
        )

    val SecondaryColors: ButtonColors
        @ReadOnlyComposable
        @Composable
        get() = EnvelopeTheme.envelopeSecondaryButtonColors ?: ButtonColors(
            containerColor = EnvelopeTheme.colorScheme.secondary,
            contentColor = EnvelopeTheme.colorScheme.onSecondary,
            disabledContainerColor = Gray600,
            disabledContentColor = Gray400,
        )
}